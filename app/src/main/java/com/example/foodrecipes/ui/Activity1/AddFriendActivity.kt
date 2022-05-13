package com.example.foodrecipes.ui.Activity1

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.*
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.foodrecipes.DB.user_info
import com.example.foodrecipes.R
import com.example.foodrecipes.model.Relationship
import com.example.foodrecipes.model.SendUserData
import com.example.foodrecipes.model.User_info
import com.example.foodrecipes.ui.adapter.addFriendAdapter
import com.example.foodrecipes.viewmodel.getData_fromSQLViewmodel
import kotlinx.android.synthetic.main.activity_add_friend.*
import kotlinx.android.synthetic.main.activity_search.*
import kotlinx.android.synthetic.main.activity_search.SearchView
import kotlinx.android.synthetic.main.activity_search.back
import kotlinx.android.synthetic.main.addfrienditem.view.*

class AddFriendActivity : BaseActivity(){
    private val getChatPersonViewmodel = getData_fromSQLViewmodel()
    var ChangeshowUser:List<String> =ArrayList<String>()
    lateinit var adapter:addFriendAdapter
    private val mViewModel:getData_fromSQLViewmodel = getData_fromSQLViewmodel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_friend)
         /*查询所有的用户*/
//        selectAllUser(User_info.User!!.email)
//        /*初始化我们的adapter*/
        Alluser_RecycleView.layoutManager = LinearLayoutManager(this,RecyclerView.VERTICAL,false)
        adapter = addFriendAdapter(User_info.AllUserposition,User_info.AllUsername,this,this)
        Alluser_RecycleView.adapter = adapter
        /**/
        changeStatusBarTextColor(true)
        back.setOnClickListener {
            finish()
        }
        /**/
        SearchView.setOnQueryTextListener(object :
                androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText!=null){
                 ChangeshowUser = filter(User_info.AllUsername,newText)
                    adapter.setFilter(ChangeshowUser)
                }
                return true
            }
        }
        )
    }
    //查找所有的用户，为后面的添加做准备
//    private fun selectAllUser(email: String){
//        getChatPersonViewmodel.getAlluserInfo(email)
//        getChatPersonViewmodel.getAllUserinfoState.observe(this,object :Observer<SendUserData>{
//            override fun onChanged(t: SendUserData?) {
//                when (t){
//                    null ->{
//
//                    }
//                    else ->{
//                        User_info.AllUsername.clear()
//                        Log.v("ppp3","AllUser get")
//                        User_info.AllUser = t.listUserData
//                        t.listUserData.forEach {
//                            if(it.name != User_info.User?.name){
//                                User_info.AllUsername.add(it.name)
//                            }
//                            User_info.AllUserposition.set(it.name,it)
//                        }
//                        adapter.notifyDataSetChanged()
//                    }
//                }
//            }
//
//        })
//    }
    private fun filter(textlist:List<String>,text:String):List<String>{
        var mediusList:MutableList<String> = mutableListOf()
        for(element in textlist){
            if(element.contains(text)){
                mediusList.add(element)
            }
        }
        return mediusList
    }

}

