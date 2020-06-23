package com.rafaelperez.videoplayer.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.rafaelperez.videoplayer.R
import com.rafaelperez.videoplayer.databinding.FragmentVideoBinding

class VideoFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding: FragmentVideoBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_video, container, false)
        return binding.root
    }
}