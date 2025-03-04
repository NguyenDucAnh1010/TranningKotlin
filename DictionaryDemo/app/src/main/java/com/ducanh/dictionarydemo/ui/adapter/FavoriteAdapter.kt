package com.example.androidtraining2.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ducanh.dictionarydemo.data.entity.Word
import com.ducanh.dictionarydemo.databinding.ItemWordBinding
import com.ducanh.dictionarydemo.ui.adapter.OnDictionaryClickListener

class FavoriteAdapter(
    private val items: List<Word>,
    private val listener: OnDictionaryClickListener
) :
    RecyclerView.Adapter<FavoriteAdapter.ViewHolder>() {
    class ViewHolder(var binding: ItemWordBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemWordBinding.inflate(
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
            listener.onWordClick(item)
        }

        holder.binding.btnSpeak.setOnClickListener {
            listener.onSoundClick(item)
        }

        holder.binding.btnFavorite.setOnClickListener {
            listener.onfavouriteClick(item)
        }

        holder.binding.btnShare.setOnClickListener {
            listener.onShareClick(item)
        }
    }

    override fun getItemCount(): Int = items.size
}