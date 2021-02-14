package com.example.jetpack_practice.utils

import com.example.jetpack_practice.retrofit.IRetrofit
import com.example.jetpack_practice.retrofit.RetrofitClient
import retrofit2.Retrofit

class RetrofitManager {

    //?
    companion object {
        val instance = RetrofitManager()
    }


    //http 콜 만들기
    // retroift interface 가져오기
    private val iRetrofit: IRetrofit? =
        RetrofitClient.getClient(Api.BASE_URL)?.create(IRetrofit::class.java)

    // 사진 검색 api 호출
    fun searchPhotos(searchTerm: String?, completion: (String) -> Unit) {


//        val term = searchTerm.let {
//            it
//        } ?: ""

        val term=searchTerm?:""
        val call = iRetrofit?.searchPhotos(searchTerm = term ).let {

        }

    }

}