package com.ducanh.dictionarydemo.ui

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.ducanh.dictionarydemo.R
import com.ducanh.dictionarydemo.databinding.ActivityMainBinding
import com.ducanh.dictionarydemo.ui.fragment.dictionary.DictionaryFragment
import com.ducanh.dictionarydemo.ui.fragment.favorite.FavoriteFragment
import com.ducanh.dictionarydemo.ui.fragment.game.GameFragment
import com.ducanh.dictionarydemo.ui.fragment.settings.SettingsFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

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

        replaceFragment(DictionaryFragment())

        binding.bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.dictionaryFragment -> replaceFragment(DictionaryFragment())
                R.id.favoriteFragment -> replaceFragment(FavoriteFragment())
                R.id.gameFragment -> replaceFragment(GameFragment())
                R.id.settingsFragment -> replaceFragment(SettingsFragment())
                else -> {

                }
            }
            true
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        binding.bottomNavigationView.visibility = View.VISIBLE
    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout, fragment)
        fragmentTransaction.commit()
    }
}