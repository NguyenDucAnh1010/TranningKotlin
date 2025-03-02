package com.ducanh.roomdemo.presentation.task

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ducanh.roomdemo.data.model.Task
import com.ducanh.roomdemo.databinding.ItemTaskBinding

class TaskAdapter(private val items: List<Task>) :
    RecyclerView.Adapter<TaskAdapter.ViewHolder>() {
    class ViewHolder(var binding: ItemTaskBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemTaskBinding.inflate(
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