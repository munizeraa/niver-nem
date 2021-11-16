package com.mnzlabz.nivernem.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mnzlabz.nivernem.data.model.SecretMessage
import com.mnzlabz.nivernem.databinding.SecretMessageItemBinding

class SecretMessageAdapter: ListAdapter<SecretMessage, SecretMessageAdapter.SecretMessageViewHolder>(DiffCallback()) {
    var listenerShare: (View) -> Unit = { }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SecretMessageViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = SecretMessageItemBinding.inflate(inflater, parent, false)
        return SecretMessageViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SecretMessageViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class SecretMessageViewHolder(private val binding: SecretMessageItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: SecretMessage) {
            binding.messageAuthor.text = item.name
            binding.messageBody.text = item.message
            //Glide.with(binding.root.context).load(item.image).into(binding.photograph)
        }
    }
}

class DiffCallback : DiffUtil.ItemCallback<SecretMessage>() {
    override fun areItemsTheSame(oldItem: SecretMessage, newItem: SecretMessage): Boolean = oldItem == newItem
    override fun areContentsTheSame(oldItem: SecretMessage, newItem: SecretMessage): Boolean = oldItem.name == newItem.name
}