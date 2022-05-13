package com.example.foodrecipes.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.foodrecipes.model.OneFavorityMessage
import com.example.foodrecipes.ui.adapter.FavorityAdapter
import com.example.foodrecipes.ui.adapter.RecipesAdapter
import com.example.foodrecipes.ui.fragment.favoritesFragment

class testViewModel: ViewModel() {
    val test1 = favoritesFragment("")
    val test2 = RecipesAdapter()
    var t1:MutableLiveData<List<OneFavorityMessage>?> = MutableLiveData(null)
    var t2:MutableLiveData<Boolean?> = MutableLiveData(null)
    var rest1: LiveData<List<OneFavorityMessage>?>? = Transformations.switchMap(t1){
        Log.v("this","${test1}")
        test1.getMutable()
    }
    var rest2:LiveData<Boolean?> = Transformations.switchMap(t2){
        test2.getChangeFav()
    }

    fun getChangeView(){
        Log.v("this3","ahahhaha")
        t1.value = t1.value
    }
    fun getChangeFav(){
        t2.value = t2.value
    }
}