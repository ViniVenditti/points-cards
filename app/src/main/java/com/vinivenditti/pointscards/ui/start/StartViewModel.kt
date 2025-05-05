package com.vinivenditti.pointscards.ui.start

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vinivenditti.pointscards.model.PlayerModel
import com.vinivenditti.pointscards.repository.MatchesRepository
import kotlinx.coroutines.launch

class StartViewModel(application: Application): ViewModel() {
    private val matchesRepository = MatchesRepository.getInstance(application)

    private val _listPlayerModel = MutableLiveData<Map<Int, PlayerModel>>()
    val listPlayerModel: LiveData<Map<Int, PlayerModel>> = _listPlayerModel

    fun addPlayer(pos: Int, playerModel: PlayerModel) {
        val currentList = _listPlayerModel.value ?: mutableMapOf()
        val newList = currentList.plus(Pair(pos, playerModel))
        _listPlayerModel.value = newList
    }

    fun savePoints() {
        viewModelScope.launch {
            for (player in listPlayerModel.value!!.values) {
                matchesRepository.savePoints(player)
            }
        }
    }

}