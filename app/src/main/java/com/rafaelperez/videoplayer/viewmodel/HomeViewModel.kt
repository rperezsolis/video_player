package com.rafaelperez.videoplayer.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HomeViewModel : ViewModel() {

    private var _goToBroadcastView = MutableLiveData<Boolean>()
    val goToBroadcastView: LiveData<Boolean>
        get() = _goToBroadcastView

    private var _goToVideoView = MutableLiveData<Boolean>()
    val goToVideoView: LiveData<Boolean>
        get() = _goToVideoView

    fun navigateToBroadcastView() {
        _goToBroadcastView.value = true
    }

    fun navigateToVideoView() {
        _goToVideoView.value = true
    }

    fun reset() {
        _goToBroadcastView.value = false
        _goToVideoView.value = false
    }
}