package com.avc.advanager.fragment.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.avc.advanager.databinding.FragmentDeviceSearchBinding
import com.avc.advanager.fragment.login.DeviceLoginDialog
import com.avc.advanager.fragment.register.DeviceRegisterDialog
import com.avc.advanager.extension.getVmFactory
import com.avc.advanager.response.DeviceInitialResponse.Companion.INITIAL_ALREADY
import com.avc.advanager.response.DeviceInitialResponse.Companion.INITIAL_YET


class DeviceSearchFragment : Fragment() {
    private val viewModel by viewModels<DeviceSearchViewModel> { getVmFactory() }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentDeviceSearchBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        binding.recyclerIp.adapter =
            IPAdapter(IPAdapter.OnClickListener {
                viewModel.checkDeviceStatus(it)
            })

        binding.layoutSwipeRefreshDeviceList.setOnRefreshListener {
            viewModel.refresh()
        }

        viewModel.refreshStatus.observe(viewLifecycleOwner, Observer {
            it?.let {
                binding.layoutSwipeRefreshDeviceList.isRefreshing = it
            }
        })

        viewModel.deviceStatus.observe(viewLifecycleOwner, Observer {
            if (it == INITIAL_YET) {
                DeviceRegisterDialog().show(childFragmentManager, DIALOG_REGISTER)
            } else if (it == INITIAL_ALREADY) {
                DeviceLoginDialog().show(childFragmentManager, DIALOG_LOGIN)
            }
        })

        return binding.root
    }

    companion object {
        private const val DIALOG_REGISTER = "device_register"
        private const val DIALOG_LOGIN = "device_login"
    }
}