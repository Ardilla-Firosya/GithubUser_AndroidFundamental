package com.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface FavoriteDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(favoriteUser: FavoriteUser)

    @Query("SELECT count (*) from favoriteuser where username= :username")
    fun checkUser(username: String): Int

    @Query("DELETE from favoriteuser where username= :username")
    fun deleteUser(username: String)

    @Query("SELECT * from favoriteUser")
    fun getAllFavorite(): LiveData<List<FavoriteUser>>


}