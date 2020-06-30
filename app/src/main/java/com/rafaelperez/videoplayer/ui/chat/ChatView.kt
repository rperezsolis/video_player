package com.rafaelperez.videoplayer.ui.chat

import android.animation.ObjectAnimator
import android.content.Context
import android.content.Intent
import android.graphics.Path
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.animation.doOnEnd
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rafaelperez.videoplayer.R
import com.rafaelperez.videoplayer.domain.Comment
import kotlinx.android.synthetic.main.chat_view.view.*
import java.text.SimpleDateFormat
import java.util.*


class ChatView : ConstraintLayout {

    private lateinit var mView: View
    private lateinit var adapter: ChatAdapter
    private var startTime: Long = 0

    companion object {
        val sdf = SimpleDateFormat("mm:ss")
        const val userPictureUrl = "https://avatars0.githubusercontent.com/u/43014319?s=460&u=3e607d82f27337b91a94afb2b585eb19429fe7cb&v=4"
        const val LIKE_REACTION = 0
        const val LOVE_REACTION = 2
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
        val recyclerView = mView.findViewById(R.id.chatRecyclerView) as RecyclerView
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

        mView.likeReaction.setOnClickListener {
            runAnim(LIKE_REACTION)
        }

        mView.loveReaction.setOnClickListener {
            runAnim(LOVE_REACTION)
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

    private fun runAnim(reaction: Int) {
        val view = ImageView(context)
        when (reaction) {
            LIKE_REACTION -> view.setImageResource(R.drawable.ic_reaction_like)
            LOVE_REACTION -> view.setImageResource(R.drawable.ic_reaction_love)
        }
        (mView as ConstraintLayout).addView(view)
        val screenWidth = context.resources.displayMetrics.widthPixels.toFloat()
        val screenHeight = context.resources.displayMetrics.heightPixels.toFloat()/2
        val path1 = Path().apply {
            arcTo(screenWidth/4, screenHeight/2, screenWidth*3/4, screenHeight, 0f, -90f, false)
            addArc(screenWidth/4, 0f, screenWidth*3/4, screenHeight/2, 90f, 180f)
        }
        val animator = ObjectAnimator.ofFloat(view, View.X, View.Y, path1).apply {
            duration = 5000
            start()
        }
        animator.doOnEnd {
            (mView as ConstraintLayout).removeView(view)
        }
    }
}
