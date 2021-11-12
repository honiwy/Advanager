package com.avc.advanager.fragment.login

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.avc.advanager.AdvanagerApplication
import com.avc.advanager.NavigationDirections
import com.avc.advanager.databinding.FragmentLoginBinding
import javax.inject.Inject

class DeviceLoginFragment : Fragment() {

    @Inject
    lateinit var viewModel: DeviceLoginViewModel

    private lateinit var binding: FragmentLoginBinding

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as AdvanagerApplication).appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater, container, false).apply {
            viewModel = viewModel
        }
        binding.lifecycleOwner = this

        binding.buttonClose.setOnClickListener {
        }

        binding.buttonLoginIp.setOnClickListener {
            viewModel.login()
        }

        viewModel.navigateToHomePage.observe(viewLifecycleOwner, Observer {
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


}