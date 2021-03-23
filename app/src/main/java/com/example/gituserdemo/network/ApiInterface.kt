package com.example.gituserdemo.network

import com.example.gituserdemo.models.UserModel
import com.example.gituserdemo.models.UsersItemModel
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface ApiInterface {

    @GET("users")
    suspend fun getGithubUserList(
        @Query("since") pageId : Int
    ) : List<UsersItemModel>


    @GET("users/{name}")
    suspend fun getGithubUserDetails(
        @Path("name") pageId : String
    ) : UserModel

}