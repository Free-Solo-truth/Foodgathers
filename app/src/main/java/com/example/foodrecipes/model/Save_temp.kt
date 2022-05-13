package com.example.foodrecipes.model

import com.example.foodrecipes.model.postImage.Image
import java.io.ObjectInputStream

class Save_temp {
    companion object{
        var favfood:List<OneFavorityMessage> = ArrayList<OneFavorityMessage>()
        var favHashMap:HashMap<String,String> = HashMap<String,String>()
        var isChangeFavority:Boolean = false
        var foodinfo:List<Result>? = null
        var favfoodTitle:MutableList<String?> = mutableListOf()
        var netfoodTitle:MutableList<String?> = mutableListOf()
        var title_position:HashMap<String,String> = HashMap<String,String>()
    }
}