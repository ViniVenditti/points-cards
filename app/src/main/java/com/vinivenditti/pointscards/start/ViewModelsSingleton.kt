package com.vinivenditti.pointscards.start

import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import com.vinivenditti.pointscards.games.bisca.model.PlayerModel

object StartViewModelSingleton {
    private var startViewModel: StartViewModel? = null

    fun getInstance(activity: FragmentActivity): StartViewModel {
        return startViewModel ?: ViewModelProvider(activity)[StartViewModel::class.java].also {
            startViewModel = it
        }
    }
}
