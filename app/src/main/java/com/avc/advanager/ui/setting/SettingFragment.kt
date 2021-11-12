package com.avc.advanager.ui.setting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import com.avc.advanager.databinding.FragmentSettingBinding
import javax.inject.Inject


class SettingFragment : Fragment() {


    @Inject
    lateinit var viewModel: SettingViewModel


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