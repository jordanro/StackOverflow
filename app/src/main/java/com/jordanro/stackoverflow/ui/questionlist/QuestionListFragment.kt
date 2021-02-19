package com.jordanro.stackoverflow.ui.questionlist

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.jordanro.stackoverflow.MainActivity
import com.jordanro.stackoverflow.R
import com.jordanro.stackoverflow.data.entities.Question
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.question_list_fragment.*

@AndroidEntryPoint
class QuestionListFragment : Fragment(), QuestionAdapter.QuestionItemClickListener {

    companion object {
        fun newInstance() = QuestionListFragment()
    }
    private val viewModel: QuestionListViewModel by viewModels()
    private val adapter = QuestionAdapter(this)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.question_list_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        filter.setOnCheckedChangeListener { buttonView, isChecked ->
            viewModel.filter(isChecked)
        }
        val decoration = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
        list.addItemDecoration(decoration)
        initAdapter()

        swipeToRefresh.setOnRefreshListener( SwipeRefreshLayout.OnRefreshListener {
            viewModel.doRefresh()
        })
    }

    private fun initAdapter() {
        list.adapter = adapter
        viewModel.questions.observe(viewLifecycleOwner, {
            Log.d("Activity", "list: ${it?.size}")
            adapter.submitList(it)
        })
        viewModel.networkErrors.observe(viewLifecycleOwner,{
            Toast.makeText(context, "\uD83D\uDE28 Wooops $it", Toast.LENGTH_LONG).show()
        })
        viewModel.isRefreshing.observe(viewLifecycleOwner, Observer<Boolean> {
            if(!it) {
                swipeToRefresh.isRefreshing = false
                list.layoutManager?.scrollToPosition(0)
            }
        })
    }

    override fun onQuestionClick(item: Question) {
        (activity as MainActivity).showQuestionDetails(item)
    }

}