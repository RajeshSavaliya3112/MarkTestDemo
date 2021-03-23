package com.example.gituserdemo.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "UsersItemModel")
data class UsersItemModel(

	@ColumnInfo(name = "gists_url")
	@field:SerializedName("gists_url")
	val gistsUrl: String? = null,

	@ColumnInfo(name = "repos_url")
	@field:SerializedName("repos_url")
	val reposUrl: String? = null,

	@ColumnInfo(name = "following_url")
	@field:SerializedName("following_url")
	val followingUrl: String? = null,

	@ColumnInfo(name = "starred_url")
	@field:SerializedName("starred_url")
	val starredUrl: String? = null,

	@PrimaryKey
	@field:SerializedName("login")
	val login: String = "",

	@ColumnInfo(name = "followers_url")
	@field:SerializedName("followers_url")
	val followersUrl: String? = null,

	@ColumnInfo(name = "type")
	@field:SerializedName("type")
	val type: String? = null,

	@ColumnInfo(name = "url")
	@field:SerializedName("url")
	val url: String? = null,

	@ColumnInfo(name = "subscriptions_url")
	@field:SerializedName("subscriptions_url")
	val subscriptionsUrl: String? = null,

	@ColumnInfo(name = "received_events_url")
	@field:SerializedName("received_events_url")
	val receivedEventsUrl: String? = null,

	@ColumnInfo(name = "avatar_url")
	@field:SerializedName("avatar_url")
	val avatarUrl: String? = null,

	@ColumnInfo(name = "events_url")
	@field:SerializedName("events_url")
	val eventsUrl: String? = null,

	@ColumnInfo(name = "html_url")
	@field:SerializedName("html_url")
	val htmlUrl: String? = null,

	@ColumnInfo(name = "site_admin")
	@field:SerializedName("site_admin")
	val siteAdmin: Boolean? = null,

	@ColumnInfo(name = "id")
	@field:SerializedName("id")
	val id: Int? = null,

	@ColumnInfo(name = "gravatar_id")
	@field:SerializedName("gravatar_id")
	val gravatarId: String? = null,

	@ColumnInfo(name = "node_id")
	@field:SerializedName("node_id")
	val nodeId: String? = null,

	@ColumnInfo(name = "organizations_url")
	@field:SerializedName("organizations_url")
	val organizationsUrl: String? = null
)
