package com.example.foodrecipes.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodrecipes.model.*
import com.example.foodrecipes.repository.FoodRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch


import java.net.URL

open class getData_fromSQLViewmodel:ViewModel() {
    var UserinfoState:MutableLiveData<UserData?> = MutableLiveData(null)
    var VerifyPasswordState:MutableLiveData<String?> = MutableLiveData(null)
    var User_EnrollState:MutableLiveData<String?> = MutableLiveData(null)
    var FavorityInfoState:MutableLiveData<SendList?> = MutableLiveData(null)
    var getChatMessageState:MutableLiveData<NewsState> = MutableLiveData(NewsState.Empty)
    var getChatPersonState:MutableLiveData<SendRelationship?> = MutableLiveData()
    var ChangeFavorityState:MutableLiveData<String> = MutableLiveData("")
    var DynamicInfoState:MutableLiveData<Carrier_DynamicMessage?> = MutableLiveData()
    var PostImageState:MutableLiveData<String> = MutableLiveData()
    var SaveDynamicState:MutableLiveData<String> = MutableLiveData()
    var AddFriendState:MutableLiveData<String> = MutableLiveData()
    var getAllUserinfoState:MutableLiveData<SendUserData> = MutableLiveData()
    var insertUserState:MutableLiveData<String> = MutableLiveData()
    var updateImageState:MutableLiveData<String> = MutableLiveData()



    fun User_Enroll(user:UserData) {
        viewModelScope.launch {
            FoodRepository.User_Enroll(user)
                    .onStart { User_EnrollState.value = "加载" }
                    .catch { e ->
                        Log.v("VeridyPassword", e.toString())
                    }
                    .collect { isExist ->
                        User_EnrollState.value = isExist
                    }
        }
    }
    fun getUserInfo(username: String?,email:String?){
        viewModelScope.launch {
            FoodRepository.getUserInfo(username,email)
                    .onStart {UserinfoState.value = null }
                    .catch { e ->
                        Log.v("VeridyPassword",e.toString())
                    }
                    .collect{ isExist ->
                        UserinfoState.value = isExist
                    }
        }
    }
    fun VerifyPasword(username:String,password:String){
        viewModelScope.launch {
            FoodRepository.VerifyPasword(username,password)
                    .onStart {VerifyPasswordState.value = "加载" }
                    .catch { e ->
                        Log.v("VeridyPassword",e.toString())
                    }
                    .collect{ isExist ->
                        VerifyPasswordState.value = isExist
                    }
        }
    }
    fun getFavorityInfo(email: String?){
        viewModelScope.launch {
            delay(1000)
            FoodRepository.getFavorityInfo(email)
                    .onStart { FavorityInfoState.value = null }
                    .catch { e ->
                        e.printStackTrace()
                    }
                    .collect{favorityinfo ->
                        FavorityInfoState.value = favorityinfo
                    }
        }
    }
    fun deleteFavorityInfo(email: String?,favorityTitle:String){
        viewModelScope.launch {
            FoodRepository.deleteFavorityInfo(email,favorityTitle)
                    .onStart { ChangeFavorityState.value = "正在操作" }
                    .catch {e ->
                        e.printStackTrace()
                    }
                    .collect{result ->
                        Log.v("this4","delete成功")
                        ChangeFavorityState.value = result }
        }
    }

    fun insertFavorityInfo(favorityMessage: Carrier_FavorityMessage){
        viewModelScope.launch {
            FoodRepository.insertFavorityInfo(favorityMessage)
                    .onStart { ChangeFavorityState.value = "正在操作" }
                    .catch { e ->
                        e.printStackTrace()
                    }
                    .collect{result ->
                        ChangeFavorityState.value = result
                    }
        }
    }
    fun getDynamicInfo(email: String?){
        viewModelScope.launch {
            FoodRepository.getDynamicInfo(email)
                .onStart { DynamicInfoState.value = null }
                .catch { e->
                    e.printStackTrace()
                }
                .collect{response ->
                    DynamicInfoState.value = response
                }
        }
    }
    fun save_dynamic(email: String?,dynamicMessage: Carrier_DynamicMessage){
        viewModelScope.launch {
            FoodRepository.save_Dynamic(email,dynamicMessage)
                    .onStart {SaveDynamicState.value = null}
                    .catch { e->e.printStackTrace() }
                    .collect{response ->
                        SaveDynamicState.value = response
                    }
        }
    }
    fun post_Image(url:URL,jsonData:String){
        viewModelScope.launch {
            FoodRepository.post_Image(url,jsonData)
                .onStart { PostImageState.value = null}
                .catch { e->e.printStackTrace() }
                .collect{response ->
                    PostImageState.value = response
                }
        }
    }
    fun getChatMessage(){
    }
    fun addFriend(friendInfo:SendRelationship){
        viewModelScope.launch {
            FoodRepository.addFriend(friendInfo)
                    .catch {  }
                    .collect { reuslt->
                        AddFriendState.value = reuslt
                    }
        }
    }
    fun getChatPerson(username: String){
        viewModelScope.launch {
            FoodRepository.getChatPerson(username)
                    .onStart { getChatPersonState.value= null }
                    .catch {  }
                    .collect { e->
                        getChatPersonState.value = e
                        Log.v("ppp3","获取数据成功")
                    }
        }
    }
    fun getAlluserInfo(username: String){
        viewModelScope.launch {
            FoodRepository.getAlluserInfo(username)
                .onStart { getAllUserinfoState.value= null }
                .catch {  }
                .collect { e->
                    getAllUserinfoState.value = e
                }
        }
    }
    fun insertfriend(User:Relationship){
        viewModelScope.launch {
            FoodRepository.insertfriend(User)
                .onStart { insertUserState.value= null }
                .catch {  }
                .collect { e->
                    insertUserState.value = e
                }

        }
    }
    fun update_userimage(UserInfo:UpdateUser){
        viewModelScope.launch {
            FoodRepository.update_userimage(UserInfo)
                    .onStart {   updateImageState.value = null}
                    .catch {  e->
                        e.printStackTrace()
                    }
                    .collect { e->
                        updateImageState.value = e
                    }
        }
    }
}

class recipesFragmentViewModel:getData_fromSQLViewmodel(){

}

class FavorityFragmentViewModel:getData_fromSQLViewmodel(){

}