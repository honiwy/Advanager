package com.avc.advanager.fragment.search

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.avc.advanager.AdvanagerApplication
import com.avc.advanager.NavigationDirections
import com.avc.advanager.databinding.FragmentDeviceSearchBinding
import com.avc.advanager.util.Constants
import javax.inject.Inject


class DeviceSearchFragment : Fragment() {

    @Inject
    lateinit var viewModel: DeviceSearchViewModel

    private lateinit var binding: FragmentDeviceSearchBinding

    private lateinit var listAdapter: IPAdapter

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as AdvanagerApplication).appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDeviceSearchBinding.inflate(inflater, container, false).apply {
            viewModel = viewModel
        }

        binding.layoutSwipeRefreshDeviceList.setOnRefreshListener {
            viewModel.refresh()
        }

        viewModel.dataLoading.observe(viewLifecycleOwner, Observer {
            it?.let {
                binding.layoutSwipeRefreshDeviceList.isRefreshing = it
            }
        })


        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        // Set the lifecycle owner to the lifecycle of the view
        binding.lifecycleOwner = this.viewLifecycleOwner
        setupListAdapter()
        setupNavigation()
        viewModel.ipList.observe(viewLifecycleOwner, {
            listAdapter.submitList(it)
        })
    }

    private fun setupNavigation() {
        viewModel.deviceStatus.observe(viewLifecycleOwner, {
            when (it) {
                Constants.NAV_LOGIN ->
                    NavigationDirections.navigateToDeviceLoginFragment()

                Constants.NAV_REGISTER ->
                    NavigationDirections.navigateToDeviceRegisterFragment()

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