package com.avc.advanager.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.avc.advanager.NavigationDirections
import com.avc.advanager.databinding.FragmentLoginBinding
import com.avc.advanager.extenstion.getVmFactory

class DeviceLoginFragment : Fragment() {

    private val viewModel by viewModels<DeviceLoginViewModel> { getVmFactory() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentLoginBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        binding.buttonClose.setOnClickListener {
            findNavController().navigate(
                NavigationDirections.navigateToDeviceSearchFragment()
            )
        }

        binding.buttonLoginIp.setOnClickListener {
            viewModel.login()
        }

        viewModel.navigateToDashboard.observe(viewLifecycleOwner, Observer {
            it?.let {
                findNavController().navigate(
                    NavigationDirections.navigateToDashboardFragment()
                )
                viewModel.onSucceeded()
            }
        })

        return binding.root
    }


}