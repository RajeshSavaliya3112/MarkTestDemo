package com.example.gituserdemo.mvvm.repositories.room_repository

import android.content.Context
import androidx.room.Room



class DatabaseClient {

    private var appDataBase : AppDataBase? = null

    companion object{
        var instance = DatabaseClient()
    }

    fun getAppDataBase(context: Context) : AppDataBase?{
        if(appDataBase == null)
            appDataBase = Room.databaseBuilder(context, AppDataBase::class.java,"app_databse").build()
        return appDataBase
    }

}