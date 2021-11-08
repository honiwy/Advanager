package com.avc.advanager.device.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.avc.advanager.NavigationDirections
import com.avc.advanager.R
import com.avc.advanager.databinding.DialogLoginBinding
import com.avc.advanager.extension.getVmFactory

class DeviceLoginDialog : DialogFragment() {

    private val viewModel by viewModels<DeviceLoginViewModel> { getVmFactory() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: DialogLoginBinding =
            DataBindingUtil.inflate(inflater, R.layout.dialog_login, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        binding.buttonClose.setOnClickListener {
            dismiss()
        }

        binding.buttonLoginIp.setOnClickListener {
            viewModel.login()
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