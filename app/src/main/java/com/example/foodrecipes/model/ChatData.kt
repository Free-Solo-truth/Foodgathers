package com.example.foodrecipes.model

import android.content.Context
import java.io.Serializable

public class ChatData(var sendID:String, var receiveID:String ,var context: String,var type:Int,var what:Int):Serializable{
    companion object{
        const val SEND_TYPE = 1
        const val REVICE_TYPE = 0
        const val noconnect = 2
        const val queryMessage = 3
    }
}