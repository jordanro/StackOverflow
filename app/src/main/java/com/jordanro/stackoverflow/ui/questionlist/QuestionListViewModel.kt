package com.jordanro.stackoverflow.ui.questionlist

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import androidx.paging.PagedList
import com.jordanro.stackoverflow.data.entities.Question
import com.jordanro.stackoverflow.data.entities.QuestionResult
import com.jordanro.stackoverflow.repositories.QuestionsRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class QuestionListViewModel @ViewModelInject constructor(private val repository: QuestionsRepository): ViewModel() {

    private var questionResult  = MutableLiveData<QuestionResult>()

    private val isFiltered = MutableLiveData<Boolean>(false)

    val questions :LiveData<PagedList<Question>>  = isFiltered.switchMap {
        val loadQuestions = repository.loadQuestions(it)
        questionResult.value = loadQuestions
        loadQuestions.data
    }

    val networkErrors: LiveData<String> = questionResult.switchMap {
        it.networkErrors
    }
    val isRefreshing = MutableLiveData<Boolean>()

    fun doRefresh() {
        isRefreshing.postValue(true)
        CoroutineScope(Dispatchers.Default).launch {

                questionResult.value?.refresh()
                isRefreshing.postValue(false)
                Log.d("BoundaryCallback", "Refresh done")

        }
    }

    fun filter(checked: Boolean) {
        isFiltered.postValue(checked)
        Log.d("BoundaryCallback", "filter $checked")
    }


}