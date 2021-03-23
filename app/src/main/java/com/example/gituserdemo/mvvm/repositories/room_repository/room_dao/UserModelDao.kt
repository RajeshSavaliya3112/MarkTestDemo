package com.example.gituserdemo.mvvm.repositories.room_repository.room_dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.gituserdemo.models.UserModel

@Dao
interface UserModelDao {

    @Query("SELECT * FROM UserModel")
    fun getAll() : List<UserModel>

    @Query("SELECT * FROM UserModel WHERE login = :name")
    fun getUser(name : String) : UserModel

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMedia(vararg hiddenMedia : UserModel)

}