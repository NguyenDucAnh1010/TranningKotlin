package com.ducanh.sleepstoriesdemo

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ducanh.sleepstoriesdemo.adapter.MenuFilterAdapter
import com.ducanh.sleepstoriesdemo.adapter.MenuStoryAdapter
import com.ducanh.sleepstoriesdemo.model.ItemMenuFilter
import com.ducanh.sleepstoriesdemo.model.ItemMenuStory

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val rvMenuFilter = findViewById<RecyclerView>(R.id.rvMenuFilter)
        val rvMenuStory = findViewById<RecyclerView>(R.id.rvMenuStory)

        rvMenuFilter.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        val itemMenuFilters = listOf(
            ItemMenuFilter(R.drawable.ic_all, "All"),
            ItemMenuFilter(R.drawable.ic_love, "My"),
            ItemMenuFilter(R.drawable.ic_sad, "Anxious"),
            ItemMenuFilter(R.drawable.ic_moon, "Sleep"),
            ItemMenuFilter(R.drawable.ic_baby_happy, "Kids")
        )

        rvMenuFilter.adapter = MenuFilterAdapter(itemMenuFilters)

        rvMenuStory.layoutManager = GridLayoutManager(this, 2)
        rvMenuStory.isNestedScrollingEnabled = false

        val itemMenuStories = listOf(
            ItemMenuStory(R.drawable.bg_menu_stories_1, "Night Island", 45, "SLEEP MUSIC"),
            ItemMenuStory(R.drawable.bg_menu_stories_2, "Sweet Sleep", 45, "SLEEP MUSIC"),
            ItemMenuStory(R.drawable.bg_menu_stories_3, "Night Sleep", 45, "SLEEP MUSIC"),
            ItemMenuStory(R.drawable.bg_menu_stories_4, "Night Island", 45, "SLEEP MUSIC")
        )

        rvMenuStory.adapter = MenuStoryAdapter(itemMenuStories)
    }
}