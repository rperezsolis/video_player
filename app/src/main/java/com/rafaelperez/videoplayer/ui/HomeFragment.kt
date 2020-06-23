package com.rafaelperez.videoplayer.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.rafaelperez.videoplayer.R
import com.rafaelperez.videoplayer.databinding.FragmentHomeBinding
import com.rafaelperez.videoplayer.viewmodel.HomeViewModel

class HomeFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding: FragmentHomeBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        val viewModel: HomeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        binding.viewModel = viewModel
        viewModel.goToVideoView.observe(viewLifecycleOwner, Observer {
            if (it) {
                findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToVideoFragment())
                viewModel.reset()
            }
        })

        return binding.root
    }
}