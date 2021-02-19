@file:Suppress("CAST_NEVER_SUCCEEDS")

package com.example.jetpack_practice.utils

import android.annotation.SuppressLint
import android.util.Log
import com.example.jetpack_practice.Model.Photo
import com.example.jetpack_practice.retrofit.IRetrofit
import com.example.jetpack_practice.retrofit.RetrofitClient
import com.example.jetpack_practice.utils.Constants.TAG
import com.google.gson.JsonElement
import com.google.gson.internal.bind.util.ISO8601Utils.format
import retrofit2.Call
import retrofit2.Response
import java.text.SimpleDateFormat

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
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
    fun searchPhotos(searchTerm: String?, completion: (RESPONSE_STATE, ArrayList<Photo>?) -> Unit) {


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
            @SuppressLint("SimpleDateFormat")
            override fun onResponse(call: Call<JsonElement>, response: Response<JsonElement>) {

                Log.d(TAG, "RetrofitManager - onResponse() called : ${response.body().toString()}")

                when (response.code()) {

                    200 -> {
                        response.body()?.let {

                            var parsedPhotoDataArray = ArrayList<Photo>()
                            val body = it.asJsonObject
                            val results = body.getAsJsonArray("results")

                            val total = body.get("total").asInt

                            Log.d(TAG, "RetrofitManager - onResponse() called / total:$total")
                            results.forEach { resultsItem ->
                                val resultItemObject = resultsItem.asJsonObject

                                val user = resultItemObject.get("user").asJsonObject

                                val username: String = user.get("username").asString

                                val likesCount = resultItemObject.get("likes").asInt

                                val thumbnailLink =
                                    resultItemObject.get("urls").asJsonObject.get("thumb").asString

                                val createdAt = resultItemObject.get("created_at").asString

                                val parser = SimpleDateFormat("yyyy-MM-dd' bT'HH:mm:ss")
                                val formatter = SimpleDateFormat("yyyy년\n MM월 dd일")
                                val outPutDateString=formatter.format(parser.parse(createdAt))

                                Log.d(TAG, "RetrofitManager - onResponse() called $outPutDateString")

                                val photoItem = Photo(
                                    authodr = username,
                                    likeCount = likesCount,
                                    thumbnail = thumbnailLink,
                                    createdAt = createdAt
                                )
                                parsedPhotoDataArray.add(photoItem)
                            }
                            completion(RESPONSE_STATE.OKAY,parsedPhotoDataArray )
                        }



                    }


                }

            }

            //응답 실패
            override
            fun onFailure(call: Call<JsonElement>, t: Throwable) {

                completion(RESPONSE_STATE.FAIL,null)
                Log.d(TAG, "RetrofitManager - onFailure() called")


            }

        })
    }

}