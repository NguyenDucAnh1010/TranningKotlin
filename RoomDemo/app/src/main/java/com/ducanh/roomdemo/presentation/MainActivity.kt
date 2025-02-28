package com.ducanh.roomdemo.presentation

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.ducanh.roomdemo.R
import com.ducanh.roomdemo.data.model.User
import com.ducanh.roomdemo.data.room.UserDatabase
import com.ducanh.roomdemo.databinding.ActivityMainBinding
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var userDatabase: UserDatabase
    private lateinit var usersAdapter: UsersAdapter

    private lateinit var binding: ActivityMainBinding

//    private val viewModel by viewModels<MainViewModel>()

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
        binding.rvUsers.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
    }

    private fun initListener() {
//        val repository: UserRepositoryImpl = UserRepositoryImpl(getDatabase(this).userDao())
//        userViewModel = MainViewModel(repository)
//
//        userViewModel.users.observe(this) {
//            usersAdapter = UsersAdapter(it)
//            binding.rvUsers.adapter = usersAdapter
//        }
//
//        userViewModel.insert(User(name = "John Doe", age = 25))

        userDatabase = UserDatabase.getDatabase(this)!!

        val user = User(name = "Nguyễn Văn A", age = 30)
        userDatabase.userDao().insert(user)

        val users = userDatabase.userDao().getAllUsers()
        usersAdapter = UsersAdapter(users)
        binding.rvUsers.adapter = usersAdapter
    }
}