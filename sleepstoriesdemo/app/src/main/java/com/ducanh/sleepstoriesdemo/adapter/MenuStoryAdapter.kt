package com.ducanh.sleepstoriesdemo.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ducanh.sleepstoriesdemo.R
import com.ducanh.sleepstoriesdemo.model.ItemMenuStory

class MenuStoryAdapter(private val items: List<ItemMenuStory>) :
    RecyclerView.Adapter<MenuStoryAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivBackground: ImageView = itemView.findViewById(R.id.ivBackground)
        val txtTilte: TextView = itemView.findViewById(R.id.txtTilte)
        val txtTime: TextView = itemView.findViewById(R.id.txtTime)
        val txtCategory: TextView = itemView.findViewById(R.id.txtCategory)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_menu_story, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.ivBackground.setImageResource(item.imageResId)
        holder.txtTilte.text = item.title
        holder.txtTime.text = item.time.toString() + " MIN"
        holder.txtCategory.text = item.category.uppercase()
    }

    override fun getItemCount(): Int = items.size
}