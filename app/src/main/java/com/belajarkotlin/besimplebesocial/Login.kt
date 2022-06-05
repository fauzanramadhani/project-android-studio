package com.belajarkotlin.besimplebesocial

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.room.Room
import com.belajarkotlin.besimplebesocial.database.AppDatabase
import com.belajarkotlin.besimplebesocial.model.UserModel
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class Login : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var db: DatabaseReference
    var backPressedTime: Long = 0
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        auth = Firebase.auth
        db = Firebase.database.reference
        val signUpButton = findViewById<TextView>(R.id.buat_baru_button)
        signUpButton.setOnClickListener{openSignUp()}
        val signInButton = findViewById<Button>(R.id.login_button)
        signInButton.setOnClickListener { openSignIn() }
        val forgotPassword = findViewById<TextView>(R.id.lupa_password_button)
        forgotPassword.setOnClickListener {
            openForgotPassword()
        }
    }

    private fun openForgotPassword() {
        startActivity(Intent(this, ForgotPasswordActivity::class.java))
    }

    fun openSignUp() {
        intent = Intent(this, SignUp::class.java)
        startActivity(intent)
    }

    fun openSignIn() {
        val emails = findViewById<EditText>(R.id.email_edit_text)
        val passwords = findViewById<EditText>(R.id.password_edit_text)
        val email = emails.text.toString().trim()
        val password = passwords.text.toString()

        if (email == "") {
            Toast.makeText(this, "Email tidak boleh kosong!", Toast.LENGTH_LONG).show()
        } else if (password == "") {
            Toast.makeText(this, "Password tidak boleh kosong!", Toast.LENGTH_LONG).show()
        } else {
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "Success SignInWithEmail : $email")
                        val user = auth.currentUser
                        val emailCurrent = user!!.email
                        val credential = EmailAuthProvider
                            .getCredential(email, password)
                        user.reauthenticate(credential)
                            .addOnCompleteListener { Log.d(TAG, "User re-authenticated.") }
                        Toast.makeText(this, "Berhasil masuk sebagai: $emailCurrent", Toast.LENGTH_SHORT)
                            .show()
                        startActivity(Intent(this, Home::class.java))
                        finish()
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "signInWithEmail:failure", task.exception)
                        Toast.makeText(this, "Email atau Password anda salah!", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
        }
    }

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        if(currentUser != null){
            startActivity(Intent(this, Home::class.java))
            finish()
        }
    }

    override fun onBackPressed() {
        if (backPressedTime + 3000 > System.currentTimeMillis()) {
            super.onBackPressed()
            finishAffinity()
        } else {
            Toast.makeText(this, "Tekan sekali lagi untuk keluar Aplikasi", Toast.LENGTH_LONG).show()
        }
        backPressedTime = System.currentTimeMillis()
    }
}