package com.example.foodrecipes.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.foodrecipes.R
import com.example.foodrecipes.model.Relationship
import com.example.foodrecipes.ui.Activity1.DetailsActivity
import kotlinx.android.synthetic.main.contact_person_layout.view.*

class ContactPersonAdapter(val context: Context,var ArrayPerson:ArrayList<Relationship>):RecyclerView.Adapter<ContactPersonAdapter.MyViewholder>(){
    class MyViewholder(var item:View):RecyclerView.ViewHolder(item){

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):MyViewholder {
        return MyViewholder(LayoutInflater.from(parent.context).inflate(R.layout.contact_person_layout,parent,false))
    }

    override fun getItemCount(): Int {
        return ArrayPerson.size
    }

    fun notifyConPerson(  ConPerson:ArrayList<Relationship>){
        ArrayPerson = ConPerson
        notifyDataSetChanged()
    }

    @SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(holder: MyViewholder, position: Int) {
        /*加载TextImage*/
        holder.item.TextImage.setText(ArrayPerson.get(position).friendname.substring(ArrayPerson.get(position).friendname.length -1))

        /* 获取ConpersonName*/
        holder.item.ConPerson_name.setText(ArrayPerson.get(position).friendname)

        /*主界面*/
        holder.item.setOnClickListener {
            DetailsActivity.startActivity(context,ArrayPerson.get(position).friendname,ArrayPerson.get(position).friendphone,ArrayPerson.get(position).friendemail)
        }

    }


}