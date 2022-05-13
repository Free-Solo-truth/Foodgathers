package com.example.foodrecipes.ui.adapter

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.foodrecipes.R
import com.example.foodrecipes.ui.Activity1.setDynamicActivity
import kotlinx.android.synthetic.main.dynamicimage_layout.view.*

class Dynamic_image_adapter():RecyclerView.Adapter<Dynamic_image_adapter.MyViewHolder>() {

    lateinit var imageUri_List:List<Uri?>
    lateinit var context:Context
    lateinit var Callback:callback
    public fun getListener(listener:callback){
        this.Callback = listener
        Log.v("ppp1",this.Callback.toString())
    }
    constructor( imageUri_List:List<Uri?>, context:Context):this(){
        this.imageUri_List = imageUri_List
        this.context = context
    }
    class MyViewHolder(var viewItem:View) :RecyclerView.ViewHolder(viewItem){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.dynamicimage_layout,parent,false))
    }

    override fun getItemCount(): Int {
        if(imageUri_List.size==6){
            return 6
        }
        return imageUri_List.size+1
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        if(position==imageUri_List.size){
            holder.viewItem.dynamic_image.apply {
                setImageResource(R.drawable.ic_add1)
                setBackgroundResource(R.color.mediumGray)
            }
            holder.viewItem.dynamic_image.setOnClickListener {
                (context as setDynamicActivity).add_image1()
            }
        }else{

            var test:(Uri) -> Bitmap = {
                context.contentResolver.openFileDescriptor(it,"r").use {
                    BitmapFactory.decodeFileDescriptor(it?.fileDescriptor)
                }
            }
            holder.viewItem.dynamic_image.setImageBitmap(test(imageUri_List.get(position)!!))
        }
    }
    public interface callback{
        fun add_image()
    }
}