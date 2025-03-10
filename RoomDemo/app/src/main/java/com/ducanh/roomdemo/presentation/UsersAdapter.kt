package com.ducanh.roomdemo.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ducanh.roomdemo.data.model.UserWithTasks
import com.ducanh.roomdemo.databinding.ItemUserBinding

class UsersAdapter(private val items: List<UserWithTasks>,private val listener: OnUserClickListener) :
    RecyclerView.Adapter<UsersAdapter.ViewHolder>() {
    class ViewHolder(var binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemUserBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.binding.item = item

        holder.itemView.setOnClickListener {
            listener.onUserClick(item)
        }

        holder.binding.btnDetail.setOnClickListener {
            listener.onDetailButtonClick(item)
        }
    }

    override fun getItemCount(): Int = items.size
}