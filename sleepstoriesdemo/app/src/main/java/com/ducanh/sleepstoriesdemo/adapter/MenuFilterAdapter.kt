package com.ducanh.sleepstoriesdemo.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.ducanh.sleepstoriesdemo.R
import com.ducanh.sleepstoriesdemo.model.ItemMenuFilter

class MenuFilterAdapter(private val items: List<ItemMenuFilter>) :
    RecyclerView.Adapter<MenuFilterAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ibIcon: ImageButton = itemView.findViewById(R.id.ibIcon)
        val txtTilte: TextView = itemView.findViewById(R.id.txtTilte)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_menu_filter, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.ibIcon.setImageResource(item.imageResId)
        holder.txtTilte.text = item.title

        if (position == 0) {
            holder.ibIcon.setBackgroundColor(
                ContextCompat.getColor(holder.itemView.context, R.color.indigo_a200)
            )
            holder.txtTilte.setTextColor(
                ContextCompat.getColor(
                    holder.itemView.context, R.color.white
                )
            )
        }
    }

    override fun getItemCount(): Int = items.size
}