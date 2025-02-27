package com.ducanh.sleepstoriesdemo.presentation.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.ducanh.sleepstoriesdemo.R
import com.ducanh.sleepstoriesdemo.databinding.ItemMenuFilterBinding
import com.ducanh.sleepstoriesdemo.data.model.ItemMenuFilter

class MenuFilterAdapter(private val items: List<ItemMenuFilter>) :
    RecyclerView.Adapter<MenuFilterAdapter.ViewHolder>() {
    class ViewHolder(var binding: ItemMenuFilterBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemMenuFilterBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        ))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.binding.item = item

        if (position == 0) {
            holder.binding.ibIcon.setBackgroundColor(
                ContextCompat.getColor(holder.itemView.context, R.color.indigo_a200)
            )
            holder.binding.txtTilte.setTextColor(
                ContextCompat.getColor(
                    holder.itemView.context, R.color.white
                )
            )
        }
    }

    override fun getItemCount(): Int = items.size
}