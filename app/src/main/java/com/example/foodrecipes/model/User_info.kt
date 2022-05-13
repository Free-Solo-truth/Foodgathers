package com.example.foodrecipes.model

class User_info {
    companion object{
         var User:UserData? = null
        var AllUser:List<UserData>? = null
        var AllUserposition:HashMap<String,UserData> = HashMap<String,UserData>()
        var AllUsername:MutableList<String> = mutableListOf()
        var  AllFriendname:MutableList<String> = mutableListOf()
        var AllFriendnameposition:HashMap<String,String> = HashMap<String,String>()
        var User_msg:MutableList<MsgData> = mutableListOf()
    }
}