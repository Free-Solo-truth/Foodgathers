package com.example.foodrecipes.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodrecipes.Lifecycle.getSocketConnect_LoginActivity
import com.example.foodrecipes.model.MsgData
import com.example.foodrecipes.repository.FoodRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.ObjectInputStream

class chatViewModel:ViewModel() {
    var reveivemsg:MutableLiveData<MsgData> = MutableLiveData()
    fun sendMsg(msgData: MsgData){
        viewModelScope.launch(Dispatchers.IO){
            FoodRepository.sendMsg(msgData)
        }
    }
    fun reciver() {
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                getSocketConnect_LoginActivity.BufferReader = ObjectInputStream(getSocketConnect_LoginActivity.socket.getInputStream())
            }
            while (true){
                FoodRepository.reciver()
                        .onStart { reveivemsg.value = null }
                        .catch { e ->
                            e.printStackTrace()
                        }
                        .collect {
                            reveivemsg.value = it
                        }


        }
      }
    }
}