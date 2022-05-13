package com.example.foodrecipes.model


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class NewsModel(
    @Json(name = "number")
    val number: Int,
    @Json(name = "offset")
    val offset: Int,
    @Json(name = "results")
    val results: List<Result>,
    @Json(name = "totalResults")
    val totalResults: Int
)