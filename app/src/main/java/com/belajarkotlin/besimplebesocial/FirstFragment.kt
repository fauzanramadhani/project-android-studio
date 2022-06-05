package com.belajarkotlin.besimplebesocial

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.belajarkotlin.besimplebesocial.adapter.ItemAdapter
import com.belajarkotlin.besimplebesocial.data.Datasource
//import com.belajarkotlin.besimplebesocial.adapter.ItemAdapter
import com.belajarkotlin.besimplebesocial.model.DataModels
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

/**
 * A simple [Fragment] subclass.
 * Use the [FirstFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class FirstFragment() : Fragment() {
    // TODO: Rename and change types of parameters
    private lateinit var communicator: Communicator
    private lateinit var nameTextView: TextView
    private lateinit var addressTextView: TextView
    private lateinit var recycleView: RecyclerView
    private lateinit var adapterItem: ItemAdapter
    private var listPostArray: MutableList<DataModels> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_first, container, false)
        nameTextView = view.findViewById<View>(R.id.profile_name_text) as TextView
        addressTextView = view.findViewById(R.id.profile_alamat_text) as TextView
        recycleView = view.findViewById(R.id.scrollView_home) as RecyclerView
        val layoutManager = GridLayoutManager(requireContext(), 1)
        recycleView.layoutManager = layoutManager
        adapterItem = ItemAdapter(requireContext(), listPostArray)
        recycleView.adapter = adapterItem
        recycleView.setHasFixedSize(true)
        getPostData()

        // Sets the derived data (type String) in the TextView
        val name = arguments?.get("names")
        val address = arguments?.get("addresses")
        nameTextView.text = name.toString()
        addressTextView.text = address.toString()
        return view
    }

    fun getPostData() {
        FirebaseDatabase.getInstance().reference.child("Posts")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val getPostValue = snapshot.children
                    listPostArray.clear()
                    for (i in getPostValue) {
                        val newPost = i.getValue(DataModels::class.java)
                        if (newPost != null) {
                            listPostArray.add(newPost)
                        }
                    }
                    adapterItem.setData(listPostArray)
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.e("firebase", "Error getting data (Post Value)")
                }
            })
    }
}