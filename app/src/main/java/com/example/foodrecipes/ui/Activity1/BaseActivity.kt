package com.example.foodrecipes.ui.Activity1

import android.app.Activity
import android.app.AlertDialog
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity


/*对于每个处在栈顶的Activity设置广播*/
open class BaseActivity:AppCompatActivity() {

    private lateinit var receiver:OffLineReceiver
     fun changeStatusBarTextColor(isBlack: Boolean) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
            if (isBlack) {
                /*使用SystemUiVisibility可以将我们的状态栏的设置精细到窗口级别
                *
                * window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                        WindowManager.LayoutParams.FLAG_FULLSCREEN)  //可以通过windowManager全局的设置状态栏知道应用结束
                * */
                window.decorView.systemUiVisibility =
                    View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR //设置状态栏黑色字体
            } else {
                window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_VISIBLE //恢复状态栏白色字体
            }
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ActivityCollector.addActivity(this)
    }


    override fun onResume() {
        super.onResume()
        val IntentFilter = IntentFilter()
        IntentFilter.addAction("com.example.brodcastbestpractice.FORCE_OFFLINE")
        receiver = OffLineReceiver()
        registerReceiver(receiver,IntentFilter)
    }

    override fun onPause() {
        super.onPause()
        unregisterReceiver(receiver)
    }

    override fun onDestroy() {
        super.onDestroy()
        ActivityCollector.moveActivity(this)
    }


    inner class OffLineReceiver():BroadcastReceiver(){
        override fun onReceive(context: Context?, intent: Intent?) {
            AlertDialog.Builder(context).apply {
                setTitle("Warning")
                setMessage("If your account is logged in remotely, please check the account security")
                setCancelable(false)
                setPositiveButton("Ok"){_,_->
                    ActivityCollector.finish()
                    context?.startActivity(Intent(context, LoginActivity::class.java))
                }
                show()
            }
        }

    }
}

object ActivityCollector{
     var Activity= ArrayList<Activity>()
    fun addActivity(activity: Activity){
        Activity.add(activity)
    }
    fun moveActivity(activity: Activity){
        Activity.remove(activity)
    }
    fun finish(){
        for (activity in Activity){
            if (!activity.isFinishing){
                activity.finish()
            }
        }
        Activity.clear()
    }
}