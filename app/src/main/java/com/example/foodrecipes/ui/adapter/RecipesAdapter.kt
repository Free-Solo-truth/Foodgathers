package com.example.foodrecipes.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.example.foodrecipes.R
import com.example.foodrecipes.model.*
import com.example.foodrecipes.ui.Activity1.show_foodActivity
import com.example.foodrecipes.viewmodel.getData_fromSQLViewmodel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.recipes_layout.view.*


class RecipesAdapter() : RecyclerView.Adapter<RecipesAdapter.MyViewHolder>(){
    var ChangeInfo = getData_fromSQLViewmodel()
    class MyViewHolder(var item: View) : RecyclerView.ViewHolder(item) {
        //对RecycleView中的Item进行设置

    }
    lateinit var context:Context
    lateinit var Response:NewsModel
    lateinit var email:String
    lateinit var lifecycleOwner: LifecycleOwner
    lateinit var favList: List<OneFavorityMessage>
    constructor( Response:NewsModel,
                 email:String,
                 lifecycleOwner: LifecycleOwner,
                 favList:List<OneFavorityMessage>):this(){
        this.Response = Response
        this.email = email
        this.lifecycleOwner = lifecycleOwner
        this.favList = favList
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        //这里出现OnCreateViewHolder：承载相应的子项的布局，返回的就是ViewHolder：就是为了recycleView滚动的时候快速的适配相应的值，提升相应的性能
        //创建viewholder并返回
        //这里是将layout转换为View
//        thisiamgeView!!.let { Log.v("test", it) }
        context = parent.context

        val layoutInflater = LayoutInflater.from(parent.context)//获取相应的layoutInflater布局填充器
        val view = layoutInflater.inflate(R.layout.recipes_layout, parent, false)//获取最后转换的View
        val ViewHolder = MyViewHolder(view)
        ViewHolder.itemView.favoriteImageView.setBackgroundResource(R.drawable.ic_favorites)

        ViewHolder.itemView.favoriteImageView.setOnClickListener {

            if (Save_temp.favHashMap.get(ViewHolder.itemView.titleTextView.text.toString())!=null){
                Save_temp.favHashMap.remove(ViewHolder.itemView.titleTextView.text.toString())
                it.setBackgroundResource(R.drawable.ic_favorites)
                ViewHolder.itemView.favoriteTextView.apply {
                    val favoritynumber = Integer.parseInt(this.text.toString())-1
                    setText(favoritynumber.toString())
                    /*删除数据*/
                    ChangeInfo.deleteFavorityInfo(email,ViewHolder.itemView.titleTextView.text.toString())
                }
            }else
            {
                it.setBackgroundResource(R.drawable.ic_favority_choose)
                ViewHolder.itemView.favoriteTextView.apply {
                    val favoritynumber = Integer.parseInt(this.text.toString())+1
                    setText(favoritynumber.toString())
                }
                /*插入数据*/
                Response.results[ViewHolder.adapterPosition].apply {
                    ChangeInfo.insertFavorityInfo(Carrier_FavorityMessage(email, OneFavorityMessage(
                            this.image,
                            this.title,
                            this.summary,
                            this.likes+1,
                            this.readyInMinutes,
                            this.vegan
                    )))
                }
            }
        }

        return ViewHolder
    }

    /*监听ChangeFavorityData是否成功*/
    fun ListenerChangeData(parent: ViewGroup){
        ChangeInfo.ChangeFavorityState.observe(lifecycleOwner, object :Observer<String>{
            override fun onChanged(t: String?) {
                when(t){
                    "" ->{

                    }
                    "正在操作" ->{
                        Toast.makeText(parent.context,t,Toast.LENGTH_SHORT).show()
                    }
                    "插入成功" ->{
                        Toast.makeText(parent.context,t,Toast.LENGTH_SHORT).show()
                    }
                    "取消成功" ->{
                        Save_temp.isChangeFavority = true
//                        favorityFragment.binding.favorityRecycleView.adapter.notifyItemRemoved()
                        Toast.makeText(parent.context,t,Toast.LENGTH_SHORT).show()
                    }
                    "取消失败" ->{
                        Toast.makeText(parent.context,t,Toast.LENGTH_SHORT).show()
                    }
                }
            }

        })
    }

    fun getChangeFav():MutableLiveData<Boolean?>{
        var temp:MutableLiveData<Boolean?> = MutableLiveData(null)
        temp.value = Save_temp.isChangeFavority
        return temp
    }



    override fun getItemCount(): Int {
        //返回相应的元素的数量
        return Response.results.size
    }
    /*
    * @author 李腾飞
    * 注意这里的revyvleView的复用的问题
    * cache——Pool——viewitem之间的关系
    * */
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    @SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        //加载标题
        holder.item.titleTextView.setText(Response.results[position].title)
        Save_temp.netfoodTitle.add(Response.results.get(position).title)
        Save_temp.title_position.set(Response.results.get(position).title,position.toString())
        Log.v("title0","${Response.results[position].summary}")
        //加载图片
        Picasso.with(holder.item.imageView.context).load(Response.results[position].image).into(holder.item.imageView)
        //加载summary
        holder.item.summaryTextView.setText(Response.results[position].summary)
        /*加载favorityImage*/
        holder.item.favoriteImageView.setBackgroundResource(R.drawable.ic_favorites)
        //加载favorite
        Log.v("ppp","${Response.results[position].likes}")
        /*加载isfavority*/
        holder.item.favoriteTextView.setText(Response.results[position].likes.toString())
        //加载readyInMinutes
        Log.v("ppp","${Response.results[position].readyInMinutes}")
        holder.item.readyInMinutes.setText(Response.results[position].readyInMinutes.toString())
        //判断是否为Vegan
        Response.results[position].vegan.apply {
            if (this){
                holder.item.vegan.drawable.setTint(Color.GREEN)
            }
        }
        isFavority(holder)
        holder.itemView.setOnClickListener {
            context.startActivity(Intent(context,
                    show_foodActivity::class.java).putExtra("position","${position}"))
        }




    }


    private fun isFavority(ViewHolder:MyViewHolder){
        Log.v("favority","${favList.size}")
        favList.forEach {
            Log.v("comparTitle","${it.favoritytitle}")
            Log.v("comparTitle","${ViewHolder.itemView.titleTextView.text}")
            if (it.favoritytitle == ViewHolder.itemView.titleTextView.text) {
                Log.v("favority","test")
                ViewHolder.itemView.favoriteTextView.apply {
                    this.setText((it.favoritynumber.toInt()).toString())
                }
                ViewHolder.itemView.favoriteImageView.setBackgroundResource(R.drawable.ic_favority_choose)
            }
        }
    }


}


