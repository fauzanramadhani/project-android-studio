package com.belajarkotlin.besimplebesocial

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.room.Room
import com.belajarkotlin.besimplebesocial.database.AppDatabase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var db: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        auth = Firebase.auth
        db = Firebase.database.reference
        openLogin()
    }

    fun openLogin() {
        Handler().postDelayed({
            intent = Intent(this, Login::class.java)
            startActivity(intent)
            finish()
        },3000)
    }

    fun showData() {

    }
}