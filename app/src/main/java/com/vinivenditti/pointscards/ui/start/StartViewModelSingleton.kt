package com.vinivenditti.pointscards.ui.start

import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider

object StartViewModelSingleton {
    private var startViewModel: StartViewModel? = null

    fun getInstance(activity: FragmentActivity): StartViewModel {
        return startViewModel ?: ViewModelProvider(activity)[StartViewModel::class.java].also {
            startViewModel = it
        }
    }
}