package com.example.androidtraining2.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ducanh.dictionarydemo.R
import com.ducanh.dictionarydemo.data.entity.Word
import com.ducanh.dictionarydemo.databinding.ItemWordBinding
import com.ducanh.dictionarydemo.ui.adapter.OnDictionaryClickListener

class WordAdapter(
    private val context: Context,
    private var items: MutableList<Word>,
    private val listener: OnDictionaryClickListener
) :
    RecyclerView.Adapter<WordAdapter.ViewHolder>() {
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

    fun updateList(newItems: List<Word>) {
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
    }

    fun updateWord(word: Word) {
        val index = items.indexOfFirst { it.word == word.word }
        if (index != -1) {
            items[index] = word
            notifyItemChanged(index)
        }
    }

    fun removeItem(word: Word) {
        val position = items.indexOfFirst { it.word == word.word }
        if (position != -1) {
            items.removeAt(position)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position, items.size)
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.binding.item = item

        var sharedPref =
            context.getSharedPreferences("AppPreferences", Context.MODE_PRIVATE)

        var textSize = sharedPref.getFloat("textSize", 19f)
        holder.binding.tvWord.textSize = textSize
        holder.binding.tvMean.textSize = textSize

        if (item.isFavorite) {
            holder.binding.btnFavorite.setImageResource(R.drawable.ic_select_favorite)
        }else{
            holder.binding.btnFavorite.setImageResource(R.drawable.ic_unselect_favorite)
        }

        holder.itemView.setOnClickListener {
            listener.onWordClick(item)
        }

        holder.binding.btnSpeak.setOnClickListener {
            listener.onSoundClick(item)
        }

        holder.binding.btnFavorite.setOnClickListener {
            listener.onfavouriteClick(item)
            notifyItemChanged(position)
        }

        holder.binding.btnShare.setOnClickListener {
            listener.onShareClick(item)
        }
    }

    override fun getItemCount(): Int = items.size
}