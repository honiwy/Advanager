package com.avc.advanager.ui.landing

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.avc.advanager.NavigationDirections
import com.avc.advanager.databinding.FragmentLandingBinding
import com.avc.advanager.extenstion.getVmFactory
import com.avc.advanager.util.Constants

class LandingFragment : Fragment() {

    private val viewModel by viewModels<LandingViewModel> { getVmFactory() }

    private lateinit var binding: FragmentLandingBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLandingBinding.inflate(inflater, container, false)

        setupNavigation()
        return binding.root
    }

    private fun setupNavigation() {
        viewModel.deviceStatus.observe(viewLifecycleOwner, {
            when (it) {
                Constants.NAV_SEARCH -> findNavController().navigate(
                    NavigationDirections.navigateToDeviceSearchFragment()
                )
                Constants.NAV_LOGIN -> findNavController().navigate(
                    NavigationDirections.navigateToDeviceLoginFragment()
                )
                Constants.NAV_REGISTER -> findNavController().navigate(
                    NavigationDirections.navigateToDeviceRegisterFragment()
                )
            }
        })
    }

}