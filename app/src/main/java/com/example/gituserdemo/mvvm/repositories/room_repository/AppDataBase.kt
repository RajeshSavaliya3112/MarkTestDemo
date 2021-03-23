package com.example.gituserdemo.mvvm.repositories.room_repository

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.gituserdemo.models.UserModel
import com.example.gituserdemo.models.UsersItemModel
import com.example.gituserdemo.mvvm.repositories.room_repository.room_dao.UserItemModelDao
import com.example.gituserdemo.mvvm.repositories.room_repository.room_dao.UserModelDao

@Database(
    entities = [UserModel::class, UsersItemModel::class],
    version = 1
)
abstract class AppDataBase : RoomDatabase() {
    abstract fun userDetailDao(): UserModelDao
    abstract fun usersItemDao(): UserItemModelDao
}