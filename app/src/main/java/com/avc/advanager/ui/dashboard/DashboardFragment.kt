package com.avc.advanager.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.avc.advanager.NavigationDirections
import com.avc.advanager.databinding.FragmentDashboardBinding
import com.avc.advanager.extenstion.getVmFactory

class DashboardFragment : Fragment() {

    private val viewModel by viewModels<DashboardViewModel> { getVmFactory() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentDashboardBinding.inflate(inflater, container, false)

        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        viewModel.navigateToDeviceSearch.observe(viewLifecycleOwner, Observer {
            it?.let {
                findNavController().navigate(
                    NavigationDirections.navigateToDeviceSearchFragment()
                )
                viewModel.onSucceeded()
            }
        })

        return binding.root
    }

}