package com.jordanro.stackoverflow.repositories

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagedList
import androidx.room.withTransaction
import com.jordanro.stackoverflow.data.entities.Question
import com.jordanro.stackoverflow.data.local.AppDatabase
import com.jordanro.stackoverflow.data.remote.StackOverflowApi
import com.jordanro.stackoverflow.data.remote.StackOverflowService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class QuestionBoundaryCallback(

    private val service: StackOverflowService,
    private val db: AppDatabase,
    private val itemsPerPage: Int,

) : PagedList.BoundaryCallback<Question>() {


    private var lastRequestedPage = 1

    private val _networkErrors = MutableLiveData<String>()
    // LiveData of network errors.
    val networkErrors: LiveData<String>
        get() = _networkErrors

    // avoid triggering multiple requests in the same time
    private var isRequestInProgress = false

    /**
     * Database returned 0 items. We should query the backend for more items.
     */
    override fun onZeroItemsLoaded() {
        Log.d("BoundaryCallback", "onZeroItemsLoaded")
        requestAndSaveData()
    }

    /**
     * When all items in the database were loaded, we need to query the backend for more items.
     */
    override fun onItemAtEndLoaded(itemAtEnd: Question) {
        Log.d("BoundaryCallback", "onItemAtEndLoaded")
        requestAndSaveData()
    }

    private  fun requestAndSaveData() {
        if (isRequestInProgress) return

        CoroutineScope(Dispatchers.IO).launch {
            requestAndSaveData_(false)
        }
    }

    private suspend fun requestAndSaveData_(isRefresh :Boolean) {
        Log.d("BoundaryCallback", "loading page $lastRequestedPage")
        isRequestInProgress = true
        StackOverflowApi.loadQuestions(service, lastRequestedPage, itemsPerPage, { questions ->
            CoroutineScope(Dispatchers.IO).launch {
                db.withTransaction {
                    if(isRefresh){
                        db.questionsDao().deleteAll()
                    }
                    db.questionsDao().insertAll(questions)
                    Log.d("BoundaryCallback", "loading page done $lastRequestedPage")
                    lastRequestedPage++
                    isRequestInProgress = false
                }
            }

        }, { error ->
            _networkErrors.postValue(error)
            isRequestInProgress = false
        })
    }

    suspend fun refresh() {
        lastRequestedPage = 1
        requestAndSaveData_(true)
        delay(200)
    }


}