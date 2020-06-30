package com.rafaelperez.videoplayer.ui

import android.app.AlertDialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.URLUtil
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.rafaelperez.videoplayer.R
import com.rafaelperez.videoplayer.databinding.FragmentHomeBinding
import com.rafaelperez.videoplayer.databinding.VideoUrlDialogBinding
import com.rafaelperez.videoplayer.viewmodel.HomeViewModel

class HomeFragment : Fragment() {
    private lateinit var viewModel: HomeViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding: FragmentHomeBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        binding.viewModel = viewModel
        viewModel.goToBroadcastView.observe(viewLifecycleOwner, Observer {
            if (it) {
                findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToVideoFragment())
                viewModel.reset()
            }
        })
        viewModel.goToVideoView.observe(viewLifecycleOwner, Observer {
            if (it) {
                askForUrl(inflater, container)
            }
        })

        return binding.root
    }

    private fun askForUrl(inflater: LayoutInflater, container: ViewGroup?) {
        val dialogBuilder: AlertDialog.Builder = AlertDialog.Builder(requireContext())
        val dialogView: VideoUrlDialogBinding = DataBindingUtil.inflate(inflater, R.layout.video_url_dialog, container, false)
        dialogBuilder.setView(dialogView.root)
        val dialog: AlertDialog = dialogBuilder.create()
        dialogView.videoUrlEditText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                dialogView.playButton.isEnabled = URLUtil.isValidUrl(s.toString())
            }
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
        })
        dialogView.playButton.setOnClickListener {
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToVideoPlayerFragment(
                dialogView.videoUrlEditText.text.toString()))
            viewModel.reset()
            dialog.dismiss()
        }
        dialog.show()
    }
}