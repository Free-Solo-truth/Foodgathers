package com.example.foodrecipes.ui.adapter

import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat.getDrawable
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.example.foodrecipes.R
import com.example.foodrecipes.model.Relationship
import com.example.foodrecipes.model.UserData
import com.example.foodrecipes.model.User_info
import com.example.foodrecipes.ui.Activity1.DetailsActivity
import com.example.foodrecipes.viewmodel.getData_fromSQLViewmodel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.addfrienditem.view.*

class addFriendAdapter(): RecyclerView.Adapter<addFriendAdapter.MyViewHolder>() {
    private var Stringlist:List<String> = ArrayList<String>()
    private val mViewModel: getData_fromSQLViewmodel = getData_fromSQLViewmodel()
    var UserPosition:HashMap<String, UserData> = HashMap<String, UserData>()
    lateinit var context:Context
    lateinit var owner: LifecycleOwner
    class MyViewHolder(val item: View): RecyclerView.ViewHolder(item){
        init {
            item.findViewById<ImageView>(R.id.addButton).setOnClickListener {

            }
        }
    }
    constructor(UserPosition: HashMap<String, UserData>, Stringlist: List<String>, context: Context, lifecycleOwner: LifecycleOwner) : this(){
        this.Stringlist = Stringlist
        this.UserPosition = UserPosition
        this.context = context
        this.owner = lifecycleOwner
    }

    fun setFilter(Stringlist: List<String>){
        this.Stringlist = Stringlist
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val Holder =  MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.addfrienditem, parent, false))

        return Holder
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        if(Stringlist.get(position) != User_info.User?.name) {
            for(friendname in User_info.AllFriendname){
                if(friendname == Stringlist.get(position))
                    holder.itemView.addButton.setBackgroundResource(R.drawable.ic_face)
            }
            holder.itemView.addUser_name.setText(Stringlist.get(position))
            Picasso.with(holder.itemView.User_photo.context).load(UserPosition.get(Stringlist.get(position))?.photo).into(holder.itemView.User_photo)
            holder.itemView.addButton.setOnClickListener {
//                Log.v("backgorund","${.background.current.constantState}and${context.resources.getDrawable(R.drawable.ic_face,null).constantState}")
                if (User_info.AllFriendnameposition.get(Stringlist.get(position))!=null){
                    Toast.makeText(context,"你们已近是好友了，开始聊天吧",Toast.LENGTH_SHORT).show()
                }else{
                    insertUser(it, position)
                }

            }
        }
        holder.itemView.setOnClickListener {
            if (User_info.AllFriendnameposition.get(Stringlist.get(position)) != null) {
                DetailsActivity.startActivity(context, UserPosition.get(Stringlist.get(position))!!.name, UserPosition.get(Stringlist.get(position))!!.phone, UserPosition.get(Stringlist.get(position))!!.email)
            }
        }

    }

    override fun getItemCount(): Int {
        return Stringlist.size
    }


    fun insertUser(view: View, position: Int){
        val friend = User_info.AllUserposition.get(Stringlist.get(position))!!
        mViewModel.insertfriend(Relationship(User_info.User?.email, User_info.User?.name,User_info.User?.phone,friend.email, friend.phone, friend.name))
        mViewModel.insertUserState.observe(owner, object : Observer<String> {
            override fun onChanged(t: String?) {
                when (t) {
                    null -> {
                        Toast.makeText(context, "添加成功", Toast.LENGTH_LONG).show()
                    }
                    "插入成功" -> {
                        view.findViewById<ImageView>(R.id.addButton).setBackgroundResource(R.drawable.ic_face)
                        Toast.makeText(context, "添加成功", Toast.LENGTH_LONG).show()
                    }
                    "插入失败" -> {
                        Toast.makeText(context, "添加失败", Toast.LENGTH_LONG).show()
                    }
                }
            }

        })

    }
    }
