package com.belajarkotlin.besimplebesocial

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.belajarkotlin.besimplebesocial.model.UserModel
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class SignUp : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var emails: EditText
    private lateinit var passwords: EditText
    private lateinit var db: DatabaseReference
//    private val dbLocal =
//        Room.databaseBuilder(applicationContext, AppDatabase::class.java, "tb_user").build()
//    private val userDao = dbLocal.userDao()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        val loginButton = findViewById<TextView>(R.id.buat_baru_button)
        loginButton.setOnClickListener { openLogin() }
        val signupButton = findViewById<Button>(R.id.signup_button)
        signupButton.setOnClickListener { signUpUser() }
        emails = findViewById(R.id.email_edit_text_daftar)
        passwords = findViewById(R.id.password_edit_text_daftar)
        db = Firebase.database.reference
        auth = Firebase.auth

    }

    fun openLogin() {
        intent = Intent(this, Login::class.java)
        startActivity(intent)
        finish()
    }

    fun regisUser(user: UserModel) {
        val uidFromAuthentication = auth.currentUser
        val newUser = UserModel(
            user.email,
            user.nik,
            user.name,
            user.noHouse,
            user.noTelp,
            user.hasData
        )
//        userDao.updateUser(newUser.toUserEntity())
        db.child("Users").child(uidFromAuthentication?.uid.toString()).setValue(newUser)
    }

    fun signUpUser() {
        val email = emails.text.toString().trim()
        val password = passwords.text.toString()
        if (email == "") {
            Toast.makeText(this, "Email tidak boleh kosong!", Toast.LENGTH_LONG).show()
        } else if (password == "") {
            Toast.makeText(this, "Password tidak boleh kosong!", Toast.LENGTH_LONG).show()
        }
        else {
            val nullEmptyString = "Data Kosong"
            val nullEmptyInt = (0..10000000).random()
            val EmptyData = false
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "createUserWithEmail:success")
                        val user = auth.currentUser
                        val emailCurrent = user?.email
                        val newUser: UserModel = UserModel(
                            email = auth.currentUser?.email.toString(),
                            name = nullEmptyString,
                            nik = nullEmptyInt.toLong(),
                            noHouse = nullEmptyString,
                            noTelp = nullEmptyInt.toLong(),
                            hasData = EmptyData
                        )
                        regisUser(newUser)
                        val credential = EmailAuthProvider
                            .getCredential(email, password)
                        user?.reauthenticate(credential)
                            ?.addOnCompleteListener { Log.d(TAG, "User re-authenticated.") }
//                    val userEntity = UserEntity(
//                        email = email!!,
//                    )
//                    userDao.insertUser(userEntity)
                        Toast.makeText(
                            this,
                            "Berhasil mendaftar sebagai: $emailCurrent",
                            Toast.LENGTH_SHORT
                        )
                            .show()
                        startActivity(Intent(this, UserInfo::class.java))
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "createUserWithEmail:failure", task.exception)
                        Toast.makeText(
                            baseContext,
                            "Gagal Mendaftar, Silahkan periksa kembali Email dan Password anda, atau hubungi admin",
                            Toast.LENGTH_LONG
                        ).show()
                        startActivity(Intent(this, SignUp::class.java))
                        finish()
                    }
                }
        }
    }
}