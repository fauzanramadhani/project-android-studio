package com.belajarkotlin.besimplebesocial

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment

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
    private lateinit var myTextView: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_first, container, false)
        myTextView = view.findViewById<View>(R.id.profile_name_text) as TextView

        // Sets the derived data (type String) in the TextView
        val name = arguments?.get("names")
        myTextView.text = name.toString()
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val name = arguments?.get("names")
        Log.e("xx", "$name")
    }
}