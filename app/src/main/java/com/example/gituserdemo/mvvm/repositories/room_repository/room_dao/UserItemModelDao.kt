package com.example.gituserdemo.mvvm.repositories.room_repository.room_dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.gituserdemo.models.UsersItemModel

@Dao
interface UserItemModelDao {

    @Query("SELECT * FROM UsersItemModel")
    fun getAll() : List<UsersItemModel>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMedia(vararg hiddenMedia : UsersItemModel)

}
