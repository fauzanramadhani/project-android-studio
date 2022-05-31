package com.belajarkotlin.besimplebesocial

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class ForgotPasswordActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)
        auth = Firebase.auth
        val sendButton = findViewById<Button>(R.id.send_button)
        sendButton.setOnClickListener {
            sendReset()
        }

    }

    fun sendReset() {
        val emailInput = findViewById<EditText>(R.id.email_edit_text)
        val emailAddress = emailInput.text.toString().trim()
        if (emailAddress != "") {
            Firebase.auth.sendPasswordResetEmail(emailAddress)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(
                            this,
                            "Tautan telah dikirim ke $emailAddress",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        Toast.makeText(this, "$emailAddress belum terdaftar!", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
        }
        else {
            Toast.makeText(this, "Silahkan masukan Email anda", Toast.LENGTH_SHORT)
                .show()
        }
    }
}