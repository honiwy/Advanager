package com.avc.advanager

import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.avc.advanager.data.Device
import com.avc.advanager.ui.search.IPAdapter
import com.avc.advanager.data.source.LoadStatus


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

@BindingAdapter("setupStatus")
fun bindStatus(view: ProgressBar, status: LoadStatus?) {
    when (status) {
        LoadStatus.LOADING -> view.visibility = View.VISIBLE
        LoadStatus.DONE, LoadStatus.ERROR -> view.visibility = View.GONE
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