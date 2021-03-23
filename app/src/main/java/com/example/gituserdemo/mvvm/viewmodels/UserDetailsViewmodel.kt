package com.example.gituserdemo.mvvm.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.gituserdemo.models.UserModel
import com.example.gituserdemo.models.UsersItemModel
import com.example.gituserdemo.mvvm.BaseResponseListener
import com.example.gituserdemo.mvvm.repositories.UserDataRepositories
import com.example.gituserdemo.mvvm.repositories.room_repository.DatabaseClient
import com.example.gituserdemo.ui.activities.UserDetailsActivity
import com.example.gituserdemo.utilities.backgroundscope
import com.example.gituserdemo.utilities.isOnline
import com.example.gituserdemo.utilities.mainscope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import okhttp3.Dispatcher

class UserDetailsViewmodel : ViewModel() {

    private val TAG = "UserDetailsViewmodel"
    var mActivity : UserDetailsActivity? = null
    val userDataRepository = UserDataRepositories()
    val userDetailModel = MutableLiveData<UserModel>()
    val onProgress = MutableLiveData<String?>()
    val onError = MutableLiveData<String?>()



    fun getUserList(userName: String) = mainscope.launch {

        // first check if room has user
        val mUser = GlobalScope.async(Dispatchers.IO) {
            getUserFromRoom(userName)
        }.await()



        if(mUser != null){
            mainscope.launch {
                userDetailModel.value = mUser!!
                onProgress.value =""
            }
        } else {

            onProgress.value = "Fetching user"

            if(mActivity?.isOnline() == false){
                onError.value = "No internet"
                return@launch
            }

            userDataRepository.getUserDetails(userName, object : BaseResponseListener {
                override fun onBaseResponseListener(response: Any?, error: String?) {

                    if (error == null) {
                        response as UserModel
                        backgroundscope.launch {
                            Log.e(TAG, "onBaseResponseListener: response--> $response" )
                            DatabaseClient.instance.getAppDataBase(mActivity!!)?.userDetailDao()?.insertMedia(response)
                        }
                        mainscope.launch {
                            userDetailModel.value = response as UserModel
                        }
                    } else {
                        Log.e(TAG, "onBaseResponseListener: error--> $error")
                        mainscope.launch {
                            onError.value = error
                        }
                    }
                    mainscope.launch { onProgress.value = null }
                }
            })
        }
    }



    suspend fun getUserFromRoom(name : String): UserModel? {
       val mUserEntity =
            DatabaseClient.instance.getAppDataBase(mActivity!!)?.userDetailDao()?.getUser(name)
       return mUserEntity
    }


}