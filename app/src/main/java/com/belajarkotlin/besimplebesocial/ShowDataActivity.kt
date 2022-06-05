package com.belajarkotlin.besimplebesocial

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.service.autofill.UserData
import android.util.Log
import android.widget.LinearLayout
import android.widget.TextView
import androidx.navigation.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.database.core.view.Change
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.net.ProtocolFamily

class ShowDataActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var db: DatabaseReference
    private lateinit var nik: String
    private lateinit var name:String
    private lateinit var address: String
    private lateinit var phone: String
    private lateinit var gajiFamily: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_data)
        auth = Firebase.auth
        db = Firebase.database.reference
        val btnEdit = findViewById<LinearLayout>(R.id.linear_Layout_show_7)
        btnEdit.setOnClickListener {
            openEditData()
        }
        val btnBack = findViewById<LinearLayout>(R.id.linear_layout_show_7)
        btnBack.setOnClickListener {
            backClick()
        }
        takeData()
    }

    fun openEditData() {
        startActivity(Intent(this, UserInfo::class.java))
    }
    fun backClick() {
        onBackPressed()
    }
    fun takeData() {
        val uidFromAuthentication = auth.currentUser?.uid.toString()
        val ref = FirebaseDatabase.getInstance().reference.child("Users").child(uidFromAuthentication)

        ref.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                nik = snapshot.child("nik").getValue(Long::class.java).toString()
                name = snapshot.child("name").getValue(String::class.java).toString()
                address = snapshot.child("noHouse").getValue(String::class.java).toString()
                phone = snapshot.child("noTelp").getValue(Long::class.java).toString()
                gajiFamily = snapshot.child("gajiFamily").getValue(Long::class.java).toString()
                showData(nik,name,address,phone,gajiFamily)
            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }
    fun showData(nikShow:String, nameShow: String, addressShow:String, phoneShow:String, gajiFamily: String) {
        val nikView = findViewById<TextView>(R.id.show_nik_value)
        val nameView = findViewById<TextView>(R.id.show_name_value)
        val addressView = findViewById<TextView>(R.id.show_alamat_value)
        val phoneView = findViewById<TextView>(R.id.show_telp_value)
        val gajiFamilyView = findViewById<TextView>(R.id.show_gaji_value)
        nikView.setText(nikShow)
        nameView.setText(nameShow)
        addressView.setText(addressShow)
        phoneView.setText(phoneShow)
        gajiFamilyView.setText(gajiFamily)
    }
}