package com.jordanro.stackoverflow.ui.questionlist

import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil

import com.jordanro.stackoverflow.data.entities.Question

class QuestionAdapter(val listener :QuestionItemClickListener) : PagedListAdapter<Question, QuestionViewHolder>(QuestionComparator){



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuestionViewHolder {
       return QuestionViewHolder.create(parent,listener)
    }

    override fun onBindViewHolder(holder: QuestionViewHolder, position: Int) {
        val item = getItem(position)
        if(item != null) {
            holder.onBindViewHolder(item)
        }

    }

    interface QuestionItemClickListener{
        fun onQuestionClick(item:Question)
    }

    companion object {
        val QuestionComparator = object : DiffUtil.ItemCallback<Question>() {
            override fun areContentsTheSame(oldItem: Question, newItem: Question): Boolean =
                oldItem == newItem

            override fun areItemsTheSame(oldItem: Question, newItem: Question): Boolean =
                oldItem.questionId == newItem.questionId
        }
    }
}