package com.jordanro.stackoverflow.ui.questionlist

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


    private val isFiltered = MutableLiveData<Boolean>(false)

    private val fullQuestionResult = repository.loadQuestions(false)
    private val filteredQuestionResult = repository.loadQuestions(true)

    private var questionResult  = MutableLiveData<QuestionResult>(fullQuestionResult)

    val isRefreshing = MutableLiveData<Boolean>()

    val questions :LiveData<PagedList<Question>>  = isFiltered.switchMap {
        val questionResult = if(it) filteredQuestionResult else fullQuestionResult
        this.questionResult.value = questionResult
        questionResult.data
    }

    val networkErrors: LiveData<String> = questionResult.switchMap {
        it.networkErrors
    }

    fun doRefresh() {
        isRefreshing.postValue(true)
        CoroutineScope(Dispatchers.Default).launch {

                questionResult.value?.refresh()
                isRefreshing.postValue(false)
        }
    }

    fun filter(checked: Boolean) {
        isFiltered.postValue(checked)
    }


}