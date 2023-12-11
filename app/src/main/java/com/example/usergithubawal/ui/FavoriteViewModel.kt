package com.example.usergithubawal.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.database.FavoriteDao
import com.database.FavoriteRoomDatabase
import com.database.FavoriteUser

class FavoriteViewModel (application: Application): AndroidViewModel(application) {
    private val dao: FavoriteDao?
    private val databaseFavorite: FavoriteRoomDatabase?
    init {
        databaseFavorite= FavoriteRoomDatabase.getDatabase(application)
        dao= databaseFavorite?.favoriteDao()
    }
    fun getUser():LiveData<List<FavoriteUser>>?{
        return dao?.getAllFavorite()
    }
}