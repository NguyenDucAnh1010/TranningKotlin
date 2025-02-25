package com.ducanh.logindemo

import android.content.Intent
import android.os.Bundle
import android.text.method.PasswordTransformationMethod
import android.util.Patterns
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.textfield.TextInputEditText

class SignUpActivity : AppCompatActivity() {
    private lateinit var signInTxt: TextView
    private lateinit var backBtn: ImageButton
    private lateinit var passwordEdt: TextInputEditText
    private lateinit var firstNameEdt: TextInputEditText
    private lateinit var lastNameEdt: TextInputEditText
    private lateinit var emailIdEdt: TextInputEditText
    private lateinit var createAcountBtn: AppCompatButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_sign_up)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        signInTxt = findViewById(R.id.signInTxt)
        backBtn = findViewById(R.id.backBtn)
        passwordEdt = findViewById(R.id.passwordEdt)
        firstNameEdt = findViewById(R.id.firstNameEdt)
        lastNameEdt = findViewById(R.id.lastNameEdt)
        emailIdEdt = findViewById(R.id.emailIdEdt)
        createAcountBtn = findViewById(R.id.createAcountBtn)

        signInTxt.setOnClickListener {
            Intent(this, MainActivity::class.java).apply {
                startActivity(this)
                finish()
            }
        }

        backBtn.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }


        passwordEdt.transformationMethod = object : PasswordTransformationMethod() {
            override fun getTransformation(source: CharSequence, view: View?): CharSequence {
                return PasswordCharSequence(source)
            }
        }

        createAcountBtn.setOnClickListener {
            checkValid()
        }
    }

    private fun checkValid() {
        val firstName = firstNameEdt.text.toString().trim()
        val lastName = lastNameEdt.text.toString().trim()
        val emailId = emailIdEdt.text.toString().trim()

        when {
            firstName.isEmpty() -> {
                Toast.makeText(this, "First name must be not empty", Toast.LENGTH_SHORT).show()
            }

            lastName.isEmpty() -> {
                Toast.makeText(this, "First name must be not empty", Toast.LENGTH_SHORT).show()
            }

            !Patterns.EMAIL_ADDRESS.matcher(emailId).matches() -> {
                Toast.makeText(this, "Email is not valid", Toast.LENGTH_SHORT).show()
            }

            else -> {
                Toast.makeText(this, "Create Account Success!", Toast.LENGTH_SHORT).show()
                Intent(this, MainActivity::class.java).apply {
                    putExtra("email", emailId)
                    startActivity(this)
                }
            }
        }
    }

    class PasswordCharSequence(private val source: CharSequence) : CharSequence {
        override val length: Int
            get() = source.length

        override fun get(index: Int): Char {
            return '*'
        }

        override fun subSequence(startIndex: Int, endIndex: Int): CharSequence {
            return source.subSequence(startIndex, endIndex)
        }
    }
}