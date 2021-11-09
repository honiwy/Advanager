package com.avc.advanager.ui.setting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.avc.advanager.NavigationDirections
import com.avc.advanager.databinding.FragmentDeviceSearchBinding
import com.avc.advanager.databinding.FragmentSettingBinding
import com.avc.advanager.databinding.FragmentStreamBinding
import com.avc.advanager.extension.getVmFactory
import com.avc.advanager.fragment.login.DeviceLoginDialog
import com.avc.advanager.fragment.search.DeviceSearchViewModel
import com.avc.advanager.ui.stream.StreamViewModel
import android.R
import androidx.fragment.app.FragmentManager
import androidx.navigation.fragment.findNavController


class SettingFragment : Fragment() {
    private val viewModel by viewModels<SettingViewModel> { getVmFactory() }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentSettingBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel


        viewModel.navigateToDeviceSearch.observe(viewLifecycleOwner, Observer {
            it?.let {
                //Fixme can't back to device search fragment
                val fm: FragmentManager = parentFragmentManager
                fm.popBackStack()
                fm.executePendingTransactions()
                viewModel.onSucceeded()
            }
        })

        return binding.root
    }

}