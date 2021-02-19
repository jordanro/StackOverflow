package com.jordanro.stackoverflow.data.remote

import android.util.Log
import com.jordanro.stackoverflow.data.entities.Question
import com.jordanro.stackoverflow.data.entities.QuestionList
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class StackOverflowApi {

    companion object {

        private const val TAG = "GithubService"

        suspend fun loadQuestions(
            service: StackOverflowService,
            pageNum: Int,
            itemsPerPage: Int,
            onSuccess: (repos: List<Question>) -> Unit,
            onError: (error: String) -> Unit
        ) {
            try {
                val response: Response<QuestionList> =
                    service.loadQuestions(pageNum, itemsPerPage)
                if (response.isSuccessful) {
                    val questions = response.body()?.items ?: emptyList()
                    onSuccess(questions)
                } else {
                    onError(response.errorBody()?.string() ?: "Unknown error")
                }
            }
            catch (t:Throwable){
                onError(t.message ?: "unknown error")
            }
        }
    }
}