package com.avc.advanager

import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.avc.advanager.data.Device
import com.avc.advanager.fragment.search.IPAdapter
import com.avc.advanager.data.source.LoadStatus
import com.avc.advanager.util.CurrentFragmentType


@BindingAdapter("bottomNavVisibility")
fun bindBottomNavVisibility(view: View, fragment: CurrentFragmentType) {
    view.visibility =
        when (fragment) {
            CurrentFragmentType.STREAM -> View.VISIBLE
            CurrentFragmentType.LOG -> View.VISIBLE
            CurrentFragmentType.NOTIFICATION -> View.VISIBLE
            CurrentFragmentType.SETTING -> View.VISIBLE
            else -> View.GONE
        }
}

@BindingAdapter("toolbarVisibility")
fun bindToolbarVisibility(view: View, fragment: CurrentFragmentType) {
    view.visibility =
        when (fragment) {
            CurrentFragmentType.STREAM -> View.VISIBLE
            CurrentFragmentType.LOG -> View.VISIBLE
            CurrentFragmentType.NOTIFICATION -> View.VISIBLE
            CurrentFragmentType.SETTING -> View.VISIBLE
            else -> View.GONE
        }
}

//Device Search Fragment
@BindingAdapter("ipItems")
fun bindRecyclerViewWithIPItems(recyclerView: RecyclerView, ipItems: List<Device>?) {
    ipItems?.let {
        recyclerView.adapter?.apply {
            when (this) {
                is IPAdapter -> submitList(it)
            }
        }
    }
}

/**
 * Decide the visibility of [errorText] according to [message]
 */
@BindingAdapter("setupErrorMessage")
fun bindErrorMessage(errorText: TextView, message: String?) {
    when (message) {
        null, "" -> {
            errorText.visibility = View.GONE
        }
        else -> {
            errorText.text = message
            errorText.visibility = View.VISIBLE
        }
    }
}