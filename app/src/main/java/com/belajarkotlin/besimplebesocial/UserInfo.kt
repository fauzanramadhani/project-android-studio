package com.belajarkotlin.besimplebesocial

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.room.Room
import com.belajarkotlin.besimplebesocial.database.AppDatabase
import com.belajarkotlin.besimplebesocial.model.UserModel
import com.belajarkotlin.besimplebesocial.model.toUserEntity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlin.reflect.full.memberProperties

class UserInfo : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var db:DatabaseReference
    private lateinit var nikInput: EditText
    private lateinit var nameInput: EditText
    private lateinit var noHouseInput: EditText
    private lateinit var noTelpInput: EditText
    var backPressedTime: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_info)
        val submitButton = findViewById<Button>(R.id.submit_button)
        submitButton.setOnClickListener{submitInfo()}
        val btnBack = findViewById<LinearLayout>(R.id.linear_layout_info_back)
        btnBack.setOnClickListener {
            backClick()
        }
        displayEmail()
        db = Firebase.database.reference
        nikInput = findViewById(R.id.nik_edit_text)
        nameInput = findViewById(R.id.nama_edit_text)
        noHouseInput = findViewById(R.id.no_rumah_edit_text)
        noTelpInput = findViewById(R.id.telefon_edit_text)
    }
    fun regisUser(user: UserModel) {
//        val dbLocal =
//            Room.databaseBuilder(applicationContext, AppDatabase::class.java, "tb_user").build()
//        val userDao = dbLocal.userDao()
        val newUser = UserModel(
            auth.currentUser?.email ?: user.email,
            user.nik,
            user.name,
            user.noHouse,
            user.noTelp,
            user.hasData
        )

        val newUserMap = newUser.asMap()
//        userDao.updateUser(newUser.toUserEntity())
        db.child("Users").child(auth.currentUser?.uid.toString()).updateChildren(newUserMap).addOnSuccessListener {
            Toast.makeText(this, "Data Berhasil di Update", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this,Home::class.java))
            finish()
        }.addOnFailureListener{
            Toast.makeText(this, "Terjadi Kesalahan Data Gagal di Update", Toast.LENGTH_SHORT).show()
        }
    }

    inline fun <reified T : Any> T.asMap() : Map<String, Any?> {
        val props = T::class.memberProperties.associateBy { it.name }
        return props.keys.associateWith { props[it]?.get(this) }
    }

    private fun signOut() {
        auth = Firebase.auth
        Firebase.auth.signOut()
        finish()
    }

    private fun submitInfo() {
        if (nikInput.text.toString() == "") {
            Toast.makeText(this, "NIK tidak boleh kosong!", Toast.LENGTH_LONG).show()
        } else if (nameInput.text.toString() == "") {
            Toast.makeText(this, "Nama Lengkap tidak boleh kosong!", Toast.LENGTH_LONG).show()
        } else if (noHouseInput.text.toString() == "") {
            Toast.makeText(this, "Nomor Rumah tidak boleh kosong!", Toast.LENGTH_LONG).show()
        } else if (noTelpInput.text.toString() == "") {
            Toast.makeText(this, "Nomor Telepon tidak boleh kosong!", Toast.LENGTH_LONG).show()
        }
        else {
            val noEmptyData = true
            val newUserMap: UserModel = UserModel(
                email = auth.currentUser?.email.toString(),
                name = nameInput.text.toString(),
                nik = nikInput.text.toString().toLong(),
                noHouse = noHouseInput.text.toString(),
                noTelp = noTelpInput.text.toString().toLong(),
                hasData = noEmptyData
            )
            regisUser(newUserMap)
        }
    }
    private fun displayEmail() {
        auth = Firebase.auth
        val user = auth.currentUser
        val email = user!!.email
        val displayEmail = findViewById<EditText>(R.id.email_edit_text_info)
        displayEmail.hint = email
    }

    fun cekData(hasData: Boolean?) {
        if (hasData==false) {
            signOut()
            startActivity(Intent(this,Login::class.java))
            finish()
        }
        else {
            super.onBackPressed()
        }
    }

    fun backClick() {
        val uidFromAuthentication = auth.currentUser?.uid.toString()
        val ref = FirebaseDatabase.getInstance().reference.child("Users").child(uidFromAuthentication).child("hasData")
        ref.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val getHasData = snapshot.getValue(Boolean::class.java)
                cekData(getHasData)
            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }

    override fun onBackPressed() {
        backClick()
        super.onBackPressed()
    }

}