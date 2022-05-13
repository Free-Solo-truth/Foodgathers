package com.example.foodrecipes.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.foodrecipes.R
import com.example.foodrecipes.model.Carrier_DynamicMessage
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_set_dynamic.*
import kotlinx.android.synthetic.main.dynamic_layout.view.*

class dynamiAdapter(var DynamicInfo:Carrier_DynamicMessage?): RecyclerView.Adapter<dynamiAdapter.MyviewHolder>() {
    class MyviewHolder(var viewItem:View):RecyclerView.ViewHolder(viewItem){

    }

     fun notifydynamic(newDynmaicInfo:Carrier_DynamicMessage){
        DynamicInfo = newDynmaicInfo
         notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyviewHolder {
        var Main_view = LayoutInflater.from(parent.context).inflate(R.layout.dynamic_layout,parent,false)
        return MyviewHolder(Main_view)
    }
    override fun getItemCount(): Int {

        var counnt = DynamicInfo?.dynMsg?.size ?: 0;

        return counnt

    }
    override fun onBindViewHolder(holder: MyviewHolder, position: Int) {
        DynamicInfo?.let {
            Picasso.with(holder.viewItem.User_imageView.context).load(it.dynMsg.get(position).userIamge).into(holder.viewItem.User_imageView)
            holder.viewItem.dynamic_name.text =it.dynMsg.get(position).userName
            holder.viewItem.dynamic_text.text =it.dynMsg.get(position).dynamicText
            holder.viewItem.recycleView_image.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            holder.viewItem.recycleView_image.isNestedScrollingEnabled = false
            holder.viewItem.recycleView_image.adapter =dynamic_imageAdapter(it.dynMsg.get(position).dynamicImage)
        }

//        Picasso.with(holder.viewItem.dynamic_imageView.context).load(DynamicInfo.dynMsg.get(position).dynamicImage).into(holder.viewItem.dynamic_imageView)
    }

}