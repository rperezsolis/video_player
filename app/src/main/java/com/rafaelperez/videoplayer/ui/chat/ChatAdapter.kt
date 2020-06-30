package com.rafaelperez.videoplayer.ui.chat

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.rafaelperez.videoplayer.R
import com.rafaelperez.videoplayer.databinding.ChatItemBinding
import com.rafaelperez.videoplayer.domain.Comment

class ChatAdapter : RecyclerView.Adapter<CommentViewHolder>() {

    var data: MutableList<Comment> = ArrayList()

    fun addComment(comment: Comment) {
        data.add(comment)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
        val dataBinding: ChatItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            CommentViewHolder.LAYOUT,
            parent,
            false
        )
        return CommentViewHolder(dataBinding)
    }

    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {
        holder.viewDataBinding.also {
            it.comment = data[position]
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }
}

class CommentViewHolder(val viewDataBinding: ChatItemBinding) : RecyclerView.ViewHolder(viewDataBinding.root) {
    companion object {
        @LayoutRes
        val LAYOUT = R.layout.chat_item
    }
}