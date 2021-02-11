package com.example.jetpack_practice.retrofit

import android.util.Log
import com.example.jetpack_practice.utils.Constants.TAG
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    // 레트로핏 클라이언트 선언
    private var retrofitClient: Retrofit? = null

    //레트로핏 클라이언트 가져오기
    fun getClient(baseUrl: String): Retrofit? {
        Log.d(TAG, "RetrofitClient - getClient() called")

        // 디자인 패턴 - 빌더패턴
        // 빌더패턴을 통해 인스턴스 생성
        if (retrofitClient == null) {

            retrofitClient = Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        return retrofitClient
    }
}