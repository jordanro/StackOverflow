package com.jordanro.stackoverflow

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.jordanro.stackoverflow.data.entities.Question
import com.jordanro.stackoverflow.ui.questiondtail.QuestionDetailsFragment
import com.jordanro.stackoverflow.ui.questionlist.QuestionListFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.container, QuestionListFragment.newInstance())
                    .commitNow()
        }
    }

    fun showQuestionDetails(question: Question){
        supportFragmentManager.beginTransaction()
            .add(R.id.container, QuestionDetailsFragment.newInstance(question))
            .addToBackStack("QuestionDetailsFragment")
            .commit()
    }
}