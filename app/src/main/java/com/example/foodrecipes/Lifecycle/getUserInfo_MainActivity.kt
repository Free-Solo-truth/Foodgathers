package com.example.foodrecipes.Lifecycle

import android.content.Intent
import android.content.SharedPreferences
import android.os.Handler
import android.os.Message
import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.Observer
import androidx.lifecycle.OnLifecycleEvent
import com.example.foodrecipes.model.MsgData
import com.example.foodrecipes.model.UserData
import com.example.foodrecipes.ui.Activity1.AddFriendActivity
import com.example.foodrecipes.ui.Activity1.ChatActivity
import com.example.foodrecipes.ui.MainActivity
import com.example.foodrecipes.ui.adapter.addFriendAdapter
import com.example.foodrecipes.viewmodel.getData_fromSQLViewmodel
import java.io.ObjectInputStream

class getUserInfo_MainActivity(val email:String?):LifecycleObserver {

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun unregitster(){
        Log.v("this","退出连接" )
    }
}