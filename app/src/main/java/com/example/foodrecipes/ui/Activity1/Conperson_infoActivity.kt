package com.example.foodrecipes.ui.Activity1

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.foodrecipes.R
import com.example.foodrecipes.model.*
import com.example.foodrecipes.ui.adapter.ContactPersonAdapter
import com.example.foodrecipes.viewmodel.getConPersonVIewmodel
import com.example.foodrecipes.viewmodel.getData_fromSQLViewmodel
import kotlinx.android.synthetic.main.activity_chat.*
import kotlinx.android.synthetic.main.activity_conperson_info.*


class Conperson_infoActivity : BaseActivity() {
    private val ConPersonviewmodel = getConPersonVIewmodel()
    private val getChatPersonViewmodel = getData_fromSQLViewmodel()
    private var  chatPersonList = ArrayList<Relationship>()
    private var arrayPerson = ArrayList<ConPersonData>()
    private lateinit var conAdapter:ContactPersonAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        Log.v("ppp6","onCreate")

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_conperson_info)

        /*设置布局方向*/
        changeStatusBarTextColor(true)
        ConPersonrecyelView.layoutManager = LinearLayoutManager(baseContext).apply {
            orientation = LinearLayoutManager.VERTICAL
            reverseLayout = true
            stackFromEnd = true
        }

        addButton.setOnClickListener {
            startActivity(Intent(this, AddFriendActivity::class.java))
        }

        ConPerson_back.setOnClickListener {
            finish()
        }
        getPhoneconperson()
        /*监听自主添加的好友的更新成功与否*/
        chatfriend()
    }

    override fun onResume() {
        super.onResume()
        /*刷新好友界面*/
        ConPersonviewmodel.getConPersonData()
        getChatPersonViewmodel.getChatPerson(User_info.User!!.email)

        Log.v("ppp6","onResume")


    }

    fun getPhoneconperson(){
        //查询手机通讯录好友
        ConPersonviewmodel.ConPersonState.observe(this,object :Observer<NewsState>{
            override fun onChanged(t: NewsState?) {
                when(val result = ConPersonviewmodel.ConPersonState.value){
                    /*注意这里的 is 的用处*/
                    is NewsState.getConPersonData ->{
                        /*清零，防止重复添加*/
//                        chatPersonList.clear()
//                        User_info.AllFriendname.clear() Log.v("ppp3","成功")
//                        Log.v("NewsState","is Success")
                        chatPersonList = result.Data
                    }
                    is NewsState.Loading ->{
                        Log.v("NewsState","is loading")
                    }
                }
            }
        })

    }
    fun chatfriend(){
//查询自主添加的好友
//        Toast.makeText(this@Conperson_infoActivity,"temp1",Toast.LENGTH_SHORT).show()
        getChatPersonViewmodel.getChatPersonState.observe(this,object:Observer<SendRelationship?>{
            override fun onChanged(t: SendRelationship?) {
                when(t){
                    null ->{
                    }
                    else ->{
                        chatPersonList.addAll(t.listChatPerson)
                        Thread{
                            for(friend in t.listChatPerson){
                                User_info.AllFriendname?.add(friend.friendname)
                                User_info.AllFriendnameposition.set(friend.friendname,"exist")
                            }
                        }.start()
                        conAdapter = ContactPersonAdapter(this@Conperson_infoActivity,chatPersonList)
                        ConPersonrecyelView.adapter = conAdapter
                        conAdapter.notifyConPerson(chatPersonList)
                        selectAllUser(User_info.User!!.email)
                    }
                }
            }

        })
    }

    //查找所有的用户，为后面的添加做准备
    private fun selectAllUser(email: String){
        getChatPersonViewmodel.getAlluserInfo(email)
        getChatPersonViewmodel.getAllUserinfoState.observe(this,object :Observer<SendUserData>{
            override fun onChanged(t: SendUserData?) {
                when (t) {
                    null -> {

                    }
                    else -> {
                        User_info.AllUsername.clear()
                        User_info.AllUser = t.listUserData
                        t.listUserData.forEach {
                            if (it.name != User_info.User?.name) {
                                User_info.AllUsername.add(it.name)
                            }
                            User_info.AllUserposition.set(it.name, it)
                        }
                    }

                }
            }
        })
}
}