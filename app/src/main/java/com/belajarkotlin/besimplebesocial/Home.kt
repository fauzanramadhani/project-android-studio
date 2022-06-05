package com.belajarkotlin.besimplebesocial

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.belajarkotlin.besimplebesocial.data.Datasource
import com.belajarkotlin.besimplebesocial.model.DataModels
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.time.LocalDate


class Home : AppCompatActivity(), Communicator {
    private lateinit var auth: FirebaseAuth
    private lateinit var db: DatabaseReference
    private lateinit var nik: String
    private lateinit var name: String
    private lateinit var address: String
    private lateinit var phone: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        auth = Firebase.auth
        db = Firebase.database.reference
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        val navController = findNavController(R.id.fragmentContainerView)
        bottomNavigationView.setupWithNavController(navController)
        cekData()
        takeData()
    }

    override fun signOut() {
        auth = Firebase.auth
        auth.signOut()
        startActivity(Intent(this, Login::class.java))
        finish()
    }

    override fun changeInfo() {
        startActivity(Intent(this, ShowDataActivity::class.java))
    }

    override fun backClick() {
        findNavController(R.id.fragmentContainerView).popBackStack()
    }

    fun cekData() {
        val uidFromAuthentication = auth.currentUser?.uid.toString()
        val ref =
            FirebaseDatabase.getInstance().reference.child("Users").child(uidFromAuthentication)
                .child("hasData")
        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val getHasData = snapshot.getValue(Boolean::class.java)
                if (getHasData == false) {
                    openUserInfoActivity()
                } else if (getHasData == null) {
                    openUserInfoActivity()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }

    fun openUserInfoActivity() {
        startActivity(Intent(this, UserInfo::class.java))
        finish()
    }

    override fun changePassword() {
        startActivity(Intent(this, ChangePasswordActivity::class.java))
    }

    override fun onClickWhatsapp() {
        try {
            val msg = "Saya Butuh Bantuan"
            val uri: Uri = Uri.parse("smsto:" + 6282260012008)
            val sendIntent = Intent(Intent.ACTION_SENDTO, uri)
            sendIntent.putExtra(Intent.EXTRA_TEXT, msg)
            sendIntent.setPackage("com.whatsapp")
            startActivity(sendIntent)
            Toast.makeText(
                this,
                "Anda akan diarahkan ke WhatsApp Customer Service kami",
                Toast.LENGTH_LONG
            ).show()
        } catch (e: PackageManager.NameNotFoundException) {
            Toast.makeText(this, "WhatsApp not Installed", Toast.LENGTH_SHORT).show()
        }
    }

    fun takeData() {
        val uidFromAuthentication = auth.currentUser?.uid.toString()
        val ref =
            FirebaseDatabase.getInstance().reference.child("Users").child(uidFromAuthentication)

        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                name = snapshot.child("name").getValue(String::class.java).toString()
                address = snapshot.child("noHouse").getValue(String::class.java).toString()
                getData(name, address)
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }

    fun getData(nameUser: String, addressUser: String) {
        val navController = findNavController(R.id.fragmentContainerView)
        navController.setGraph(R.navigation.my_nav, Bundle().apply {
            putString("names", nameUser)
            putString("addresses", addressUser)
        })
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun createPost(descriptionText: String) {
        val uidFromAuthentication = auth.currentUser?.uid.toString()
        FirebaseDatabase.getInstance().reference.child("Users").child(uidFromAuthentication).child("name")
                .get().addOnSuccessListener {
            val getPostName = it.value
                if (descriptionText != "") {
                    val newPost = DataModels(
                        name = getPostName.toString(),
                        uid = uidFromAuthentication,
                        description = descriptionText,
                        timePost = LocalDate.now().toString()
                    )
                    pushPostData(newPost)
                    Toast.makeText(this, "Postingan anda telah dibuat", Toast.LENGTH_SHORT).show()
                }
                else {
                    Toast.makeText(this, "Postingan anda masih kosong", Toast.LENGTH_SHORT).show()
                }
        }.addOnFailureListener{
            Log.e("firebase", "Error getting data (Post Name", it)
        }
    }

    fun pushPostData(user: DataModels) {
        val newPost = DataModels(
            user.name,
            user.uid,
            user.description,
            user.timePost
        )
//        userDao.updateUser(newUser.toUserEntity())
        val ref = FirebaseDatabase.getInstance().reference
        ref.child("Next Post ID").get().addOnSuccessListener {
            val getPostIdValue = it.value
            db.child("Posts").child("$getPostIdValue")
                .setValue(newPost)
        }.addOnFailureListener{
            Log.e("firebase", "Error getting data (Next Post ID)", it)
        }
        getPostId()
    }

    fun getPostId() {
        val ref = FirebaseDatabase.getInstance().reference
        ref.child("Next Post ID").get().addOnSuccessListener {
            val getPostNumber = it.value
                setChangeOnPostId(getPostNumber.toString())
        }.addOnFailureListener{
        }
    }
    fun setChangeOnPostId(cetak:String) {
        db.child("Next Post ID").setValue(cetak.toInt() + 1)
    }
}