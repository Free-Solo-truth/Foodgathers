package com.example.foodrecipes.ui.adapter

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.foodrecipes.R
import com.example.foodrecipes.Utils.favutils
import com.example.foodrecipes.model.OneFavorityMessage
import com.example.foodrecipes.model.Save_temp
import com.example.foodrecipes.model.SendList
import com.example.foodrecipes.ui.Activity1.show_foodActivity
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.favority_layout.view.*

class FavorityAdapter(var FavInfo:SendList?): RecyclerView.Adapter<FavorityAdapter.favorityViewHolder>() {
    private lateinit var context:Context
    inner class favorityViewHolder(val viewitem:View):RecyclerView.ViewHolder(viewitem){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): favorityViewHolder {
        context = parent.context
        return   favorityViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.favority_layout,parent,false))
    }

    fun notifyAdapter(sendList: SendList){
        FavInfo = sendList
        notifyDataSetChanged()
    }
    fun setDatas(newData:List<OneFavorityMessage>){
        val diffResult = FavInfo?.let { favutils(newData, it.favMeg) }?.let { DiffUtil.calculateDiff(it) }
         FavInfo?.favMeg= newData
        diffResult
        diffResult?.dispatchUpdatesTo(this)
    }

    override fun getItemCount(): Int {
        if (FavInfo!=null){
            Log.v("Main",FavInfo!!.favMeg.size.toString())
            return FavInfo!!.favMeg.size
        }
        return 0
    }

    override fun onBindViewHolder(holder: favorityViewHolder, position: Int) {
        Log.v("FavorityAdapter",position.toString())
        holder.viewitem.favoritytitle.setText(FavInfo?.favMeg?.get(position)?.favoritytitle)

        Save_temp.favfoodTitle.add(FavInfo?.favMeg?.get(position)?.favoritytitle)
        Save_temp.title_position.set(FavInfo?.favMeg?.get(position)!!.favoritytitle,position.toString())
        Picasso.with(holder.viewitem.circleImageView.context)
                .load(FavInfo?.favMeg?.get(position)?.favorityimage)
                .resize(100,100)
                .into(holder.viewitem.circleImageView)
        holder.viewitem.favoritysubit.setText(FavInfo?.favMeg?.get(position)?.favoritysubit?.replace("</b>","\n")
        )

        holder.itemView.setOnClickListener {
            context.startActivity(Intent(context,
                    show_foodActivity::class.java).putExtra("favposition","${position}"))
        }
        Save_temp.favHashMap.set(FavInfo?.favMeg?.get(position)?.favoritytitle.toString(),"exist")
    }
}