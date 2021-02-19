package com.jordanro.stackoverflow.ui.questionlist

import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.jordanro.stackoverflow.R
import com.jordanro.stackoverflow.data.entities.Question
import com.jordanro.stackoverflow.utils.UIUtil
import com.squareup.picasso.Picasso
import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter
import java.util.*

class QuestionViewHolder(view: View): RecyclerView.ViewHolder(view) {

    private val title: TextView = view.findViewById(R.id.title)
    private val tags: TextView = view.findViewById(R.id.tags)
    private val createdAt: TextView = view.findViewById(R.id.createdAt)
    private val isAnswered: TextView = view.findViewById(R.id.isAnswered)
    private val thumbnail : ImageView = view.findViewById(R.id.thumbnail)
    private val ownerName: TextView = view.findViewById(R.id.ownerName)
    private val reputation: TextView = view.findViewById(R.id.reputation)

    private var imageSize: Int = UIUtil.convertDPToPixel(itemView.context,48f)

    private lateinit var question : Question

    fun onBindViewHolder(item: Question?) {
        this.question = item!!
        title.text = Html.fromHtml(item.title)
        tags.text = item.tags.toString()
        ownerName.text = item.owner.name
        reputation.text = item.owner.reputation.toString()
        isAnswered.visibility = if(item.isAnswered ) View.VISIBLE else View.GONE
        val date = Date(item.createdAt*1000)
        createdAt.text = itemView.context.getString(R.string.asked_at,dateFormatter.format(date),
            timeFormatter.format(date))

        Picasso.get()
            .load(item.owner.profileImage)
            .resize(imageSize, imageSize)
            .centerCrop()
            .into(thumbnail)

        itemView.tag = item
    }

    companion object {

        var dateFormatter = SimpleDateFormat("MMM d ''yy")
        var timeFormatter = SimpleDateFormat("HH:mm")

        fun create(parent: ViewGroup,listener: QuestionAdapter.QuestionItemClickListener): QuestionViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.question_item, parent, false)
            view.setOnClickListener{
                listener.onQuestionClick(it.tag as Question)
            }
            return QuestionViewHolder(view)
        }
    }
}