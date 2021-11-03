package com.avc.advanager.utils

/**
 * Created by jimmy on 2017/5/10.
 */
import android.util.Log
import java.io.IOException
import java.lang.Exception
import java.net.*
import java.nio.charset.StandardCharsets
import java.security.SecureRandom
import java.util.*
import java.util.concurrent.ConcurrentSkipListSet
import java.util.concurrent.CountDownLatch
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import java.util.regex.Pattern

object WSDeviceDiscovery {
    private const val WS_DISCOVERY_TIMEOUT = 4000
    private const val WS_DISCOVERY_PORT = 3702
    private const val WS_DISCOVERY_ADDRESS_IPv4 = "239.255.255.250"
    private val PRODUCT_LIST = arrayOf( //            "onvif://www.onvif.org/hardware/gv-vd8700",
        //            "onvif://www.onvif.org/hardware/gv-fd8700-fr",
        //            "onvif://www.onvif.org/hardware/vd-one",
        //            "onvif://www.onvif.org/hardware/ai_ipcam",
        //            "onvif://www.onvif.org/hardware/md-one",
        //            "onvif://www.onvif.org/hardware/sc-one"
        "onvif://www.onvif.org/hardware/md2"
    )
    private const val IPADDRESS_PATTERN =
        "(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)(:\\d{1,5})*"
    private const val TAG = "WSDeviceDiscovery"
    private const val XADDR = ":XAddrs"
    private const val HTTPS_PORT = "https_port/"

    /**
     * Not supported yet.
     */
    private const val WS_DISCOVERY_ADDRESS_IPv6 = "[FF02::C]"
    private const val WS_DISCOVERY_PROBE_MESSAGE =
        "<soap:Envelope xmlns:soap=\"http://www.w3.org/2003/05/soap-envelope\" xmlns:wsa=\"http://schemas.xmlsoap.org/ws/2004/08/addressing\" xmlns:tns=\"http://schemas.xmlsoap.org/ws/2005/04/discovery\"><soap:Header><wsa:Action>http://schemas.xmlsoap.org/ws/2005/04/discovery/Probe</wsa:Action><wsa:MessageID>urn:uuid:c032cfdd-c3ca-49dc-820e-ee6696ad63e2</wsa:MessageID><wsa:To>urn:schemas-xmlsoap-org:ws:2005:04:discovery</wsa:To></soap:Header><soap:Body><tns:Probe/></soap:Body></soap:Envelope>"
    private val random: Random = SecureRandom()
    @Throws(InterruptedException::class)
    @JvmStatic
    fun main(args: Array<String>) {
        for (p1 in discoverWsDevicesAsUrls()) {
            println("Device discovered: $p1")
        }
    }

    /**
     * Discover WS device on the local network and returns Urls
     *
     * @return list of unique device urls
     */
    fun discoverWsDevicesAsUrls(): Collection<String> { //{return discoverWsDevicesAsUrls("", "");}
        return discoverWsDevices()
    }

    /**
     * Discover WS device on the local network
     *
     * @return  list of unique devices access strings which might be URLs in most cases
     */
    fun discoverWsDevices(): Collection<String> {
        val addresses: MutableCollection<String> = ConcurrentSkipListSet()
        val serverStarted = CountDownLatch(1)
        val serverFinished = CountDownLatch(1)
        val addressList: MutableCollection<InetAddress> = ArrayList()
        try {
            val interfaces = NetworkInterface.getNetworkInterfaces()
            if (interfaces != null) {
                while (interfaces.hasMoreElements()) {
                    val anInterface = interfaces.nextElement()
                    if (!anInterface.isLoopback) {
                        val interfaceAddresses = anInterface.interfaceAddresses
                        for (address in interfaceAddresses) {
                            addressList.add(address.address)
                        }
                    }
                }
            }
        } catch (e: SocketException) {
            e.printStackTrace()
        }
        val executorService = Executors.newCachedThreadPool()
        for (address in addressList) {
            val runnable = Runnable {
                try {
                    val uuid = UUID.randomUUID().toString()
                    val probe = WS_DISCOVERY_PROBE_MESSAGE.replace(
                        "<wsa:MessageID>urn:uuid:.*</wsa:MessageID>".toRegex(),
                        "<wsa:MessageID>urn:uuid:$uuid</wsa:MessageID>"
                    )
                    val port = random.nextInt(20000) + 40000
                    val server = DatagramSocket(port, address)
                    object : Thread() {
                        override fun run() {
                            try {
                                val packet = DatagramPacket(ByteArray(4096), 4096)
                                server.soTimeout = WS_DISCOVERY_TIMEOUT
                                val timerStarted = System.currentTimeMillis()
                                while (System.currentTimeMillis() - timerStarted < WS_DISCOVERY_TIMEOUT) {
                                    serverStarted.countDown()
                                    server.receive(packet)
                                    val collection = parseSoapResponseForUrls(
                                        Arrays.copyOf(
                                            packet.data,
                                            packet.length
                                        )
                                    )
                                    for (key in collection) {
                                        addresses.add(key)
                                    }
                                }
                            } catch (ignored: SocketTimeoutException) {
                            } catch (e: Exception) {
                                e.printStackTrace()
                            } finally {
                                serverFinished.countDown()
                                server.close()
                            }
                        }
                    }.start()
                    try {
                        serverStarted.await(1000, TimeUnit.MILLISECONDS)
                    } catch (e: InterruptedException) {
                        e.printStackTrace()
                    }
                    if (address is Inet4Address) {
                        server.send(
                            DatagramPacket(
                                probe.toByteArray(), probe.length, InetAddress.getByName(
                                    WS_DISCOVERY_ADDRESS_IPv4
                                ), WS_DISCOVERY_PORT
                            )
                        )
                    } else {
                        server.send(
                            DatagramPacket(
                                probe.toByteArray(), probe.length, InetAddress.getByName(
                                    WS_DISCOVERY_ADDRESS_IPv6
                                ), WS_DISCOVERY_PORT
                            )
                        )
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
                try {
                    serverFinished.await(WS_DISCOVERY_TIMEOUT.toLong(), TimeUnit.MILLISECONDS)
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
            }
            executorService.submit(runnable)
        }
        try {
            executorService.shutdown()
            executorService.awaitTermination(
                (WS_DISCOVERY_TIMEOUT + 2000).toLong(),
                TimeUnit.MILLISECONDS
            )
        } catch (ignored: InterruptedException) {
        }
        return addresses
    }

    @Throws(IOException::class)
    private fun parseSoapResponseForUrls(data: ByteArray): Collection<String> {
        //System.out.println(new String(data));
        val urls: MutableCollection<String> = ArrayList()
        val input_raw = String(data, StandardCharsets.UTF_8)
        for (target in PRODUCT_LIST) {
            if (!input_raw.toLowerCase().contains(target)) continue
            val ip_filter = input_raw.substring(input_raw.indexOf(XADDR))
            val pattern = Pattern.compile(IPADDRESS_PATTERN)
            val matcher = pattern.matcher(ip_filter)
            var targetIP = ""
            if (matcher.find()) {
                targetIP = matcher.group()
                if (input_raw.indexOf(HTTPS_PORT) > 0) {
                    val httpsPort_and_soap =
                        input_raw.substring(input_raw.indexOf(HTTPS_PORT) + HTTPS_PORT.length)
                    val httpsPort = httpsPort_and_soap.substring(0, httpsPort_and_soap.indexOf("<"))
                    targetIP = if (targetIP.contains(":")) {
                        val ipInfo = targetIP.split(":").toTypedArray()
                        ipInfo[0] + ":" + httpsPort + "https"
                    } else {
                        targetIP + ":" + httpsPort + "https"
                    }
                }
            }
            if (!targetIP.isEmpty()) {
                urls.addAll(Arrays.asList(targetIP))
            }
        }
        return urls
    }
}