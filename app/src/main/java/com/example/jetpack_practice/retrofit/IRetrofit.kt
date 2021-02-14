package com.example.jetpack_practice.retrofit

import com.example.jetpack_practice.utils.Api
import retrofit2.Call
import com.google.gson.JsonElement

import retrofit2.http.GET
import retrofit2.http.Query

interface IRetrofit {

    @GET(Api.SEARCH_PHOTOS) //API KEY
    fun searchPhotos(@Query("query") searchTerm: String): Call<JsonElement>

    @GET(Api.SEARCH_USERS) //API KEY
    fun searchUsers(@Query("query") searchTerm: String): Call<JsonElement>

}