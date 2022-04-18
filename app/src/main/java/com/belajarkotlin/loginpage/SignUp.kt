package com.belajarkotlin.loginpage

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import com.google.firebase.FirebaseApp.getInstance
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase

class SignUp : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)
        val signUpButton = findViewById<Button>(R.id.sign_up_button)
        signUpButton.setOnClickListener { signUpClick() }
        val alreadyHave = findViewById<TextView>(R.id.no_have_account)
        alreadyHave.setOnClickListener { openSignIn() }
    }

    private fun signUpClick() {
        val database = Firebase.database("https://login-page-ae3e0-default-rtdb.asia-southeast1.firebasedatabase.app")
        val myRef = database.getReference("test")
        myRef.setValue("Test Input Data")
        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                val value = dataSnapshot.getValue<String>()
                Log.d(TAG, "Value is: $value")
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException())
            }
        })
    }

    private fun openSignIn() {
        val intent = Intent(this, SignIn::class.java)
        startActivity(intent)
    }
}