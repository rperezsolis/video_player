package com.rafaelperez.videoplayer.ui

import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.URLUtil
import android.widget.MediaController
import android.widget.VideoView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.rafaelperez.videoplayer.R
import com.rafaelperez.videoplayer.databinding.FragmentVideoPlayerBinding


class VideoPlayerFragment : Fragment() {
    private lateinit var binding: FragmentVideoPlayerBinding
    private var currentPosition = 0

    companion object {
        private const val VIDEO_SAMPLE = "https://developers.google.com/training/images/tacoma_narrows.mp4"
        private const val PLAYBACK_TIME = "play_time";
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_video_player, container, false)
        if (savedInstanceState != null) {
            currentPosition = savedInstanceState.getInt(PLAYBACK_TIME);
        }
        val controller = MediaController(requireContext())
        controller.setMediaPlayer(binding.videoView)
        binding.videoView.setMediaController(controller)

        return binding.root
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(PLAYBACK_TIME, binding.videoView.currentPosition)
    }

    override fun onStart() {
        super.onStart()
        initializePlayer()
    }

    override fun onPause() {
        super.onPause()
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            binding.videoView.pause();
        }
    }

    override fun onStop() {
        super.onStop()
        releasePlayer()
    }

    private fun initializePlayer() {
        binding.videoStatus.visibility = VideoView.VISIBLE
        val videoPath: Uri? = getMedia(VIDEO_SAMPLE)
        if (videoPath!=null) {
            binding.videoView.setVideoPath(VIDEO_SAMPLE)
            binding.videoView.setOnPreparedListener {
                binding.videoStatus.visibility = VideoView.INVISIBLE
                if (currentPosition>0) {
                    binding.videoView.seekTo(currentPosition)
                } else {
                    binding.videoView.seekTo(1)
                }
                binding.videoView.start()
            }
            binding.videoView.setOnCompletionListener {
                binding.videoView.seekTo(0)
            }
        } else {
            Snackbar.make(binding.root, "Invalid url", Snackbar.LENGTH_SHORT).show()
        }
    }

    private fun getMedia(mediaName: String) : Uri? {
        return if (URLUtil.isValidUrl(mediaName)) Uri.parse(mediaName) else null
    }

    private fun releasePlayer() {
        binding.videoView.stopPlayback()
    }
}