package com.vinivenditti.pointscards.games.bisca.points

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.vinivenditti.pointscards.model.PlayerModel

class PointsViewModel : ViewModel() {
    private val _round = MutableLiveData<Int>(1)
    var round: LiveData<Int> = _round
    private val _statusGame = MutableLiveData<String>("subindo")
    var statusGame: LiveData<String> = _statusGame
    var limitRound: Int = 0

    fun calculateTotalRounds(listPlayers: List<PlayerModel>) {
        when (52 % listPlayers.size) {
            0 -> limitRound = (52 / listPlayers.size)-1
            else -> limitRound = (52 / listPlayers.size)
        }
    }

    fun calculateRound() {
        when (_statusGame.value){
            "subindo" -> {
                _round.value = _round.value?.inc()
                if (_round.value == limitRound) {
                    _statusGame.value = "repetir"
                }
            }
            "repetir" -> {
                _statusGame.value = "descendo"
            }
            else -> {
                _round.value = _round.value?.dec()
            }
        }

    }

}