package com.jordanro.stackoverflow.data.entities

import com.google.gson.annotations.SerializedName

data class QuestionList (
    val items: List<Question>,
    @SerializedName("has_more")
    val hasMore :Boolean
)