package com.example.usergithubawal.network

import com.example.usergithubawal.response.DetailResponse
import com.example.usergithubawal.response.ItemsItem
import com.example.usergithubawal.response.UserResponse
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    companion object{
        const val  APIKEY = "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"
    }
    @GET("search/users")
    fun getSearch(@Query("q")username:String):Call<UserResponse>

    @GET("users/{username}")
    fun getDetailUser(@Path("username") username: String): Call<DetailResponse>

    @GET("users/{username}/followers")
    fun getFollower(@Path("username")username:String): Call<List<ItemsItem>>

    @GET("users/{username}/following")
    fun getFollowing(@Path("username")username:String): Call<List<ItemsItem>>

}