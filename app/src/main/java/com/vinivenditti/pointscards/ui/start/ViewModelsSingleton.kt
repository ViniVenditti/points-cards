package com.vinivenditti.pointscards.ui.start

import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import com.vinivenditti.pointscards.model.PlayerModel

object StartViewModelSingleton {
    private var startViewModel: StartViewModel? = null

    fun getInstance(activity: FragmentActivity): StartViewModel {
        return startViewModel ?: ViewModelProvider(activity)[StartViewModel::class.java].also {
            startViewModel = it
        }
    }
}

object DataSingleton {
    private val _sharedData = MutableLiveData<List<PlayerModel>>()
    val sharedData: LiveData<List<PlayerModel>> get() = _sharedData

    fun updateData(players: List<PlayerModel>) {
        _sharedData.postValue(players)
    }
}
