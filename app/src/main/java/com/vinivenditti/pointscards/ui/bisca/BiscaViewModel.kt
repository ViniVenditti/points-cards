package com.vinivenditti.pointscards.ui.bisca

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.vinivenditti.pointscards.model.PlayerModel
import com.vinivenditti.pointscards.model.Points
import com.vinivenditti.pointscards.model.ScoreBiscaModel
import com.vinivenditti.pointscards.ui.start.StartViewModel

class BiscaViewModel(startViewModel: StartViewModel): ViewModel() {

    private val _players = MutableLiveData<List<PlayerModel>>()
    val players: LiveData<List<PlayerModel>> = _players
    private val _score = MutableLiveData<List<PlayerModel>>()
    val score: LiveData<List<PlayerModel>> = _score
    private val _listPoints = MutableLiveData<List<ScoreBiscaModel>>()
    val listPoints: LiveData<List<ScoreBiscaModel>> = _listPoints

    val listPlayers = MediatorLiveData<String>().apply {
        addPlayer(startViewModel.listPlayers)
    }

    fun addPlayer(data: LiveData<List<String>>) {
        data.observeForever { it ->
            val listPlayers = it.map { name ->
                PlayerModel(name = name, score = 0, doing = null, done = null, match = 0)
            }
            val listPoints = it.map { name ->
                ScoreBiscaModel(name = name, listPoints = listOf(Points(0, 0, 0)))
            }
            _players.value = listPlayers
            _score.value = listPlayers
            _listPoints.value = listPoints
        }
    }

    fun updatePlayer(player: PlayerModel) {
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
            addPoints(player)
        }
        _players.value = _score.value
    }

    private fun addPoints(player: PlayerModel) {
        val currentList = _listPoints.value ?: mutableListOf()
        val newList = currentList.map {
            if (it.name == player.name) {
                val updatedPointsList = it.listPoints.toMutableList().apply {
                    add(Points(player.doing ?: 0, player.done ?: 0, player.score))
                }.toList()
                it.copy(listPoints = updatedPointsList)
            } else {
                it
            }
        }
        _listPoints.value = newList
    }

}