package com.example.usergithubawal.network
import de.hdodenhof.circleimageview.BuildConfig
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class ApiConfig {
    companion object{
        const val TOKENAPI="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"

        const val  baseurl ="https://api.github.com/"
        fun getApiService(): ApiService {
            val loggingInterceptor= if(BuildConfig.DEBUG){
                HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

            }else{
                HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.NONE)

            }
            val token = Interceptor{ chain ->
                val req =chain.request()
                val requestHeader = req.newBuilder()
                    .addHeader("Authorization","token $TOKENAPI" )
                    .build()
                chain.proceed(requestHeader)
            }
            val client = OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .addInterceptor(token)
                .build()
            val retrofit = Retrofit.Builder()
                .baseUrl(baseurl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
            return retrofit.create(ApiService::class.java)

        }

    }


}