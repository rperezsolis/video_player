package com.rafaelperez.videoplayer.ui.chat

import android.content.Context
import android.content.Intent
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rafaelperez.videoplayer.R
import com.rafaelperez.videoplayer.domain.Comment
import kotlinx.android.synthetic.main.chat_view.view.*
import java.text.SimpleDateFormat
import java.util.*


class ChatView : LinearLayout {

    private lateinit var mView: View
    private lateinit var adapter: ChatAdapter
    private var startTime: Long = 0

    companion object {
        val sdf = SimpleDateFormat("mm:ss")
        val userPictureUrl = "https://avatars0.githubusercontent.com/u/43014319?s=460&u=3e607d82f27337b91a94afb2b585eb19429fe7cb&v=4"
    }

    constructor(context: Context) : super(context){
        init(context, null)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(context, attrs)
    }

    private fun init(context: Context, attrs: AttributeSet?) {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        mView = inflater.inflate(R.layout.chat_view, this)
        val recyclerView = (mView.findViewById(R.id.chatRecyclerView)) as RecyclerView
        recyclerView.setHasFixedSize(true)
        val layoutManager = LinearLayoutManager(context)
        layoutManager.stackFromEnd = true
        recyclerView.layoutManager = layoutManager
        adapter = ChatAdapter()
        recyclerView.adapter = adapter

        mView.commentEditText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                mView.sendButton.visibility = View.VISIBLE
            }
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
        })

        mView.sendButton.setOnClickListener {
            addComment()
        }

        mView.shareButton.setOnClickListener {
            share()
        }
    }

    fun initTimer() {
        startTime = System.currentTimeMillis()
    }

    private fun addComment() {
        val period = System.currentTimeMillis() - startTime
        val currentTime = sdf.format(Date(period))
        val comment = Comment(userPictureUrl, "Rafael Perez", currentTime, mView.commentEditText.text.toString())
        adapter.addComment(comment)
        val imm: InputMethodManager? = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
        mView.commentEditText.clearFocus()
        mView.commentEditText.setText("")
        imm?.hideSoftInputFromWindow(mView.commentEditText.windowToken, 0)
        mView.sendButton.visibility = View.GONE
    }

    private fun share() {
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, "This is my text to send.")
            type = "text/plain"
        }
        val shareIntent = Intent.createChooser(sendIntent, null)
        context.startActivity(shareIntent)
    }
}
