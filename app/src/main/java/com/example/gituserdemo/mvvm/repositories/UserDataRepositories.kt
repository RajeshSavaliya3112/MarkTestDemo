package com.example.gituserdemo.mvvm.repositories

import android.util.Log
import com.example.gituserdemo.mvvm.BaseResponseListener
import com.example.gituserdemo.network.RetrofitHelper
import com.example.gituserdemo.utilities.ioscope
import com.example.gituserdemo.utilities.mainscope
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import java.lang.Exception

class UserDataRepositories {

    var retrofitInterface = RetrofitHelper.instance.getRetrofit()

    fun getUserList(pageId : Int, onResponseListener : BaseResponseListener) = ioscope.launch{
        try {
            val usersDataArray = retrofitInterface.getGithubUserList(pageId)
            mainscope.launch {
                onResponseListener.onBaseResponseListener(usersDataArray,null)
            }
        } catch (e: Exception) {
            mainscope.launch {
                onResponseListener.onBaseResponseListener(null,e.message)
            }
        }
    }


    fun getUserDetails(name : String,  onResponseListener : BaseResponseListener) = ioscope.launch {
        try {
            Log.e("TAG", "getUserDetails: name--> $name" )
            val usersData = retrofitInterface.getGithubUserDetails(name)
            mainscope.launch {
                onResponseListener.onBaseResponseListener(usersData,null)
            }
        } catch (e: Exception) {
            mainscope.launch {
                onResponseListener.onBaseResponseListener(null,e.message)
            }
        }
    }


}