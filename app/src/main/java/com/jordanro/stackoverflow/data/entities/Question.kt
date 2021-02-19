package com.jordanro.stackoverflow.data.entities

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "questions")
data class Question (
    @SerializedName("question_id")
    @PrimaryKey val
    questionId:Int,
    val title :String,
    @SerializedName("is_answered")
    val isAnswered : Boolean,
    val link :String,
    val tags :List<String>,
    @Embedded
    val owner: UserProfile,
    @SerializedName("creation_date")
    val createdAt: Long,
    @SerializedName("last_activity_date")
    val lastActivityAt: Long
    ){

    var indexInResponse: Int = -1

}

