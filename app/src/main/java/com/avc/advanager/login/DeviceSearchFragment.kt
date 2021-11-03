package com.avc.advanager.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.avc.advanager.databinding.FragmentDeviceSearchBinding
import com.avc.advanager.extension.getVmFactory


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

            })

        return binding.root
    }


}