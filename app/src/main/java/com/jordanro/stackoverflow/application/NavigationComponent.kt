package com.jordanro.stackoverflow.application

import com.jordanro.stackoverflow.data.entities.Question

interface NavigationComponent {

    fun showQuestionDetails(question : Question)
}