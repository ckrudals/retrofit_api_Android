package com.example.jetpack_practice.utils

object Constants {
    const val TAG: String = "로그"
}

enum class SEARCH_TYPE {
    PHOTO, USER
}

enum class RESPONSE_STATE{
    OKAY,FAIL
}
object Api {
    const val BASE_URL = "https://api.unsplash.com/" //BASE_URL

    //key는 개개인
    const val CLIENT_ID = "KBGNt5GAzHGKD0HLONiNqzmrD9Q5_s5hdbWe9OG8rSw" //api key

    const val SEARCH_PHOTOS = "/search/photos"
    const val SEARCH_USERS = " /search/users"
}