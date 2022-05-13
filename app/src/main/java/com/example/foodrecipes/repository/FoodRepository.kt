package com.example.foodrecipes.repository

import android.util.Log
import com.example.foodrecipes.DB.Operate_User_info_db
import com.example.foodrecipes.model.*
import com.example.foodrecipes.network.HttpUtil

import com.example.foodrecipes.network.foodApiClient
import com.example.foodrecipes.network.getDataFromSQL
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow//注意这里的Flow的导入是kotlinx并非java
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.net.URL


class FoodRepository {
    //使得UI通过Repository实现数据的交互
    companion object{
        //封装获取数据的方法
        fun getFoodNews(
                        type:String,
                        diet:String,
                        cuisine:String):Flow<NewsModel> = flow{
            var foodNews= foodApiClient.foodAPiService.getFoodNews(type, diet,cuisine,"20")
            emit(foodNews)//执行回调
            }.flowOn(Dispatchers.IO)

        fun sendMsg(msgData: MsgData){
            var sendmsg = getDataFromSQL().sendMsg(msgData)
        }
        fun reciver():Flow<MsgData> = flow{
            var recrivemsg = getDataFromSQL().reciver()
            emit(recrivemsg)
        }.flowOn(Dispatchers.IO)
         fun getChatPerson(username:String):Flow<SendRelationship> = flow{
            var ChatPerson = getDataFromSQL().getChatPerson(username)
            emit(ChatPerson)
        }.flowOn(Dispatchers.IO)
        fun getAlluserInfo(email: String?):Flow<SendUserData> = flow{
            var ListUserData = getDataFromSQL().getAlluserInfo(email)
            emit(ListUserData)
         }.flowOn(Dispatchers.IO)

        fun insertfriend(User:Relationship):Flow<String> = flow {
            var result = getDataFromSQL().insertfriend(User)
            emit(result)
        }.flowOn(Dispatchers.IO)
        fun addFriend(friendInfo:SendRelationship):Flow<String> = flow {
            var result =  getDataFromSQL().addFriend(friendInfo)
            emit(result)
        }.flowOn(Dispatchers.IO)

        fun getConPersonData():Flow<ArrayList<Relationship>> = flow {
            Log.v("NewsState","错误")
            var ConpersonData = Operate_User_info_db.operConPerson_info.queryConPerson()
            Log.v("NewsState","${ConpersonData}")
            emit(ConpersonData)
        }.flowOn(Dispatchers.IO)

        fun User_Enroll(user:UserData):Flow<String?> = flow{
            var isExistUser = getDataFromSQL().User_Enroll(user)
            emit(isExistUser)
        }.flowOn(Dispatchers.IO)

        fun getUserInfo(username: String?,email:String?):Flow<UserData?> = flow{
            emit(getDataFromSQL().getUserInfo(username,email))
        }.flowOn(Dispatchers.IO)

        fun VerifyPasword(username:String,password:String):Flow<String?> = flow{
            var isExistUser = getDataFromSQL().VerifyPasword(username,password)
            emit(isExistUser)
        }.flowOn(Dispatchers.IO)

        fun getFavorityInfo(email: String?):Flow<SendList?> =flow{
            var FavorityAllInfo = getDataFromSQL().getFavorityInfo(email)
            emit(FavorityAllInfo)
        }.flowOn(Dispatchers.IO)
        fun deleteFavorityInfo(email: String?,favorityTitle: String):Flow<String?> = flow{
            var result = getDataFromSQL().deleteFavorityInfo(email,favorityTitle)
            emit(result)
        }.flowOn(Dispatchers.IO)

        fun insertFavorityInfo(favorityMessage: Carrier_FavorityMessage):Flow<String?> = flow{
            var result = getDataFromSQL().insertFavorityInfo(favorityMessage)
            emit(result)
        }.flowOn(Dispatchers.IO)
        fun getChatMessage(){

        }
        fun getDynamicInfo(email: String?):Flow<Carrier_DynamicMessage?> = flow{
            var DynamicMessage = getDataFromSQL().getDynamicInfo(email)
            emit(DynamicMessage)
        }.flowOn(Dispatchers.IO)
        fun save_Dynamic(email: String?,dynamicMessage: Carrier_DynamicMessage):Flow<String?> = flow {
            var Response = getDataFromSQL().save_Dynamic(email,dynamicMessage)
            emit(Response)
        }.flowOn(Dispatchers.IO)
        fun post_Image(url:URL,jsonData:String):Flow<String?> = flow {
            var result = HttpUtil.doJsonPost(url,jsonData)
            emit(result)
        }.flowOn(Dispatchers.IO)
        fun update_userimage(UserInfo:UpdateUser):Flow<String> = flow{
            var result  = getDataFromSQL().update_userimag(UserInfo)
            emit(result)
        }.flowOn(Dispatchers.IO)
    }

    }
