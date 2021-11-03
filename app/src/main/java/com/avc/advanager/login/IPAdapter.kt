package com.avc.advanager.login

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.avc.advanager.databinding.ItemIpBinding

class IPAdapter(
    private val onClickListener: OnClickListener
) : ListAdapter<String, IPAdapter.IPViewHolder>(DiffCallback) {

    class OnClickListener(val clickListener: (ip: String) -> Unit) {
        fun onClick(ip: String) = clickListener(ip)
    }

    class IPViewHolder(private var binding: ItemIpBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(ip: String, onClickListener: OnClickListener) {
            binding.ip = ip
            binding.root.setOnClickListener {
                onClickListener.onClick(ip)
            }
            binding.executePendingBindings()
        }

    }

    companion object DiffCallback : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return (oldItem === newItem)
        }

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
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