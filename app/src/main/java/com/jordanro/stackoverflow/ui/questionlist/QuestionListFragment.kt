package com.jordanro.stackoverflow.ui.questionlist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.jordanro.stackoverflow.MainActivity
import com.jordanro.stackoverflow.R
import com.jordanro.stackoverflow.data.entities.Question
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.question_list_fragment.*

@AndroidEntryPoint
class QuestionListFragment : Fragment(), QuestionAdapter.QuestionAdapterListener {

    companion object {
        fun newInstance() = QuestionListFragment()
    }
    private val viewModel: QuestionListViewModel by viewModels()
    private val adapter = QuestionAdapter(this)

    var isFiltering = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.question_list_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        questionFilter.setOnCheckedChangeListener { buttonView, isChecked ->
            isFiltering = true
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

    override fun onCurrentListChanged(){
        if(isFiltering) {
            list.layoutManager?.scrollToPosition(0)
            isFiltering = false
        }
    }

}