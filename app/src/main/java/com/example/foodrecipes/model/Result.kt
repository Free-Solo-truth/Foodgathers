package com.example.foodrecipes.model


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Result(
    @Json(name = "aggregateLikes")
    val aggregateLikes: Int,
    @Json(name = "cheap")
    val cheap: Boolean,

    @Json(name = "cuisines")
    val cuisines: List<Any>,
    @Json(name = "dairyFree")
    val dairyFree: Boolean,
    @Json(name = "diets")
    val diets: List<String>,
    @Json(name = "dishTypes")
    val dishTypes: List<String>,
    @Json(name = "extendedIngredients")
    val extendedIngredients: List<ExtendedIngredient>,
    @Json(name = "gaps")
    val gaps: String,
    @Json(name = "glutenFree")
    val glutenFree: Boolean,
    @Json(name = "healthScore")
    val healthScore: Double,
    @Json(name = "id")
    val id: Int,
    @Json(name = "image")
    val image: String,
    @Json(name = "imageType")
    val imageType: String,
    @Json(name = "likes")
    val likes: Int,
    @Json(name = "lowFodmap")//低聚糖饮食
    val lowFodmap: Boolean,
    @Json(name = "missedIngredientCount")
    val missedIngredientCount: Int,
    @Json(name = "occasions")//场合
    val occasions: List<String>,
    @Json(name = "pricePerServing")//食物价格
    val pricePerServing: Double,
    @Json(name = "readyInMinutes")
    val readyInMinutes: Int,
    @Json(name = "servings")
    val servings: Int,
    @Json(name = "sourceUrl")
    val sourceUrl: String,
    @Json(name = "spoonacularScore")
    val spoonacularScore: Double,
    @Json(name = "spoonacularSourceUrl")
    val spoonacularSourceUrl: String,
    @Json(name = "summary")
    val summary: String,
    @Json(name = "sustainable")
    val sustainable: Boolean,
    @Json(name = "title")
    val title: String,
    @Json(name = "unusedIngredients")
    val unusedIngredients: List<Any>,
    @Json(name = "usedIngredientCount")
    val usedIngredientCount: Int,
    @Json(name = "usedIngredients")
    val usedIngredients: List<Any>,
    @Json(name = "vegan")
    val vegan: Boolean,
    @Json(name = "vegetarian")//素食
    val vegetarian: Boolean,
    @Json(name = "veryHealthy")
    val veryHealthy: Boolean,
    @Json(name = "veryPopular")
    val veryPopular: Boolean,
    @Json(name = "weightWatcherSmartPoints")
    val weightWatcherSmartPoints: Int
)