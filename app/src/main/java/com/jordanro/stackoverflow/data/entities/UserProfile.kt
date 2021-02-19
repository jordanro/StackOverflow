package com.jordanro.stackoverflow.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

data class UserProfile(
    @SerializedName("user_id") val userId:Int,
    @ColumnInfo(name = "owner_name") @SerializedName("display_name") val name : String,
    @ColumnInfo(name = "owner_profile_image") @SerializedName("profile_image") val profileImage : String?,
    @ColumnInfo(name = "owner_reputation") val reputation : Int
)