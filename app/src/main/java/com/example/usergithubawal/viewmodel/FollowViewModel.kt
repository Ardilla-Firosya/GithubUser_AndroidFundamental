package com.example.usergithubawal.viewmodel

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.usergithubawal.network.ApiConfig
import com.example.usergithubawal.response.ItemsItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowViewModel : ViewModel() {
    private val _followModel = MutableLiveData<List<ItemsItem>>()
    val followModel: LiveData<List<ItemsItem>> = _followModel

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getFollow(name: String, tabs:String){
        _isLoading.value = true
        viewModelScope.launch(Dispatchers.IO) {
            val client: Call<List<ItemsItem>> = if (tabs == "Follower") {
                ApiConfig.getApiService().getFollower(name)
            } else {
                ApiConfig.getApiService().getFollowing(name)
            }
            client.enqueue(object : Callback<List<ItemsItem>>{
                override fun onResponse(
                    call: Call<List<ItemsItem>>,
                    response: Response<List<ItemsItem>>
                ) {
                    _isLoading.value = false
                    if(response.isSuccessful){
                        _followModel.value = response.body()
                    }else{
                        Log.e(ContentValues.TAG, "Error: ${response.message()}")
                    }
                }

                override fun onFailure(call: Call<List<ItemsItem>>, t: Throwable) {
                    _isLoading.value = false
                    Log.e(ContentValues.TAG, "Error: ${t.message.toString()}")
                }

            })


        }
    }
}