package com.example.foodrecipes.network

import android.util.Log
import com.example.foodrecipes.Lifecycle.getSocketConnect_LoginActivity
import com.example.foodrecipes.model.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

import java.io.IOException
import java.io.ObjectInputStream

class getDataFromSQL{

     lateinit var  resultmsg:String
    lateinit var getFavorityInforead:ObjectInputStream
        fun User_Enroll(user:UserData?): String?{
            getSocketConnect_LoginActivity.PrintWriter.writeObject(user)
            getSocketConnect_LoginActivity.BufferReader = ObjectInputStream(getSocketConnect_LoginActivity.socket.getInputStream())
            Log.v("close", "VerifyPasword:-----")
            while (true) {
                val Object_UserData = getSocketConnect_LoginActivity.BufferReader.readObject() as MsgData
                /*问题2 handler问题*/
    //                LoginActivity().handler.sendMessage(
    //                        Message().apply {
    //                    Log.v("Error","成功")
    //                    data = Bundle().apply {
    //                        putString("result",Object_UserData.content)
    //                    }
    //                })
                return Object_UserData.content
            }
    //            Log.v("Error","${resultmsg}")
    //            LoginActivity().message= Object_UserData.content
    //            Log.v("Error2","${LoginActivity().message}")
            return null
        }

    //兼职查询message数据库
    fun sendMsg(msgData: MsgData){

        getSocketConnect_LoginActivity.PrintWriter.writeObject(msgData)
    }
    fun reciver():MsgData{
        while (true){
            var result = getSocketConnect_LoginActivity.BufferReader.readObject() as MsgData
            return result
        }
    }

    fun getAlluserInfo(email: String?):SendUserData{
        getSocketConnect_LoginActivity.PrintWriter.writeObject(MsgData(email,null,null,0,9))
        getSocketConnect_LoginActivity.BufferReader = ObjectInputStream(getSocketConnect_LoginActivity.socket.getInputStream())
        while (true){
            var result = getSocketConnect_LoginActivity.BufferReader.readObject() as SendUserData
            return result
        }
    }

    fun insertfriend(User:Relationship):String{
        getSocketConnect_LoginActivity.PrintWriter.writeObject(User)
        getSocketConnect_LoginActivity.BufferReader = ObjectInputStream(getSocketConnect_LoginActivity.socket.getInputStream())
        while (true){
            var result = getSocketConnect_LoginActivity.BufferReader.readObject() as MsgData
            return result.content
        }
    }
        fun getUserInfo(username: String?, email:String?):UserData? {
            /*这里需要注意的是this和类对象锁的区别
            * this:只能锁住同一个对象，也就是两个线程调用同一个对象的时候才会同步
            * 类对象锁：只要是这个类的对象，都会形成同步
            * */
            /*持有这个对象的锁,分开几个页面的数据的请求*/
            synchronized(getDataFromSQL::javaClass) {
                getSocketConnect_LoginActivity.PrintWriter.writeObject(MsgData(username, null, email, 0, 3))
                getSocketConnect_LoginActivity.BufferReader = ObjectInputStream(getSocketConnect_LoginActivity.socket.getInputStream())
                Log.v("close", "VerifyPasword:-----")
                while (true) {
                    /*问题四 read time out的问题和代码的编写有关*/
                    var Object_UserData: UserData = getSocketConnect_LoginActivity.BufferReader.readObject() as UserData
                    if (Object_UserData != null) {
                        Log.v("init", "${Object_UserData.name}")
                        return Object_UserData
                    }
                }
                return null
            }
            }
//            Log.v("Error","${resultmsg}")
//            LoginActivity().message= Object_UserData.content
//            Log.v("Error2","${LoginActivity().message}")
        fun VerifyPasword(username:String,password: String): String?{
            getSocketConnect_LoginActivity.PrintWriter.writeObject(MsgData(username,"系统",password,0,2))
            getSocketConnect_LoginActivity.BufferReader = ObjectInputStream(getSocketConnect_LoginActivity.socket.getInputStream())
            Log.v("close", "VerifyPasword:-----")
            while (true) {
                val Object_UserData = getSocketConnect_LoginActivity.BufferReader.readObject() as MsgData
                /*问题2 handler问题*/
//                LoginActivity().handler.sendMessage(
//                        Message().apply {
//                    Log.v("Error","成功")
//                    data = Bundle().apply {
//                        putString("result",Object_UserData.content)
//                    }
//                })
               return Object_UserData.content
            }
//            Log.v("Error","${resultmsg}")
//            LoginActivity().message= Object_UserData.content
//            Log.v("Error2","${LoginActivity().message}")
           return null
        }
    fun   getFavorityInfo(email:String?):SendList?{
        synchronized(getDataFromSQL::class.java) {
            try {
                getSocketConnect_LoginActivity.PrintWriter.writeObject(MsgData(email, null, null, 0, 5))
                Log.v("fasong,", "type：5-》成功")
                getSocketConnect_LoginActivity.BufferReader = ObjectInputStream(getSocketConnect_LoginActivity.socket.getInputStream())
                while (true) {
                    val favorityAllInfo = getSocketConnect_LoginActivity.BufferReader.readObject() as SendList
                    return favorityAllInfo
                }
            } catch (e: IOException) {
                e.printStackTrace()
            } finally {

            }
        }
        return null
    }
    fun deleteFavorityInfo(email: String?,favorityTitle:String):String{
        getSocketConnect_LoginActivity.PrintWriter.writeObject(MsgData(email,null,favorityTitle,0,6))
        getSocketConnect_LoginActivity.BufferReader = ObjectInputStream(getSocketConnect_LoginActivity.socket.getInputStream())
        while (true){
            var result = getSocketConnect_LoginActivity.BufferReader.readObject() as MsgData
            return result.content
        }
        return "取消错误"
    }

    fun insertFavorityInfo(favorityMessage: Carrier_FavorityMessage):String{
        getSocketConnect_LoginActivity.PrintWriter.writeObject(favorityMessage)
        getSocketConnect_LoginActivity.BufferReader = ObjectInputStream(getSocketConnect_LoginActivity.socket.getInputStream())
        while (true){
            var result = getSocketConnect_LoginActivity.BufferReader.readObject() as MsgData
            return result.content
        }
        return "插入错误"
    }

        fun getChatMessage() {
        }

    fun save_Dynamic(email: String?,dynamicMessage: Carrier_DynamicMessage):String{
        getSocketConnect_LoginActivity.PrintWriter.writeObject(dynamicMessage)
        getSocketConnect_LoginActivity.BufferReader = ObjectInputStream(getSocketConnect_LoginActivity.socket.getInputStream())
        while (true){
            var result = getSocketConnect_LoginActivity.BufferReader.readObject() as MsgData
            return result.content
        }
        return "插入错误"
    }
    fun getDynamicInfo(email: String?):Carrier_DynamicMessage{
        getSocketConnect_LoginActivity.PrintWriter.writeObject(MsgData(email,null,null,0,7))
        getSocketConnect_LoginActivity.BufferReader = ObjectInputStream(getSocketConnect_LoginActivity.socket.getInputStream())
        while (true){
            var result = getSocketConnect_LoginActivity.BufferReader.readObject() as Carrier_DynamicMessage
            return result
        }
    }

     fun getChatPerson(username:String):SendRelationship{
        getSocketConnect_LoginActivity.PrintWriter.writeObject(MsgData(username,null,null,0,8))
        getSocketConnect_LoginActivity.BufferReader = ObjectInputStream(getSocketConnect_LoginActivity.socket.getInputStream())
        while (true){
            var result = getSocketConnect_LoginActivity.BufferReader.readObject() as SendRelationship
            return result
        }
    }

    fun addFriend(friend:SendRelationship):String{
        getSocketConnect_LoginActivity.PrintWriter.writeObject(friend)
        getSocketConnect_LoginActivity.BufferReader = ObjectInputStream(getSocketConnect_LoginActivity.socket.getInputStream())
        while (true){
            var result = getSocketConnect_LoginActivity.BufferReader.readObject() as MsgData
            return result.content
        }
    }
    fun update_userimag(UserInfo:UpdateUser):String{
        getSocketConnect_LoginActivity.PrintWriter.writeObject(UserInfo)
        getSocketConnect_LoginActivity.BufferReader = ObjectInputStream(getSocketConnect_LoginActivity.socket.getInputStream())
        while (true){
            var result = getSocketConnect_LoginActivity.BufferReader.readObject() as MsgData
            return result.content
        }
    }
}