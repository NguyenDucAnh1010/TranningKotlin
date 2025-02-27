package com.ducanh.sleepstoriesdemo.presentation.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ducanh.sleepstoriesdemo.databinding.ItemMenuStoryBinding
import com.ducanh.sleepstoriesdemo.data.model.ItemMenuStory

class MenuStoryAdapter(private val items: List<ItemMenuStory>) :
    RecyclerView.Adapter<MenuStoryAdapter.ViewHolder>() {
    class ViewHolder(var binding: ItemMenuStoryBinding) : RecyclerView.ViewHolder(binding.root) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemMenuStoryBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.binding.item = item
    }

    override fun getItemCount(): Int = items.size
}