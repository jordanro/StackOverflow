package com.jordanro.stackoverflow.ui.questiondtail

import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import com.jordanro.stackoverflow.R
import com.jordanro.stackoverflow.data.entities.Question
import kotlinx.android.synthetic.main.question_details_fragment.*
import java.io.File


class QuestionDetailsFragment : Fragment() {

    companion object {

        const val ITEM_KEY = "ITEM_KEY"

        fun newInstance(item: Question) : QuestionDetailsFragment{
            val result = QuestionDetailsFragment()
            val args= Bundle()
            args.putString(ITEM_KEY, item.link)
            result.arguments = args
            return result
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.onBackPressedDispatcher?.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if(webview !=null && webview.canGoBack()){
                    webview.goBack()
                }else{
                    isEnabled = false
                    activity?.onBackPressed()
                }
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.question_details_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        webview.settings.javaScriptEnabled = true
        webview.webViewClient = webviewClient
        val url = arguments?.getString(ITEM_KEY)!!
        webview.loadUrl(url)
    }

    private val webviewClient = object : WebViewClient() {
        override fun onPageFinished(view: WebView, url: String) {
            progressBar.visibility = View.GONE
        }
    }
}