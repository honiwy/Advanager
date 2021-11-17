package com.avc.advanager.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.avc.advanager.NavigationDirections
import com.avc.advanager.data.source.LoadStatus
import com.avc.advanager.databinding.FragmentDeviceSearchBinding
import com.avc.advanager.extenstion.getVmFactory
import com.avc.advanager.util.Constants


class DeviceSearchFragment : Fragment() {

    private val viewModel by viewModels<DeviceSearchViewModel> { getVmFactory() }

    private lateinit var binding: FragmentDeviceSearchBinding

    private lateinit var listAdapter: IPAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDeviceSearchBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        binding.layoutSwipeRefreshDeviceList.setOnRefreshListener {
            viewModel.refresh()
        }

        viewModel.loadingStatus.observe(viewLifecycleOwner, Observer {
            it?.let {
                binding.layoutSwipeRefreshDeviceList.isRefreshing = it==LoadStatus.LOADING
            }
        })


        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setupListAdapter()
        setupNavigation()
        viewModel.ipList.observe(viewLifecycleOwner, {
            listAdapter.submitList(it)
        })
    }

    private fun setupNavigation() {
        viewModel.deviceStatus.observe(viewLifecycleOwner, {
            it?.let {
                when (it) {
                    Constants.NAV_LOGIN -> findNavController().navigate(
                        NavigationDirections.navigateToDeviceLoginFragment()
                    )
                    Constants.NAV_REGISTER -> findNavController().navigate(
                        NavigationDirections.navigateToDeviceRegisterFragment()
                    )
                }
                viewModel.onSucceeded()
            }
        })
    }

    private fun setupListAdapter() {
        listAdapter = IPAdapter(IPAdapter.OnClickListener {
            viewModel.checkDeviceStatus(it.ip)
        })
        binding.recyclerIp.adapter = listAdapter

    }

}