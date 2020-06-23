package com.rafaelperez.videoplayer.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HomeViewModel : ViewModel() {

    private var _goToVideoView = MutableLiveData<Boolean>()
    val goToVideoView: LiveData<Boolean>
        get() = _goToVideoView

    fun navigate() {
        _goToVideoView.value = true
    }

    fun reset() {
        _goToVideoView.value = false
    }
}