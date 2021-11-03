package com.avc.advanager

import android.util.Log
import android.view.View
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.avc.advanager.login.IPAdapter
import com.avc.advanager.util.CurrentFragmentType


@BindingAdapter("bottomNavVisibility")
fun bindBottomNavVisibility(view: View, fragment: CurrentFragmentType) {
    view.visibility =
        when (fragment) {
            CurrentFragmentType.STREAM -> View.VISIBLE
            CurrentFragmentType.LOG -> View.VISIBLE
            CurrentFragmentType.NOTIFICATION -> View.VISIBLE
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
            else -> View.GONE
        }
}

//Login  Fragment
@BindingAdapter("ipItems")
fun bindRecyclerViewWithIPItems(recyclerView: RecyclerView, ipItems: List<String>?) {
    ipItems?.let {
        recyclerView.adapter?.apply {
            when (this) {
                is IPAdapter -> submitList(it)
            }
        }
    }
}
