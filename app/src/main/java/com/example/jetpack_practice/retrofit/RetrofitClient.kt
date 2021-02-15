package com.example.jetpack_practice.retrofit

import android.util.Log
import com.example.jetpack_practice.utils.Api
import com.example.jetpack_practice.utils.Constants.TAG
import com.example.jetpack_practice.utils.isJsonArray
import com.example.jetpack_practice.utils.isJsonObject
import com.google.gson.JsonObject
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONObject
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import kotlin.coroutines.coroutineContext

object RetrofitClient {

    // 레트로핏 클라이언트 선언
    private var retrofitClient: Retrofit? = null

    //레트로핏 클라이언트 가져오기
    fun getClient(baseUrl: String): Retrofit? {
        Log.d(TAG, "RetrofitClient - getClient() called")


        //okHttpClient create instance

        val client = OkHttpClient.Builder()
        //로그를 찍기 위한 로깅 인터셉터 추가

        val loggingInterceptor = HttpLoggingInterceptor { message ->
            Log.d(
                TAG,
                "RetrofitClient - log() called / message: message : $message"
            )
            when {
                message.isJsonObject() -> Log.d(TAG, JSONObject(message).toString(4))
                message.isJsonArray() -> Log.d(TAG, JSONObject(message).toString(4))


                else -> {
                    try {
                        Log.d(TAG, JSONObject(message).toString(4))
                    } catch (e: Exception) {
                        Log.d(TAG, message)
                    }

                }
            }


        }

        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.HEADERS)

        //위에서 설정한 로깅 인터셉터를 okhttp 클라이언트에 추가

        client.addInterceptor(loggingInterceptor)

        // 기본 파라미터

        val baseParaMeterInterceptor: Interceptor = (object : Interceptor {
            override fun intercept(chain: Interceptor.Chain): Response {
                Log.d(TAG, "RetrofitClient - intercept() called")

                // original Request
                val originalRequest = chain.request()

                //쿼리 파라미터 추가
                val adderUrl: HttpUrl =
                    originalRequest.url.newBuilder().addQueryParameter("client_id", Api.CLIENT_ID)
                        .build()

                val finalRequest: Request =
                    originalRequest.newBuilder()
                        .url(adderUrl)
                        .method(originalRequest.method, originalRequest.body)
                        .build()

                return chain.proceed(finalRequest)
            }

        })

    //okhttp는 뭐지?
        // 위에서 설정한 기본파라미터 인터셉터를 okhttp 클라이언트에 추가한다.
        client.addInterceptor(baseParaMeterInterceptor)

        //커넥션 타임아웃

        //10초동안 반응이 없으시 종료
        client.connectTimeout(10, TimeUnit.SECONDS)
        // ;; 읽음
        client.readTimeout(10,TimeUnit.SECONDS)
        // ;; 씀
        client.writeTimeout(10,TimeUnit.SECONDS)
        // 실패햇을때 재시도 여부
        client.retryOnConnectionFailure(true)
        // 디자인 패턴 - 빌더패턴
        // 빌더패턴을 통해 인스턴스 생성
        if (retrofitClient == null) {

            retrofitClient = Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                // 위에서 설정한 클라이언트롤 레트로핏 클라이언트 설정
                .client(client.build())
                .build()
        }
        return retrofitClient
    }
}