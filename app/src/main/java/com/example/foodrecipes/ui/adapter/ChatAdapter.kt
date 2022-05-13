package com.example.foodrecipes.ui.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.foodrecipes.R
import com.example.foodrecipes.model.MsgData
import com.example.foodrecipes.model.User_info
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.receivermag_layout.view.*
import kotlinx.android.synthetic.main.sendmsg1_layout.view.*
import kotlin.IllegalArgumentException

class ChatAdapter(var msg:List<MsgData>,var friendname:String):RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    class Rightviewholder(val item:View):RecyclerView.ViewHolder(item){

    }
    class LeftviewHolder(val item: View):RecyclerView.ViewHolder(item){}

    override fun getItemViewType(position: Int): Int {
       return msg.get(position).type
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = if (viewType == 1 ){
        Rightviewholder(LayoutInflater.from(parent.context).inflate(R.layout.sendmsg1_layout,parent,false))
    }else{
        LeftviewHolder(LayoutInflater.from(parent.context).inflate(R.layout.receivermag_layout,parent,false))

    }

    override fun getItemCount(): Int {
        return msg.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){
            is Rightviewholder ->      {
                holder.itemView.showSendMsg.setText(msg.get(position).content)
                Picasso.with(holder.item.User_imageView1.context).load(User_info.User?.photo).into(holder.item.User_imageView1)
            }
            is LeftviewHolder ->    {
                Log.v("this5","${User_info.AllUserposition.get(friendname)?.photo}")
//                Picasso.with(holder.item.friend_image.context).load("").into(holder.item.friend_image)
                holder.itemView.showreceivemsg.setText(msg.get(position).content)
            }
            else -> throw IllegalArgumentException()
        }

    }
}