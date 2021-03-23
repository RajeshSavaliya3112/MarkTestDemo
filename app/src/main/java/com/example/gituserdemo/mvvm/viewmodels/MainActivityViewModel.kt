package com.example.gituserdemo.mvvm.viewmodels

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.gituserdemo.models.UsersItemModel
import com.example.gituserdemo.mvvm.BaseResponseListener
import com.example.gituserdemo.mvvm.repositories.UserDataRepositories
import com.example.gituserdemo.mvvm.repositories.room_repository.DatabaseClient
import com.example.gituserdemo.ui.activities.MainActivity
import com.example.gituserdemo.utilities.backgroundscope
import com.example.gituserdemo.utilities.isOnline
import com.example.gituserdemo.utilities.mainscope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class MainActivityViewModel : ViewModel(){

    val TAG = "MainActivityViewModel"
    val userDataList  : MutableLiveData<ArrayList<UsersItemModel>> = MutableLiveData(ArrayList<UsersItemModel>())
    val onProgress = MutableLiveData<String?>()
    val onError = MutableLiveData<String?>()
    var mActivity : MainActivity? = null
    val userDataRepositories = UserDataRepositories()


    /// get user list
    fun getUserList(pageId : Int) = mainscope.launch {

        val mUser = GlobalScope.async(Dispatchers.IO) {
                if(pageId == 0)
                    getUsersFromRoom()
                else
                    null
        }.await()

        
        if( mUser?.isNotEmpty() == true){
            val listOld = userDataList.value
            listOld?.addAll(mUser)
            userDataList.value = listOld
            onProgress.value = null
        } else {

            if(mActivity?.isOnline() == false){
                onError.value = "No internet"
                return@launch
            }

            // continue calling api if network is available
            if(userDataList.value == null || userDataList.value?.isEmpty() == true){
                onProgress.value = "Fetching user"
            }

            userDataRepositories.getUserList(pageId, object : BaseResponseListener{
                override fun onBaseResponseListener(response: Any?, error: String?) {
                    if(error == null){
                        val userList = response as List<UsersItemModel>
                        Log.e(TAG, "onBaseResponseListener: userSize --> ${userList.size}")
                        val listOld = userDataList.value
                        backgroundscope.launch {  DatabaseClient.instance.getAppDataBase(mActivity!!)?.usersItemDao()?.insertMedia(*userList.toTypedArray()) }
                        listOld?.addAll(userList)
                        userDataList.value = listOld
                    } else {
                        Log.e(TAG, "onBaseResponseListener: error--> $error" )
                        onError.value = error
                    }
                    onProgress.value = null
                }
            })

        }
    }

    suspend fun getUsersFromRoom(): List<UsersItemModel>? {
        val mUserEntity = DatabaseClient.instance.getAppDataBase(mActivity!!)?.usersItemDao()?.getAll()
        return mUserEntity
    }





}