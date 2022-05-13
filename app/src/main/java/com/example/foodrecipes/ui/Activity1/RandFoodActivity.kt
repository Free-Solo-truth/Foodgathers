package com.example.foodrecipes.ui.Activity1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.foodrecipes.R
import com.example.foodrecipes.model.OneFavorityMessage

class RandFoodActivity : AppCompatActivity(){
    lateinit var FavorityFoodList:List<OneFavorityMessage>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rand_food)
        
    }


}