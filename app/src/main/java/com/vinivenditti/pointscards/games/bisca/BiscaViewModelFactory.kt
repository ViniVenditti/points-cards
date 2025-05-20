package com.vinivenditti.pointscards.games.bisca

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.vinivenditti.pointscards.start.StartViewModel

class BiscaViewModelFactory(private val startViewModel: StartViewModel) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(BiscaViewModel::class.java)) {
            return BiscaViewModel(startViewModel) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}