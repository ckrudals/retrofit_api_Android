package com.example.jetpack_practice.retrofit

import retrofit2.http.GET
import retrofit2.http.Query

interface IRetrofit {

    @GET("/search/photos")
    fun searchPhotos(@Query("query") searchTerm: String)

    @GET(" /search/users")
    fun searchUsers(@Query("query") searchTerm: String)

}