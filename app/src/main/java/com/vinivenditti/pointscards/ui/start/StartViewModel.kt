package com.vinivenditti.pointscards.ui.start

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vinivenditti.pointscards.model.PlayerModel
import com.vinivenditti.pointscards.repository.PlayerRepository
import kotlinx.coroutines.launch


class StartViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = PlayerRepository.getInstance(application)

    private val _players = MutableLiveData<Map<Int, PlayerModel>>()
    val players: LiveData<Map<Int, PlayerModel>> = _players

    fun addPlayer(pos: Int, player: PlayerModel) {
        val currentList = _players.value ?: mutableMapOf()
        val newList = currentList.plus(Pair(pos, player))
        _players.value = newList
    }

    fun savePlayers() {
        viewModelScope.launch {
            repository.savePlayers(_players.value!!)
        }
    }

    fun calculatePoints() {
        for (player in _players.value!!) {
            player.value.
        }
    }
}