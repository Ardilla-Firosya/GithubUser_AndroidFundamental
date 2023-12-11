package com.example.usergithubawal.viewmodel

import android.app.Application
import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.database.FavoriteDao
import com.database.FavoriteRoomDatabase
import com.database.FavoriteUser
import com.example.usergithubawal.network.ApiConfig
import com.example.usergithubawal.response.DetailResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class DetailViewModel(application: Application): AndroidViewModel(application) {
    private val _detail = MutableLiveData<DetailResponse>()
    val detailUser: LiveData<DetailResponse> = _detail

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val dao: FavoriteDao?
    private val databaseFavorite: FavoriteRoomDatabase?
    init {
        databaseFavorite= FavoriteRoomDatabase.getDatabase(application)
        dao= databaseFavorite?.favoriteDao()
    }
    fun insertUser(username: String, avatarUrl: String){
        CoroutineScope(Dispatchers.IO).launch {
            var favoriteUser = FavoriteUser(username, avatarUrl)
            dao?.insert(favoriteUser)
        }
    }
    fun deleteUser(username: String){
        CoroutineScope(Dispatchers.IO).launch {
            dao?.deleteUser(username)
        }
    }
    fun checkUser(username: String) = dao?.checkUser(username)



    fun userDetail(username: String){
        _isLoading.value = true
        viewModelScope.launch (Dispatchers.IO){
            ApiConfig.getApiService().getDetailUser(username)
                .enqueue(object : Callback<DetailResponse>{
                    override fun onResponse(
                        call: Call<DetailResponse>,
                        response: Response<DetailResponse>
                    ) {
                        _isLoading.value = false
                        if (response.isSuccessful){
                            _detail.value = response.body()
                        }else{
                            Log.e(ContentValues.TAG, "Error: ${response.message()}")
                        }
                    }

                    override fun onFailure(call: Call<DetailResponse>, t: Throwable) {
                        _isLoading.value = false
                        Log.e(ContentValues.TAG, "Error ${t.message.toString()}")
                    }

                })
        }
    }

}