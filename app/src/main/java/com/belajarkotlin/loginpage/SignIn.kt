package com.belajarkotlin.loginpage
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast

class SignIn : AppCompatActivity() {
    var backPressedTime: Long = 0



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        val signUpButton: Button = findViewById(R.id.signup_button)
        signUpButton.setOnClickListener{openSignUp()}
        val signInButton: Button = findViewById(R.id.sign_in_button)
        signInButton.setOnClickListener{loginFireBase()}

    }

    private fun openSignUp() {
        val intent = Intent(this, SignUp::class.java)
        startActivity(intent)
    }
    private fun loginFireBase() {
        TODO("Logic Here")
    }



    override fun onBackPressed() {
        if (backPressedTime + 3000 > System.currentTimeMillis()) {
            super.onBackPressed()
            finishAffinity()
        } else {
            Toast.makeText(this, "Press back again to leave the app.", Toast.LENGTH_LONG).show()
        }
        backPressedTime = System.currentTimeMillis()
    }

}