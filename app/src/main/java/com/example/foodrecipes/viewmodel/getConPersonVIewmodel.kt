package com.example.foodrecipes.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodrecipes.model.NewsState
import com.example.foodrecipes.repository.FoodRepository
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class getConPersonVIewmodel:ViewModel() {
    var ConPersonState : MutableLiveData<NewsState> = MutableLiveData(NewsState.Empty)
    init {
       getConPersonData()
    }
    fun getConPersonData(){
        viewModelScope.launch {
            /*查询用户*/
            FoodRepository.getConPersonData()
                .onStart { ConPersonState.value = NewsState.Loading }
                .catch {e ->
                    Log.v("NewsState","${e}") }
                .collect { response ->
                    ConPersonState.value = NewsState.getConPersonData(response)
                }
        }
    }
    private fun getChatPerson(){

    }

}