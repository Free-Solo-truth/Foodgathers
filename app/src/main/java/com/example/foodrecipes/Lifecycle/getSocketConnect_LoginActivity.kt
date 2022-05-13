package com.example.foodrecipes.Lifecycle

import android.content.Context
import android.content.SharedPreferences
import android.location.Address
import android.net.ConnectivityManager
import android.net.wifi.WifiManager
import android.util.Log
import androidx.annotation.RestrictTo
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.Observer
import androidx.lifecycle.OnLifecycleEvent
import com.example.foodrecipes.model.MsgData
import com.example.foodrecipes.model.NewsState
import com.example.foodrecipes.model.UserData
import com.example.foodrecipes.model.isConnected
import com.example.foodrecipes.ui.Activity1.ActivityCollector
import com.example.foodrecipes.viewmodel.getData_fromSQLViewmodel
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.*
import java.net.*
import java.nio.Buffer
import java.util.*
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import kotlin.reflect.jvm.internal.impl.load.kotlin.JvmType

/*这里的Socket连接电脑服务器存在着一个防火墙安全的问题 将Socket连接到阿里云上面*/
/*自定义Lifecycle*/
class getSocketConnect_LoginActivity():LifecycleObserver{
    companion object{
        /*创建线程池*/
        val  ExecutorService: ExecutorService by lazy {
            Executors.newCachedThreadPool()
        }

         lateinit var PrintWriter: ObjectOutputStream
         lateinit var BufferReader: ObjectInputStream

         lateinit var socket:Socket
          val POST = 8881


    }
/*问题  生命周期：Actiivty被杀死，需要持久化的问题*/
    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun register(){
    if (isConnected.isExist){
    /*判断isfirst 是：执行  不是：不执行将isfirst值加1  当socket连接断开时 isfirst设置为true*/
        ExecutorService.execute {
            try {
                Log.v("pppp","litenfei")
                socket = Socket("你的服务ip",POST)
                Log.v("pppp","连接成功" )
                socket.soTimeout = 2000000000
                /*只能对流封装一次*/
                PrintWriter =  ObjectOutputStream( socket.getOutputStream())
//                BufferReader = ObjectInputStream(socket.getInputStream())
                Log.v("init","完成初始化")
//                PrintWriter.writeObject(MsgData("李腾飞",null,null,0,3))
//              Thread{
//                  BufferReader = ObjectInputStream(socket.getInputStream())
//                  Log.v("init","完成初始化")
//              }.start()
//                prefs.edit().apply{
//                    Log.v("init","被停止3")
//                    putBoolean("isFirst",false)
//                    apply()
//                }
                isConnected.isExist = false
            }catch (e:IOException){
                Log.v("ERROR","${e}")
            }
        }
    }
    }
    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun stop(){
        Log.v("init","stop————————")
    }
    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun unregitster(){

    }

}
