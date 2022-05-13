package com.example.foodrecipes.ui.adapter

import android.content.Context
import android.content.res.Resources
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.foodrecipes.R
import kotlinx.android.synthetic.main.choosetype.view.*

class chooseAdapter(var choosename:List<String>):RecyclerView.Adapter<chooseAdapter.MyViewHolder>() {
    lateinit var mContext: Context
    var oldPosition = -1
     var onItemListener: OnItemListener1? = null
    inner class MyViewHolder(var item:View):RecyclerView.ViewHolder(item){
        init {
            item.textView1.setOnClickListener {
                onItemListener?.onClick(item,layoutPosition)
            }
        }
        }
    fun getchooseOne(position: Int){

    }
    fun setDefSelect(position: Int) {
        this.oldPosition = position
        notifyDataSetChanged()
    }
    //设置点击事件
    interface OnItemListener1 {
        fun onClick(v: View?, pos: Int)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        mContext = parent.context
        var ViewHolder = MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.choosetype,parent,false))
//        ViewHolder.itemView.setOnClickListener {
//            setDefSelect(ViewHolder.layoutPosition)
//        }
        return ViewHolder
    }

    override fun getItemCount(): Int {
        return choosename.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.itemView.textView1.setText(choosename.get(position))
        if (oldPosition != -1)
        {
            /*点的位置跟点击的textview位置一样设置点击后的不同样式*/
            if (oldPosition== position)
            {
                /*设置选中的样式*/
                holder.itemView.textView1.setBackgroundResource(R.color.teal_700);
            } else {
                /*其他的变为未选择状态
                 *设置未选中的样式
                */
                holder.itemView.textView1.setBackgroundResource(R.color.black);

            }
        }else{
            if(position ==0){
                holder.itemView.textView1.setBackgroundResource(R.color.teal_700);
            }
        }



    }
}