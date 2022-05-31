package com.belajarkotlin.besimplebesocial

import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class ChangePasswordActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_password)
        auth = Firebase.auth
        val submitButton = findViewById<Button>(R.id.submit_button)
        submitButton.setOnClickListener {
            changePassword()
        }
        val backButton = findViewById<LinearLayout>(R.id.linear_layout_show_7)
        backButton.setOnClickListener {
            onBackPressed()
        }
    }

    fun changePassword() {
        val user = auth.currentUser
        val newPassword = findViewById<EditText>(R.id.password_edit_text).text.toString()

        if (newPassword != "") {

            user!!.updatePassword(newPassword)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this, "Password anda telah diperbarui", Toast.LENGTH_SHORT)
                            .show()
                    }
                    else {
                        Toast.makeText(this, "Password baru harus berbeda dengan yang Password lama", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
        }
        else {
            Toast.makeText(this, "Silahkan masukan Password baru anda", Toast.LENGTH_SHORT)
                .show()
        }
    }
}