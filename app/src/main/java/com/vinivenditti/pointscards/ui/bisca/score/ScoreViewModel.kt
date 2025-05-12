package com.vinivenditti.pointscards.ui.bisca.score

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.vinivenditti.pointscards.model.PlayerModel
import com.vinivenditti.pointscards.model.Points
import com.vinivenditti.pointscards.model.ScoreBiscaModel

class ScoreViewModel: ViewModel() {
    private val _players = MutableLiveData<List<ScoreBiscaModel>>()
    val players: LiveData<List<ScoreBiscaModel>> = _players

    fun addPlayer(list: List<PlayerModel>) {
        var newList = mutableListOf<ScoreBiscaModel>()
        list.forEach { p ->
            val player = ScoreBiscaModel(
                name = p.name,
                listPoints = listOf(Points(
                    doing = p.doing,
                    done = p.done,
                    score = p.score
                ))
            )
            newList.add(player)
        }
        _players.value = newList
    }

    fun updatePointsPlayer(player: PlayerModel) {
        val currentList = _players.value ?: mutableListOf<ScoreBiscaModel>()
        var newList = currentList.map {
            if (it.name == player.name) {
                it.listPoints.plus(Points(
                    doing = player.doing,
                    done = player.done,
                    score = player.score
                ))

            } else {
                it
            }
        }
        _players.value = newList as List<ScoreBiscaModel>?
    }
}