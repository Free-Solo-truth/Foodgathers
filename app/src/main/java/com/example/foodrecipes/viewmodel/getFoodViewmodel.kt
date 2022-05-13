package com.example.foodrecipes.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodrecipes.model.NewsState
import com.example.foodrecipes.repository.FoodRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class getFoodViewmodel:ViewModel() {
    var foodState :MutableLiveData<NewsState> =MutableLiveData(NewsState.Empty)
    //是想UI层和data层的数据的交互
    fun getFoodNews (
                     type:String,
                     diet:String,
                     cuisine:String){
        viewModelScope.launch {
            Log.v("ppp","Viewmodel没有错误")
            FoodRepository.getFoodNews(type,diet,cuisine)
                    .onStart {
                        foodState.value= NewsState.Loading
                    }
                    .catch {e ->
                        foodState.value= NewsState.Failure(e)
                    }
                    .collect {response ->
                        foodState.value = NewsState.Success(response)
                        Log.v("ppp","${response}")
                    } //If any exception occurs during collect or in the provided flow, this exception is rethrown from this method.
            Log.v("ppp", "${foodState.value}")

        }
    }
}