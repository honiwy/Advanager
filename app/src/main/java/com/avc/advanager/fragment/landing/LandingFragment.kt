package com.avc.advanager.fragment.landing

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.avc.advanager.AdvanagerApplication
import com.avc.advanager.NavigationDirections
import com.avc.advanager.databinding.FragmentLandingBinding
import com.avc.advanager.fragment.login.DeviceLoginFragment
import com.avc.advanager.fragment.register.DeviceRegisterFragment
import com.avc.advanager.util.Constants
import javax.inject.Inject

class LandingFragment : Fragment() {

    @Inject
    lateinit var viewModel: LandingViewModel

    private lateinit var binding: FragmentLandingBinding

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as AdvanagerApplication).appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLandingBinding.inflate(inflater, container, false).apply {
            viewModel = viewModel
        }
        setupNavigation()
        return binding.root
    }

    private fun setupNavigation() {
        viewModel.deviceStatus.observe(viewLifecycleOwner, {
            when (it) {
                Constants.NAV_SEARCH -> findNavController().navigate(
                    NavigationDirections.navigateToDeviceSearchFragment()
                )
                Constants.NAV_LOGIN ->
                    NavigationDirections.navigateToDeviceLoginFragment()

                Constants.NAV_REGISTER ->
                    NavigationDirections.navigateToDeviceRegisterFragment()

            }
        })
    }

}