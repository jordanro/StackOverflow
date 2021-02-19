package com.jordanro.stackoverflow.data.entities

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.jordanro.stackoverflow.repositories.QuestionBoundaryCallback
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

data class QuestionResult(
    val data: LiveData<PagedList<Question>>,
    val networkErrors: LiveData<String>,
    val boundaryCallback : QuestionBoundaryCallback

)
{
    suspend fun refresh() {
        withContext(Dispatchers.IO) {
            boundaryCallback.refresh()
            delay(200)
        }
    }
}