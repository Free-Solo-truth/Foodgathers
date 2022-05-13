package com.example.foodrecipes.ui.Activity1


import android.os.Bundle

import com.example.foodrecipes.R
import com.example.foodrecipes.model.Save_temp
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_show_food.*


class show_foodActivity : BaseActivity() {
    var position:Int? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_food)

       intent.getStringExtra("position")?.toInt()?.let {
            initnetfood(it)
        }
        intent.getStringExtra("favposition")?.toInt()?.let {
            initfavfood(it)
        }


    }
    fun initnetfood(position:Int){

        Save_temp.foodinfo?.get(position)?.apply {
            Picasso.with(showFood_imageView.context).load(image).into(showFood_imageView)
            title1.setText(title)
            desc.setText("\t\t\t"+summary.replace(Regex("\\<b>|\\</b>|\\<a>|\\</a>"),""))
            if(lowFodmap){
                lowFodmap1.checkMarkDrawable = getDrawable(R.drawable.type_check)
            }
            if(vegan){
                vegan1.checkMarkDrawable = getDrawable(R.drawable.type_check)
            }
            if(vegetarian){
                vegetarian1.checkMarkDrawable = getDrawable(R.drawable.type_check)
            }
            if(veryPopular){
                veryPopular1.checkMarkDrawable = getDrawable(R.drawable.type_check)
            }
            if(sustainable){
                sustainable1.checkMarkDrawable = getDrawable(R.drawable.type_check)
            }
            if(dairyFree){
                dairyFree1.checkMarkDrawable = getDrawable(R.drawable.type_check)
            }
        }

    }
    fun initfavfood(position:Int){

        Save_temp.favfood?.get(position)?.apply {
            Picasso.with(showFood_imageView.context).load(favorityimage).into(showFood_imageView)
            title1.setText(title)
            desc.setText("\t\t\t"+favoritysubit.replace(Regex("\\<b>|\\</b>|\\<a>|\\</a>"),""))
            if(true){
                lowFodmap1.checkMarkDrawable = getDrawable(R.drawable.type_check)
            }
            if(favoritytype){
                vegan1.checkMarkDrawable = getDrawable(R.drawable.type_check)
            }
            if(false){
                vegetarian1.checkMarkDrawable = getDrawable(R.drawable.type_check)
            }
            if(false){
                veryPopular1.checkMarkDrawable = getDrawable(R.drawable.type_check)
            }
            if(true){
                sustainable1.checkMarkDrawable = getDrawable(R.drawable.type_check)
            }
            if(true){
                dairyFree1.checkMarkDrawable = getDrawable(R.drawable.type_check)
            }
        }

    }
}