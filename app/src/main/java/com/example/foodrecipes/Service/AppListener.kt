package com.example.foodrecipes.Service

import android.app.ActivityManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Handler
import android.os.IBinder
import android.os.Message
import android.util.Log
import java.util.*


class AppListener : Service() {
    private var isAppStart = false // 判断软件是否打开，过滤重复执行
    private var packageName_now = "" //记录当前所在应用的包名
    override fun onBind(intent: Intent): IBinder? {
        // TODO 自动生成的方法存根
        return null
    }

    override fun onCreate() {
        timer.schedule(task, 0, 500) //开始监听应用，每500毫秒查询一次，用这种方式循环比while更节约资源，而且更好用，这个项目刚开始用了while，把我坑坏了
        super.onCreate()
    }
    var handler_listen: Handler = object : Handler() {
        override fun handleMessage(msg: Message) {
            if (msg.what == 1) {
                val am = getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
                am.getRunningTasks(1).forEach {
                     Log.v("init","${it.topActivity?.packageName}")
                }
                val cn = am.getRunningTasks(1)[0].topActivity //获取到栈顶最顶层的activity所对应的应用
                val packageName = cn!!.packageName //从ComponentName对象中获取到最顶层的应用包名
                if (packageName_now != packageName) { //如果两个包名不相同，那么代表切换了应用
                    packageName_now = packageName //更新当前的应用包名
                    isAppStart = false //将是否是监听的应用包名的状态修改为false
                }
                if (packageName == "com.example.foodrecipes") { //这里举例监听QQ
                    if (!isAppStart) {
                        isAppStart = true //因为一直在循环，所以需要加个isAppStart判断防止代码重复执行
                        //。。。。逻辑处理
                    }
                }
            }
            super.handleMessage(msg)
        }
    }
    var timer = Timer()
    var task: TimerTask = object : TimerTask() {
        override fun run() {
            val message = Message()
            message.what = 1
            handler_listen.sendMessage(message)
        }
    }

    override fun onDestroy() {
        timer.cancel() //销毁服务的时候同时关闭定时器timer
        super.onDestroy()
    }
}