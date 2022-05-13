package com.example.foodrecipes.ui.Activity1

import android.graphics.Rect
import android.os.Bundle
import android.os.Message
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.foodrecipes.Lifecycle.getSocketConnect_LoginActivity
import com.example.foodrecipes.R

import com.example.foodrecipes.model.ChatData
import com.example.foodrecipes.model.MsgData
import com.example.foodrecipes.model.Save_temp
import com.example.foodrecipes.model.User_info
import com.example.foodrecipes.network.getDataFromSQL
import com.example.foodrecipes.ui.adapter.ChatAdapter
import kotlinx.android.synthetic.main.activity_chat.*
import java.io.IOException
import java.util.*
import kotlin.random.Random


class ChatActivity : BaseActivity() {

    companion object{
        private final lateinit var tel:String
        private final lateinit var one_msg:MsgData
        private  var msg = mutableListOf<MsgData>()
    }
    private lateinit var message:List<String>
    private lateinit var email:String
    private lateinit var friendname:String
    private val chatViewModel = com.example.foodrecipes.viewmodel.chatViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_chat)

//        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN or
//                WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
        intent.getStringExtra("tel")?.let {
            tel = it
        }
        intent.getStringExtra("name")?.let {
            Log.v("litenfei","lttenfei")
            ChatPersonname.text = it
            friendname = it
        }
        intent.getStringExtra("email")?.let {
            email = it
            //获取离线消息
            getSavemessage()
        }
        ChatRecycleView.layoutManager = LinearLayoutManager(baseContext)
        ChatRecycleView.adapter = ChatAdapter(msg,friendname)

        send_msgButton.setOnClickListener {
            /*注意这里的线程池对于线程的重用，对于重复的任务不需要开启新的线程*/

                    Send_msg()
        }
        /*一直监听消息*/
            receive_msg()

//        判断键盘收起或是弹出
        setListenerToRootView()
    }

    private fun getSavemessage(){
        chatViewModel.sendMsg( MsgData(User_info.User?.email,email, Edit_SendMsg.text.toString(),ChatData.queryMessage,1))
    }

    override fun onResume() {
        super.onResume()
    }
    /*通过监听键盘的收起还是弹出设置我们的额UI界面*/
    private fun setListenerToRootView() {
        /*获取到根视图*/
        val rootView: View =
            getWindow().getDecorView().findViewById(android.R.id.content)
        rootView.viewTreeObserver.addOnGlobalLayoutListener {
            val mKeyboardUp = isKeyboardShown(rootView)
                    if (mKeyboardUp&&Linearlayout_SendMsg.paddingBottom<400) {
            //键盘弹出

            Log.v("this","${mKeyboardUp}}")
            Toast.makeText(getApplicationContext(), "键盘弹出", Toast.LENGTH_SHORT).show();
            Linearlayout_SendMsg.setPadding(Linearlayout_SendMsg.paddingLeft,Linearlayout_SendMsg.paddingTop,
                Linearlayout_SendMsg.paddingRight,
                Linearlayout_SendMsg.paddingBottom+800
            )


        } else if (!mKeyboardUp&&Linearlayout_SendMsg.paddingBottom>=800) {
            //键盘收起
            Toast.makeText(getApplicationContext(), "键盘收起", Toast.LENGTH_SHORT).show();
            Linearlayout_SendMsg.setPadding(Linearlayout_SendMsg.paddingLeft,Linearlayout_SendMsg.paddingTop,
                Linearlayout_SendMsg.paddingRight,
                Linearlayout_SendMsg.paddingBottom-800
            )
        }else{
        }
        }

    }
    private fun isKeyboardShown(rootView: View): Boolean {
        val softKeyboardHeight = 100
        /*保存可视区域的范围 如left, top, right, bottom。*/
        val r = Rect()
        /*getWindowVisibleDisplayFrame()是View类下的一个方法，用来获取当前窗口可视区域的大小
        * 调用该方法的view对象必须在有效的window中，比如activity,fragment或者dialog的layout中
        *
        * */
        rootView.getWindowVisibleDisplayFrame(r)
        val dm = rootView.resources.displayMetrics
        val heightDiff: Int = rootView.bottom - r.bottom
        Log.v("this","heightDiff:${heightDiff}")
        Log.v("this","${rootView.bottom}")
        Log.v("this","${r.bottom}")
        Log.v("this","${dm.density}")

        /*这里的dm.density表示的就是（dpi/160) 因为当dp为160dpi的时候表示的就是一个px（像素大小）
        * 所以我们设置的dp x dpi就表示我们的px再去和我们的计算出的可是界面的高度差进行比较，判断结果。
        * */
        return heightDiff > softKeyboardHeight * dm.density
    }
    /*发送聊天消息*/
     fun Send_msg(){
         val Context = MsgData(User_info.User?.email,email, Edit_SendMsg.text.toString(),ChatData.SEND_TYPE,1)
                     /*添加数据  用于更新recycleView*/
                     msg.add(Context)
                     runOnUiThread {
                         ChatRecycleView.adapter?.notifyItemInserted(msg.size - 1)
                         Log.v("msg", " ${msg.size}")
                         ChatRecycleView.scrollToPosition(msg.size - 1)
                         Edit_SendMsg.text = null
                     }
         chatViewModel.sendMsg(Context)
         /*写入流  存在分隔符*/

             }

    /*接收消息*/
    fun receive_msg() {
        chatViewModel.reciver()
        chatViewModel.reveivemsg.observe(this, object : Observer<MsgData> {
            override fun onChanged(t: MsgData?) {
                when (t) {
                    null -> {

                    }
                    else -> {
                        Log.v("this5","shoudao${t.type}")
                        one_msg = t!!
                        if (one_msg != null) {
                            if (one_msg.type == ChatData.queryMessage){
                                Log.v("this6",one_msg.content.toString())
                                message = one_msg.content.toString().split("-!-".toRegex())
                                String_to_MsgData(message)
                            }
                            if(one_msg.type ==ChatData.noconnect){
                                Toast.makeText(this@ChatActivity, one_msg.content,Toast.LENGTH_SHORT).show()
                            }
                            if (one_msg.type ==ChatData.REVICE_TYPE){
                                msg.add(one_msg)
                                runOnUiThread {
                                    ChatRecycleView.adapter?.notifyDataSetChanged()
                                    ChatRecycleView.scrollToPosition(msg.size - 1)
                                }
                            }

                        }
                    }
                }
            }
        })
    }
    /*加载离线消息*/
    private fun String_to_MsgData(listmessage:List<String>){
        listmessage.forEach {
            Log.v("test5",it)
            msg.add(MsgData(null,null,it,ChatData.REVICE_TYPE,1))
        }
        runOnUiThread {
            ChatRecycleView.adapter?.notifyDataSetChanged()
            ChatRecycleView.scrollToPosition(msg.size - 1)
        }
    }
    }


//    /*其中真正的后台线程只有一个，
//    当我们想要创建多个AsyncTask的时候，
//    也就是想要创建多个后台子线程的时候，
//    这些后台子线程都是串行执行*/
//    inner class Myasynctask:AsyncTask<Unit,Int,Boolean>(){
//        /*方便和UI进行交互，进行相应的耗时任务*/
//        override fun doInBackground(vararg params: Unit?): Boolean {
//            return false
//        }
//        /*和UI进行交互*/
//        override fun onProgressUpdate(vararg values: Int?) {
//            super.onProgressUpdate(*values)
//        }
//        /*执行结束之后进行相应的UI操作*/
//        override fun onPreExecute() {
//            super.onPreExecute()
//        }
//
//    }

