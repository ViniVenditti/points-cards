package com.vinivenditti.pointscards.ui.bisca

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.vinivenditti.pointscards.model.PlayerModel
import com.vinivenditti.pointscards.ui.start.StartViewModel

class BiscaViewModel(startViewModel: StartViewModel): ViewModel() {

    private val _players = MutableLiveData<List<PlayerModel>>()
    val players: LiveData<List<PlayerModel>> = _players
    private val _score = MutableLiveData<List<PlayerModel>>()
    val score: LiveData<List<PlayerModel>> = _score

    val listPlayers = MediatorLiveData<String>().apply {
        addPlayer(startViewModel.listPlayers)
    }

    fun addPlayer(data: LiveData<List<String>>) {
        data.observeForever { it ->
            val newList = it.map { name ->
                PlayerModel(name = name, score = 0, doing = null, done = null, match = 0)
            }
            _players.value = newList
            _score.value = newList
        }
    }

    fun updatePlayer(player: PlayerModel, updateScore: Boolean = false) {
        val currentList = _score.value ?: mutableListOf()
        val newList = currentList.map {
            if (it.name == player.name)
                PlayerModel(
                    name = it.name,
                    score = player.score,
                    doing = player.doing,
                    done = player.done,
                    match = it.match
                )
            else
                it
        }
        _score.value = newList
    }

    fun calculatePoints() {
        for (player in _score.value!!) {
            if (player.doing == player.done){
                player.score += player.done!! + 10
            } else {
                player.score += player.done!!
            }
        }
        _players.value = _score.value
    }
}