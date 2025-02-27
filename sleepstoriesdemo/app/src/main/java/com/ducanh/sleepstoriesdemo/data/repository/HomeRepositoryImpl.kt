package com.ducanh.sleepstoriesdemo.data.repository

import com.ducanh.sleepstoriesdemo.R
import com.ducanh.sleepstoriesdemo.data.model.ItemMenuFilter
import com.ducanh.sleepstoriesdemo.data.model.ItemMenuStory

class HomeRepositoryImpl : HomeRepository {
    override fun getMenuFilterList() = listOf(
        ItemMenuFilter(R.drawable.ic_all, "All"),
        ItemMenuFilter(R.drawable.ic_love, "My"),
        ItemMenuFilter(R.drawable.ic_sad, "Anxious"),
        ItemMenuFilter(R.drawable.ic_moon, "Sleep"),
        ItemMenuFilter(R.drawable.ic_baby_happy, "Kids")
    )

    override fun getMenuStoryList() = listOf(
        ItemMenuStory(R.drawable.bg_menu_stories_1, "Night Island", "45 MIN", "SLEEP MUSIC"),
        ItemMenuStory(R.drawable.bg_menu_stories_2, "Sweet Sleep", "45 MIN", "SLEEP MUSIC"),
        ItemMenuStory(R.drawable.bg_menu_stories_3, "Night Sleep", "45 MIN", "SLEEP MUSIC"),
        ItemMenuStory(R.drawable.bg_menu_stories_4, "Night Island", "45 MIN", "SLEEP MUSIC")
    )
}