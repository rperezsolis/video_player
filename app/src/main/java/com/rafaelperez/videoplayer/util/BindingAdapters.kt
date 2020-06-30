package com.rafaelperez.videoplayer.util

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.rafaelperez.videoplayer.R

@BindingAdapter("imageUrl")
fun setImageUrl(imageView: ImageView, url: String?) {
    if(url!=null) {
        Glide.with(imageView.context)
            .load(url)
            .apply(RequestOptions().centerCrop().circleCrop().placeholder(R.drawable.ic_launcher_foreground))
            .into(imageView)
    } else {
        Glide.with(imageView.context)
            .load(R.drawable.ic_launcher_foreground)
            .into(imageView)
    }
}