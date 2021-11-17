package com.avc.advanager.ui.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.avc.advanager.NavigationDirections
import com.avc.advanager.databinding.FragmentRegisterBinding
import com.avc.advanager.extenstion.getVmFactory


class DeviceRegisterFragment : Fragment() {

    private val viewModel by viewModels<DeviceRegisterViewModel> { getVmFactory() }

    private lateinit var binding: FragmentRegisterBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegisterBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        binding.buttonClose.setOnClickListener {
            findNavController().navigate(
                NavigationDirections.navigateToDeviceSearchFragment()
            )
        }

        viewModel.password.observe(viewLifecycleOwner, Observer {
            viewModel.checkPasswordLegal()
        })

        viewModel.repeatedPassword.observe(viewLifecycleOwner, Observer {
            viewModel.checkPasswordConsistent()
        })

        binding.buttonRegisterIp.setOnClickListener {
            viewModel.register()
        }

        viewModel.navigateToLogin.observe(viewLifecycleOwner, Observer {
            it?.let {
                findNavController().navigate(
                    NavigationDirections.navigateToDeviceLoginFragment()
                )
                viewModel.onSucceeded()
            }
        })

        return binding.root
    }

}