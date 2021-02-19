package com.jordanro.stackoverflow.repositories


import android.util.Log
import androidx.paging.LivePagedListBuilder
import androidx.room.withTransaction
import com.jordanro.stackoverflow.data.entities.Question
import com.jordanro.stackoverflow.data.entities.QuestionResult
import com.jordanro.stackoverflow.data.local.AppDatabase
import com.jordanro.stackoverflow.data.local.QuestionsDao
import com.jordanro.stackoverflow.data.remote.StackOverflowApi
import com.jordanro.stackoverflow.data.remote.StackOverflowService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

import java.util.concurrent.Executor
import javax.inject.Inject

class QuestionsRepository @Inject constructor(
    private val remoteDataSource: StackOverflowService,
    private val db: AppDatabase,
    private val itemsPerPage: Int = ITEMS_PER_PAGE

) {

    companion object{
        const val ITEMS_PER_PAGE = 20
    }

    fun loadQuestions(isFiltered: Boolean): QuestionResult {

        val questions = if(isFiltered) db.questionsDao().getAnsweredQuestions() else db.questionsDao().getQuestions()
        val boundaryCallback = QuestionBoundaryCallback(remoteDataSource,db,itemsPerPage)
        val networkErrors = boundaryCallback.networkErrors

        // Get the paged list
        val data = LivePagedListBuilder(questions, itemsPerPage)
            .setBoundaryCallback(boundaryCallback)
            .build()

        return QuestionResult(data,networkErrors,boundaryCallback)
    }

    suspend fun doRefresh(onError: (error: String) -> Unit){

        StackOverflowApi.loadQuestions(remoteDataSource,1,itemsPerPage,
            {
                CoroutineScope(Dispatchers.IO).launch {
                    withContext(Dispatchers.IO) {
                        db.withTransaction {
                            db.questionsDao().deleteAll()
                            db.questionsDao().insertAll(it)
                            Log.d("BoundaryCallback", "Refresh done")
                        }

                    }
                }
            },
            {
                onError(it)
            })

    }
}