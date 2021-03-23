package com.example.gituserdemo.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "UserModel")
data class UserModel(

	@ColumnInfo(name = "gists_url")
	@field:SerializedName("gists_url")
	val gistsUrl: String? = "",

	@ColumnInfo(name = "repos_url")
	@field:SerializedName("repos_url")
	val reposUrl: String? = "",

	@ColumnInfo(name = "following_url")
	@field:SerializedName("following_url")
	val followingUrl: String? = "",

	@ColumnInfo(name = "twitter_username")
	@field:SerializedName("twitter_username")
	val twitterUsername: String? = "",

	@ColumnInfo(name = "bio")
	@field:SerializedName("bio")
	val bio: String? = "",

	@ColumnInfo(name = "created_at")
	@field:SerializedName("created_at")
	val createdAt: String? = "",

	@PrimaryKey()
	@field:SerializedName("login")
	val login: String  = "",

	@ColumnInfo(name = "type")
	@field:SerializedName("type")
	val type: String?  = "",

	@ColumnInfo(name = "blog")
	@field:SerializedName("blog")
	val blog: String?  = "",

	@ColumnInfo(name = "subscriptions_url")
	@field:SerializedName("subscriptions_url")
	val subscriptionsUrl: String? = "",

	@ColumnInfo(name = "updated_at")
	@field:SerializedName("updated_at")
	val updatedAt: String?  = "",

	@ColumnInfo(name = "site_admin")
	@field:SerializedName("site_admin")
	val siteAdmin: Boolean,

	@ColumnInfo(name = "company")
	@field:SerializedName("company")
	val company: String?  = "",


	@ColumnInfo(name = "id")
	@field:SerializedName("id")
	val id: Int = 0,

	@ColumnInfo(name = "public_repos")
	@field:SerializedName("public_repos")
	val publicRepos: Int = 0,

	@ColumnInfo(name = "gravatar_id")
	@field:SerializedName("gravatar_id")
	val gravatarId: String?  = "",

	@ColumnInfo(name = "email")
	@field:SerializedName("email")
	val email: String?  = "",

	@ColumnInfo(name = "organizations_url")
	@field:SerializedName("organizations_url")
	val organizationsUrl: String?  = "",

	@ColumnInfo(name = "hireable")
	@field:SerializedName("hireable")
	val hireable: String?  = "",

	@ColumnInfo(name = "starred_url")
	@field:SerializedName("starred_url")
	val starredUrl: String?  = "",

	@ColumnInfo(name = "followers_url")
	@field:SerializedName("followers_url")
	val followersUrl: String?  = "",

	@ColumnInfo(name = "public_gists")
	@field:SerializedName("public_gists")
	val publicGists: Int,

	@ColumnInfo(name = "url")
	@field:SerializedName("url")
	val url: String? = "",

	@ColumnInfo(name = "received_events_url")
	@field:SerializedName("received_events_url")
	val receivedEventsUrl: String? = "",

	@ColumnInfo(name = "followers")
	@field:SerializedName("followers")
	val followers: Int = 0,

	@ColumnInfo(name = "avatar_url")
	@field:SerializedName("avatar_url")
	val avatarUrl: String? = "",

	@ColumnInfo(name = "events_url")
	@field:SerializedName("events_url")
	val eventsUrl: String?  = "",

	@ColumnInfo(name = "html_url")
	@field:SerializedName("html_url")
	val htmlUrl: String?  = "",

	@ColumnInfo(name = "following")
	@field:SerializedName("following")
	val following: Int = 0,

	@ColumnInfo(name = "name")
	@field:SerializedName("name")
	val name: String?  = "",

	@ColumnInfo(name = "location")
	@field:SerializedName("location")
	val location: String?  = "",

	@ColumnInfo(name = "node_id")
	@field:SerializedName("node_id")
	val nodeId: String? = ""
)
