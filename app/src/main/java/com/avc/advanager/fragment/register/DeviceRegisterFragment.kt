package com.avc.advanager.fragment.register

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.avc.advanager.AdvanagerApplication
import com.avc.advanager.NavigationDirections
import com.avc.advanager.databinding.FragmentRegisterBinding
import javax.inject.Inject


class DeviceRegisterFragment : Fragment() {

    @Inject
    lateinit var viewModel: DeviceRegisterViewModel

    private lateinit var binding: FragmentRegisterBinding

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as AdvanagerApplication).appComponent.inject(this)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegisterBinding.inflate(inflater, container, false).apply {
            viewModel = viewModel
        }
        binding.lifecycleOwner = this

        binding.buttonClose.setOnClickListener {
//            dismiss()
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
                NavigationDirections.navigateToDeviceLoginFragment()
                viewModel.onSucceeded()
            }
        })

        return binding.root
    }

}