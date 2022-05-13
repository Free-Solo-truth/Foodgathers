package com.example.foodrecipes.network

import android.util.Log
import com.example.foodrecipes.model.NewsModel
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.RequestBody
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.*


//https://api.spoonacular.com/recipes/complexSearch?query=pasta&maxFat=25&number=2&apiKey=290c44b30b94445f87016b071ba07021
//https://api.spoonacular.com/recipes/complexSearch?type=soup&diet=vegan&addRecipeInformation=true&fillIngredients=true&apiKey=1a0edebda73f4a17ad82375357e41313&number=1
object foodApiClient {
    //初始化所需的所有变量  通过一个对象直接的调用
    private val BASE_URL = "https://api.spoonacular.com"
    private val BASE_URL1 = "http://8.130.11.202:8080"
    private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
    private val retrofit:Retrofit by lazy {
        Log.v("ppp","retrofit建成功")
        Retrofit.Builder()
                .baseUrl(BASE_URL)//获取相应的基地址
                .addConverterFactory(MoshiConverterFactory.create(moshi))//指定解析器进行解析
                .build()
    }
    private val retrofit1:Retrofit by lazy {
        Retrofit.Builder()
                .baseUrl(BASE_URL1)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
    }

    val foodAPiService :foodAPiService by lazy {
        Log.v("ppp","lazy正确")
        retrofit.create(com.example.foodrecipes.network.foodAPiService::class.java)}


//    by lazy {
//        //创建api接口的服务
//        Log.v("ppp","已近开始访问")
//        retrofit.create(foodAPiService::class.java)
//    }
//    /post_war3/ImageServlet
}

 interface  foodAPiService{
        @GET("/recipes/complexSearch?addRecipeInformation=true&fillIngredients=true&apiKey=d29e7eb77ede4cabb3e7543546d46427")
        suspend  fun getFoodNews(
                                 @Query("type") maxFat:String,
                                 @Query("diet") diet:String,
                                 @Query("cuisine") cuisine:String,
                                 @Query("number") number:String): NewsModel
}
//https://api.spoonacular.com/recipes/complexSearch?type=soup&diet=vegan&cuisine=African&addRecipeInformation=true&fillIngredients=true&apiKey=d29e7eb77ede4cabb3e7543546d46427

