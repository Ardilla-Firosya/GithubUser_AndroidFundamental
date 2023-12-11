package com.example.usergithubawal.viewmodel

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.usergithubawal.network.ApiConfig
import com.example.usergithubawal.response.ItemsItem
import com.example.usergithubawal.response.UserResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Response

class UserViewModel : ViewModel() {
    companion object{
        private const val NAME ="B"
    }
    private val _datalist = MutableLiveData<List<ItemsItem>>()
    val datalist: LiveData<List<ItemsItem>> = _datalist

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private fun searchUser(data: String){
        _isLoading.value=true
        viewModelScope.launch(Dispatchers.IO){
            ApiConfig.getApiService().getSearch(data)
                .enqueue(object: retrofit2.Callback<UserResponse>{
                    override fun onResponse(
                        call: Call<UserResponse>,
                        response: Response<UserResponse>
                    ) {
                        _isLoading.value = false
                        if (response.isSuccessful){
                            _datalist.value = response.body()?.items
                        }else{
                            Log.e(TAG, "Error:${response.message()}")
                        }
                    }

                    override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                        _isLoading.value = false
                        Log.e(TAG, "Error:${t.message.toString()}")
                    }
                })

        }
    }

    fun setName(username : String){
        searchUser(username)
    }
    init{
        searchUser(NAME)
    }

}