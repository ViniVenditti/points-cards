package com.vinivenditti.pointscards.ui.start

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.android.material.color.utilities.Score
import com.vinivenditti.pointscards.model.PlayerCachetaModel
import com.vinivenditti.pointscards.model.PlayerModel
import com.vinivenditti.pointscards.repository.PlayerRepository
import com.vinivenditti.pointscards.ui.bisca.score.ScoreViewModel
import kotlinx.coroutines.launch


class StartViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = PlayerRepository.getInstance(application)

    private val _players = MutableLiveData<List<PlayerModel>>()
    val players: LiveData<List<PlayerModel>> = _players

    private val _listPlayers = MutableLiveData<List<String>>()
    val listPlayers: LiveData<List<String>> = _listPlayers

    fun addPlayer(player: String){
        val currentList = _listPlayers.value ?: mutableListOf()
        val newList = currentList.plus(player)
        _listPlayers.value = newList
    }

    fun addPlayer(player: PlayerModel) {
        val currentList = _players.value ?: mutableListOf()
        val newList = currentList.plus(player)
        _players.value = newList
    }

    fun deletePlayer(name: String) {
        val currentList = _players.value ?: mutableListOf()
        val player = currentList.find { it.name == name }
        val newList = currentList.minus(player!!)
        _players.value = newList
    }

    fun resetPlayers(){
        val currentList = _players.value ?: mutableListOf()
        val newList = currentList.map {
            it.score = 0
            it.doing = 0
            it.done = 0
            it.match?.plus(1)
            it
        }
    }

    fun savePlayers() {
        viewModelScope.launch {
            repository.savePlayers(_players.value!!)
        }
    }




}