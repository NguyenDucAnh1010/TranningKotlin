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
import com.ducanh.logindemo.model.CommonToast
import com.ducanh.logindemo.model.PasswordCharSequence
import com.google.android.material.textfield.TextInputEditText

class SignUpActivity : AppCompatActivity() {
    private lateinit var txtSignIn: TextView
    private lateinit var btnBack: ImageButton
    private lateinit var edtPassword: TextInputEditText
    private lateinit var edtFirstName: TextInputEditText
    private lateinit var edtLastName: TextInputEditText
    private lateinit var edtEmailId: TextInputEditText
    private lateinit var btnCreateAcount: AppCompatButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_sign_up)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        txtSignIn = findViewById(R.id.txtSignIn)
        btnBack = findViewById(R.id.btnBack)
        edtPassword = findViewById(R.id.edtPassword)
        edtFirstName = findViewById(R.id.edtFirstName)
        edtLastName = findViewById(R.id.edtLastName)
        edtEmailId = findViewById(R.id.edtEmailId)
        btnCreateAcount = findViewById(R.id.btnCreateAcount)

        txtSignIn.setOnClickListener {
            Intent(this, MainActivity::class.java).apply {
                startActivity(this)
                finish()
            }
        }

        btnBack.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }


        edtPassword.transformationMethod = object : PasswordTransformationMethod() {
            override fun getTransformation(source: CharSequence, view: View?): CharSequence {
                return PasswordCharSequence(source)
            }
        }

        btnCreateAcount.setOnClickListener {
            checkValidate()
        }
    }

    private fun checkValidate() {
        val firstName = edtFirstName.text.toString().trim()
        val lastName = edtLastName.text.toString().trim()
        val emailId = edtEmailId.text.toString().trim()

        when {
            firstName.isEmpty() -> {
                CommonToast(this, "First name must be not empty").showToast()
            }

            lastName.isEmpty() -> {
                CommonToast(this, "Last name must be not empty").showToast()
            }

            !Patterns.EMAIL_ADDRESS.matcher(emailId).matches() -> {
                CommonToast(this, "Email is not valid").showToast()
            }

            else -> {
                CommonToast(this, "Create Account Success!").showToast()
                Intent(this, MainActivity::class.java).apply {
                    putExtra("email", emailId)
                    startActivity(this)
                }
            }
        }
    }
}