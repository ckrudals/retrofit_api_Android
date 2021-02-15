package com.example.jetpack_practice.utils

import android.util.Log
import com.example.jetpack_practice.retrofit.IRetrofit
import com.example.jetpack_practice.retrofit.RetrofitClient
import com.example.jetpack_practice.utils.Constants.TAG
import com.google.gson.JsonElement
import retrofit2.Call
import retrofit2.Response
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
    //비동기 방식
    fun searchPhotos(searchTerm: String?, completion: (RESPONSE_STATE,String) -> Unit)  {


//        val term = searchTerm.let {
//            it
//        } ?: ""

        val term = searchTerm ?: ""
//        val call = iRetrofit?.searchPhotos(searchTerm = term ).let {
//
//            it // call에 들감
//        }?: return

        val call = iRetrofit?.searchPhotos(searchTerm = term) ?: return

        call.enqueue(object : retrofit2.Callback<JsonElement> {
            //응답 성공
            //Lambda Parameter 람다식을 파라미터로 사용하기
            override fun onResponse(call: Call<JsonElement>, response: Response<JsonElement>) {

                Log.d(TAG, "RetrofitManager - onResponse() called : ${response.body().toString()}" )
                completion(RESPONSE_STATE.OKAY,response.raw().toString())
            }

            //응답 실패
            override
            fun onFailure(call: Call<JsonElement>, t: Throwable) {

                completion(RESPONSE_STATE.FAIL,t.toString())
                Log.d(TAG, "RetrofitManager - onFailure() called")


            }

        })
    }

}