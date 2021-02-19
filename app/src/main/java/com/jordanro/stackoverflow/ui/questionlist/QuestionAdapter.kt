package com.jordanro.stackoverflow.ui.questionlist

import android.view.ViewGroup
import androidx.paging.PagedList
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil

import com.jordanro.stackoverflow.data.entities.Question

class QuestionAdapter(val listener :QuestionAdapterListener) : PagedListAdapter<Question, QuestionViewHolder>(QuestionComparator){



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuestionViewHolder {
       return QuestionViewHolder.create(parent,listener)
    }

    override fun onBindViewHolder(holder: QuestionViewHolder, position: Int) {
        val item = getItem(position)
        if(item != null) {
            holder.onBindViewHolder(item)
        }

    }

    override fun onCurrentListChanged(
        previousList: PagedList<Question>?,
        currentList: PagedList<Question>?
    ) {
        super.onCurrentListChanged(previousList, currentList)
        listener.onCurrentListChanged()
    }

    interface QuestionAdapterListener{
        fun onQuestionClick(item:Question)
        fun onCurrentListChanged()
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