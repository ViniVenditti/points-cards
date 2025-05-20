package com.vinivenditti.pointscards.games.cacheta

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.vinivenditti.pointscards.model.PlayerCachetaModel
import com.vinivenditti.pointscards.start.StartViewModel

class CachetaViewModel(startViewModel: StartViewModel) : ViewModel() {
    private val _players = MutableLiveData<List<PlayerCachetaModel>>()
    val players: LiveData<List<PlayerCachetaModel>> = _players

    val listPlayers = MediatorLiveData<String>().apply {
        addPlayer(startViewModel.listPlayers)
    }

    fun addPlayer(data: LiveData<List<String>>) {
        data.observeForever { it ->
            val newPlayerCachetaList = it.map { name ->
                PlayerCachetaModel(name, false, 10)
            }
            _players.value = newPlayerCachetaList
        }
    }

    fun updateCachetaPlayer(player: PlayerCachetaModel) {
        val currentList = _players.value ?: mutableListOf()
        _players.value = currentList.map {
            if (it.name == player.name) {
                player
            } else {
                it
            }
        }
    }

    fun calculateCachetaPoints(player: PlayerCachetaModel) {
        val currentList = _players.value ?: mutableListOf()
        _players.value = currentList.map {
            if (it.name == player.name) {
                it.points = player.points
            } else {
                if (it.played) {
                    it.points = it.points - 2
                } else {
                    it.points = it.points - 1
                }
            }
            it.played = false
            it
        }
    }
}