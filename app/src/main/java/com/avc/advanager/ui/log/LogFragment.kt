package com.avc.advanager.ui.log

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.avc.advanager.databinding.FragmentLogBinding

class LogFragment : Fragment() {

    private lateinit var logViewModel: LogViewModel
    private var _binding: FragmentLogBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        logViewModel =
            ViewModelProvider(this).get(LogViewModel::class.java)

        _binding = FragmentLogBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textLog
        logViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}