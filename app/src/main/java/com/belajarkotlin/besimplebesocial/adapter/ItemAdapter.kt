package com.belajarkotlin.besimplebesocial.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.belajarkotlin.besimplebesocial.R
import com.belajarkotlin.besimplebesocial.model.DataModels
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class ItemAdapter (
    private val context: Context,
    private var dataset: MutableList<DataModels>) : RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {
    private lateinit var auth: FirebaseAuth
    private lateinit var db: DatabaseReference
    private var listPostArray: MutableList<DataModels> = mutableListOf()

    class ItemViewHolder(
        private val view: View
    ) : RecyclerView.ViewHolder(view) {
        val profilesNameText : TextView = view.findViewById(R.id.profile_name_text)
        val descriptionText : TextView = view.findViewById(R.id.post_descriptions)
        val postDate : TextView = view.findViewById(R.id.hours_list_1)

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item, parent, false)
        return ItemViewHolder(adapterLayout)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = dataset[position]
        holder.profilesNameText.text = item.name
        holder.descriptionText.text = item.description
        holder.postDate.text = item.timePost
        holder.itemView
    }

    override fun getItemCount(): Int {
        return dataset.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(data : MutableList<DataModels>) {
        dataset = data
        Log.e("cetak", dataset.toString())
        notifyDataSetChanged()
    }
}