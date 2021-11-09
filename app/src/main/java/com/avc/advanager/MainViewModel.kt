package com.avc.advanager

import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.avc.advanager.data.Result
import com.avc.advanager.data.Token
import com.avc.advanager.fragment.DeviceManager
import com.avc.advanager.source.AdvanagerRepository
import com.avc.advanager.source.LoadStatus
import com.avc.advanager.util.CurrentFragmentType
import com.avc.advanager.util.Util
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class MainViewModel() : ViewModel() {

    // Record current fragment to support data binding
    val currentFragmentType = MutableLiveData<CurrentFragmentType>()
}