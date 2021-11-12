package com.avc.advanager.fragment.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.avc.advanager.data.Device
import com.avc.advanager.databinding.ItemIpBinding

class IPAdapter(
    private val onClickListener: OnClickListener
) : ListAdapter<Device, IPAdapter.IPViewHolder>(DiffCallback) {

    class OnClickListener(val clickListener: (device: Device) -> Unit) {
        fun onClick(device: Device) = clickListener(device)
    }

    class IPViewHolder(private var binding: ItemIpBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(device: Device, onClickListener: OnClickListener) {
            binding.ip = device.ip
            binding.root.setOnClickListener {
                onClickListener.onClick(device)
            }
            binding.executePendingBindings()
        }

    }

    companion object DiffCallback : DiffUtil.ItemCallback<Device>() {
        override fun areItemsTheSame(oldItem: Device, newItem: Device): Boolean {
            return (oldItem === newItem)
        }

        override fun areContentsTheSame(oldItem: Device, newItem: Device): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IPViewHolder {
        return IPViewHolder(
            ItemIpBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    /**
     * Replaces the contents of a view (invoked by the layout manager)
     */
    override fun onBindViewHolder(holder: IPViewHolder, position: Int) {
        holder.bind(getItem(position), onClickListener)
    }
}