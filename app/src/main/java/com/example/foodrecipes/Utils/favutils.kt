package com.example.foodrecipes.Utils

import androidx.recyclerview.widget.DiffUtil
import com.example.foodrecipes.model.OneFavorityMessage
import com.example.foodrecipes.model.SendList

class favutils(
        private val newData:List<OneFavorityMessage>,
        private val oldData:List<OneFavorityMessage>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return oldData.size
    }

    override fun getNewListSize(): Int {
        return newData.size
    }


    //边角Item本身
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldData[oldItemPosition]
        val newItem = newData[newItemPosition]
        return  oldItem.favoritytitle == newItem.favoritytitle
    }
    //比较数据
    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldData[oldItemPosition]
        val newItem = newData[newItemPosition]
        return oldItem == newItem
    }


}