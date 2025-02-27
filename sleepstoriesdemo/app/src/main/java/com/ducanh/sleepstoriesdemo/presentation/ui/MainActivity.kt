package com.ducanh.sleepstoriesdemo.presentation.ui

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.ducanh.sleepstoriesdemo.R
import com.ducanh.sleepstoriesdemo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var menuFilterAdapter: MenuFilterAdapter
    private lateinit var menuStoryAdapter: MenuStoryAdapter

    private lateinit var binding: ActivityMainBinding

    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater)
        binding.lifecycleOwner = this
        binding.executePendingBindings()
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        initView()
        initListener()
    }

    private fun initView() {
        binding.rvMenuFilter.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.rvMenuStory.layoutManager = GridLayoutManager(this, 2)
        binding.rvMenuStory.isNestedScrollingEnabled = false
    }

    private fun initListener() {
        viewModel.menuFilters.observe(this) {
            menuFilterAdapter = MenuFilterAdapter(it)
            binding.rvMenuFilter.adapter = menuFilterAdapter
        }

        viewModel.menuStories.observe(this) {
            menuStoryAdapter = MenuStoryAdapter(it)
            binding.rvMenuStory.adapter = menuStoryAdapter
        }

        viewModel.getMenuFilterData()
        viewModel.getMenuStoryData()
    }
}