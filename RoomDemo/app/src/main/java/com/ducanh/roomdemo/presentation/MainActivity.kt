package com.ducanh.roomdemo.presentation

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.ducanh.roomdemo.R
import com.ducanh.roomdemo.data.model.User
import com.ducanh.roomdemo.data.repository.UserRepositoryImpl
import com.ducanh.roomdemo.data.room.UserDatabase
import com.ducanh.roomdemo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), OnUserClickListener {
    private lateinit var userDatabase: UserDatabase
    private lateinit var usersAdapter: UsersAdapter

    private lateinit var binding: ActivityMainBinding

    private lateinit var viewModel: MainViewModel

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

        binding.btnCreateNew.setOnClickListener {
            val name = binding.edtName.text ?: ""
            val ageText = binding.edtAge.text ?: ""

            if (name.isNotEmpty() && ageText.isNotEmpty()) {
                val age = ageText.toString().toIntOrNull()
                if (age != null) {
                    val user = User(name = name.toString(), age = age)
                    viewModel.insert(user)
                }
            }
        }

        binding.btnUpate.setOnClickListener {
            val idText = binding.edtId.text ?: ""
            val name = binding.edtName.text ?: ""
            val ageText = binding.edtAge.text ?: ""

            if (idText.isNotEmpty() && name.isNotEmpty() && ageText.isNotEmpty()) {
                val id = idText.toString().toIntOrNull()
                val age = ageText.toString().toIntOrNull()
                if (id != null && age != null) {
                    val user = User(id = id, name = name.toString(), age = age)
                    viewModel.updateUser(user)
                }
            }
        }

        binding.btnDelete.setOnClickListener {
            val idText = binding.edtId.text ?: ""
            val name = binding.edtName.text ?: ""
            val ageText = binding.edtAge.text ?: ""

            if (idText.isNotEmpty() && name.isNotEmpty() && ageText.isNotEmpty()) {
                val id = idText.toString().toIntOrNull()
                val age = ageText.toString().toIntOrNull()
                if (id != null && age != null) {
                    val user = User(id = id, name = name.toString(), age = age)
                    viewModel.deleteUser(user)
                }
            }
        }
    }

    private fun initListener() {
        userDatabase = UserDatabase.getDatabase(this)!!

        val repository = UserRepositoryImpl(userDatabase.userDao())

        viewModel = MainViewModel(repository)

        viewModel.users.observe(this) {
            usersAdapter = UsersAdapter(it, this)
            binding.rvUsers.adapter = usersAdapter
        }

        viewModel.getAllUsers()
    }

    override fun onUserClick(user: User) {
        binding.edtId.setText(user.id.toString())
        binding.edtName.setText(user.name)
        binding.edtAge.setText(user.age.toString())
    }
}