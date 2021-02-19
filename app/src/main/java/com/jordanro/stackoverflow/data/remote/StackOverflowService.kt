package com.jordanro.stackoverflow.data.remote

import com.jordanro.stackoverflow.data.entities.QuestionList
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

// /2.2/questions?page=1&pagesize=10&order=desc&sort=creation&site=stackoverflow
interface StackOverflowService {

    @GET("questions")
    suspend fun loadQuestions(
        @Query("page") pageNum: Int,
        @Query("pagesize") pageSize: Int,
        @Query("order") order: String = "desc",
        @Query("sort") sort: String = "creation",
        @Query("site") site: String = "stackoverflow",
    ) : Response<QuestionList>
}