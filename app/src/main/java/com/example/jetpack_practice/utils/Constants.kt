package com.example.jetpack_practice.utils

object Constants {
    const val TAG: String = "로그"
}

enum class SEARCH_TYPE {
    PHOTO, USER
}

object Api {
    const val BASE_URL = "https://api.unsplash.com/" //BASE_URL

    const val CLIENT_ID = "" //api key

    const val SEARCH_PHOTOS = "/search/photos"
    const val SEARCH_USERS = " /search/users"
}