package com.ducanh.logindemo

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.textfield.TextInputEditText

class MainActivity : AppCompatActivity() {
    private lateinit var sign_UpTxt: TextView
    private lateinit var emailIdEdt: TextInputEditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        sign_UpTxt = findViewById(R.id.signUpTxt)
        emailIdEdt = findViewById(R.id.emailIdEdt)

        sign_UpTxt.setOnClickListener {
            Intent(this, SignUpActivity::class.java).apply {
                startActivity(this)
                finish()
            }

        }

        intent.getStringExtra("email")?.let {
            emailIdEdt.setText(it)
        }
    }
}