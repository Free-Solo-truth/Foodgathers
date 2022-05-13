package com.example.foodrecipes.ui.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.foodrecipes.R
import com.example.foodrecipes.model.postImage.ImageList
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.showdynamiclayout.view.*
import java.lang.reflect.Type

class dynamic_imageAdapter(var jsonStr:String):RecyclerView.Adapter<dynamic_imageAdapter.MyviewHolder>() {

//    ImageList ImageList = new ImageList();
//    Gson gson = new Gson();
//    java.lang.reflect.Type type = new TypeToken<ImageList>(){}.getType();
//    ImageList= gson.fromJson(json, type);
    val imagelist= getImageList(jsonStr).array_image
    class MyviewHolder(var viewitem:View):RecyclerView.ViewHolder(viewitem){
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyviewHolder {
        return MyviewHolder(LayoutInflater.from(parent.context).inflate(R.layout.showdynamiclayout,parent,false))
    }

    override fun getItemCount(): Int {
        return imagelist.size
    }

    override fun onBindViewHolder(holder: MyviewHolder, position: Int) {
        Log.v("ppp2",imagelist.get(position).imagename)
        Picasso.with(holder.viewitem.dynamic_imageView.context).load("http://8.130.11.202:8080/pictures/"+imagelist.get(position).imagename).into(holder.viewitem.dynamic_imageView)
    }
    fun getImageList(jsonStr: String):ImageList{
        val type: Type = object : TypeToken<ImageList?>() {}.getType()
        var imageList = Gson().fromJson<ImageList>(jsonStr,type)
        for(i in 0..imageList.array_image.size-1){
            Log.v("ppp4","${imageList.array_image.get(i).imagename}")
        }
        Log.v("ppp1",imageList.array_image.size.toString())
        return imageList
    }
}