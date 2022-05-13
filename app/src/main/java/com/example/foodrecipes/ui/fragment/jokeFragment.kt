package com.example.foodrecipes.ui.fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.foodrecipes.R
import com.example.foodrecipes.databinding.FragmentJokeBinding
import com.example.foodrecipes.ui.Activity1.DynamicActivity


class jokeFragment : Fragment() {
    lateinit var binding:FragmentJokeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentJokeBinding.inflate(inflater,container,false)
        binding.friendCircle.setOnClickListener {
            startActivity(Intent(activity,DynamicActivity::class.java).putExtra("email",activity?.intent?.getStringExtra("email")))
            activity?.overridePendingTransition(R.anim.translate_in,R.anim.translate_out)
        }
        return binding.root
    }


}