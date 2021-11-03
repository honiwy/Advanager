package com.avc.advanager

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import com.avc.advanager.databinding.ActivityMainBinding
import com.avc.advanager.extension.getVmFactory
import com.avc.advanager.util.CurrentFragmentType

class MainActivity : AppCompatActivity() {

    val viewModel by viewModels<MainViewModel> { getVmFactory() }
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        binding.navView.setOnItemSelectedListener{ item ->
            when (item.itemId) {
                R.id.navigation_stream -> {
                    findNavController(R.id.nav_host).navigate(
                        NavigationDirections.navigateToStreamFragment(

                        )
                    )
                    return@setOnItemSelectedListener true
                }
                R.id.navigation_log -> {
                    findNavController(R.id.nav_host).navigate(
                        NavigationDirections.navigateToLogFragment(
                        )
                    )
                    return@setOnItemSelectedListener true
                }
                R.id.navigation_notifications -> {
                    findNavController(R.id.nav_host).navigate(
                        NavigationDirections.navigateToNotificationsFragment(
                        )
                    )
                    return@setOnItemSelectedListener true
                }
            }
            false
        }

        setupNavController()
    }

    private fun setupNavController() {
        findNavController(R.id.nav_host).addOnDestinationChangedListener { navController: NavController, _: NavDestination, _: Bundle? ->
            viewModel.currentFragmentType.value = when (navController.currentDestination?.id) {
                R.id.streamFragment -> CurrentFragmentType.STREAM
                R.id.logFragment -> CurrentFragmentType.LOG
                R.id.notificationsFragment -> CurrentFragmentType.NOTIFICATION
                R.id.deviceSearchFragment -> CurrentFragmentType.DEVICESEARCH
                else -> viewModel.currentFragmentType.value
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.d("Main Activity", "onActivityResult: ")
    }
}