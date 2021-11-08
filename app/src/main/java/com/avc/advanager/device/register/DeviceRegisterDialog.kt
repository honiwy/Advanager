package com.avc.advanager.device.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.avc.advanager.R
import com.avc.advanager.databinding.DialogRegisterBinding
import com.avc.advanager.extension.getVmFactory
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.avc.advanager.NavigationDirections

class DeviceRegisterDialog : DialogFragment() {

    private val viewModel by viewModels<DeviceRegisterViewModel> { getVmFactory() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: DialogRegisterBinding =
            DataBindingUtil.inflate(inflater, R.layout.dialog_register, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        binding.buttonClose.setOnClickListener {
            dismiss()
        }

        viewModel.password.observe(this, Observer {
            viewModel.checkPasswordLegal()
        })

        viewModel.repeatedPassword.observe(this, Observer {
            viewModel.checkPasswordConsistent()
        })

        binding.buttonRegisterIp.setOnClickListener {
            viewModel.register()
        }

        viewModel.navigateToHomePage.observe(this, Observer {
            it?.let {
                findNavController().navigate(
                    NavigationDirections.navigateToStreamFragment(

                    )
                )
                viewModel.onSucceeded()
            }
        })

        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_FRAME, R.style.MessageDialog)
    }
}