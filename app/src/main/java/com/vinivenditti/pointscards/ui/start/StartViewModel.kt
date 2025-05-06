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

    private val _players = MutableLiveData<List<PlayerModel>>()
    val players: LiveData<List<PlayerModel>> = _players

    fun addPlayer(player: PlayerModel) {
        val currentList = _players.value ?: mutableListOf()
        val newList = currentList.plus(player)
        _players.value = newList
    }

    fun resetPlayers(){
        _players.value = mutableListOf()
    }

    fun getFinalPlayer(): List<PlayerModel> {
        return _players.value!!.sortedByDescending { it.score }
    }

    fun savePlayers() {
        viewModelScope.launch {
            repository.savePlayers(_players.value!!)
        }
    }

    fun updatePlayer(player: PlayerModel) {
        val currentList = _players.value ?: mutableListOf()
        val newList = currentList.map {
            if (it.name == player.name) {
                player
            } else {
                it
            }
        }
        _players.value = newList
    }

    fun calculatePoints() {
        for (player in _players.value!!) {
            if (player.doing == player.done){
                player.score += player.done + 10
            } else {
                player.score += player.done
            }
            updatePlayer(player)
        }
    }
}