package com.vinivenditti.pointscards.games.bisca

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.vinivenditti.pointscards.games.bisca.model.MatchModel
import com.vinivenditti.pointscards.games.bisca.model.PlayerModel
import com.vinivenditti.pointscards.games.bisca.model.Points
import com.vinivenditti.pointscards.games.bisca.model.ScoreBiscaModel
import com.vinivenditti.pointscards.repository.FirebaseRepository
import com.vinivenditti.pointscards.start.StartViewModel
import java.time.LocalDate

class BiscaViewModel(startViewModel: StartViewModel) : ViewModel() {

    private val database = FirebaseRepository()

    private val _players = MutableLiveData<List<PlayerModel>>()
    val players: LiveData<List<PlayerModel>> = _players

    private val _score = MutableLiveData<List<PlayerModel>>()
    val score: LiveData<List<PlayerModel>> = _score

    private val _listPoints = MutableLiveData<List<ScoreBiscaModel>>()
    val listPoints: LiveData<List<ScoreBiscaModel>> = _listPoints

    private val _statusGame = MutableLiveData<String>("subindo")
    var statusGame: LiveData<String> = _statusGame

    private val _round = MutableLiveData<Int>(1)
    var round: LiveData<Int> = _round

    var limitRound: Int = 0
    private var phoneId = ""
    private var match = 0
    private var day = LocalDate.now().toString()

    val listPlayers = MediatorLiveData<String>().apply { addPlayer(startViewModel.listPlayers) }
    fun addPlayer(data: LiveData<List<String>>) {
        data.observeForever { it ->
            val listPlayers = it.map { name ->
                PlayerModel(name = name, score = 0, doing = null, done = null)
            }
            val listPoints = it.map { name ->
                ScoreBiscaModel(name = name, listPoints = listOf(Points(0, 0, 0, 0)))
            }
            _players.value = listPlayers
            _score.value = listPlayers
            _listPoints.value = listPoints
        }
    }

    private fun calculateLimitRound(size: Int) {
        limitRound = when (52 % size) {
            0 -> (52 / size) - 1
            else -> (52 / size)
        }
    }

    val continueGame = MediatorLiveData<Pair<Pair<String, String>, MatchModel>>().apply {
        setContinueGame(startViewModel.continueGame)
    }

    private fun setContinueGame(data: LiveData<Pair<Pair<String, String>, MatchModel>>) {
        data.observeForever { it ->
            day = it.first.first
            match = it.second.match
            val isGameUnfinished = it.second.listPlayers.takeIf { it.isNotEmpty() }
                ?.let { 52.floorDiv(it.size).times(2) > it[0].listPoints.size } == true
            if (isGameUnfinished && it.first.second == "true") {
                _listPoints.value = it.second.listPlayers
                _players.value = it.second.listPlayers.map { player ->
                    PlayerModel(
                        name = player.name,
                        score = player.listPoints.last().score,
                        doing = player.listPoints.last().doing,
                        done = player.listPoints.last().done
                    )
                }
                calculateLimitRound(it.second.listPlayers.size)
                setStatusGame(it.second.listPlayers[0].listPoints)
                _round.value = calculateRound(it.second.listPlayers[0].listPoints.last().round)
                _score.value = _players.value
            } else {
                calculateLimitRound(_listPoints.value!!.size)
            }

        }
    }

    private fun calculateRound(rounds: Int): Int {
        return if(statusGame.value == "subindo")
            rounds+1
        else if(statusGame.value == "repetir")
            rounds
        else
            rounds-1
    }

    private fun setStatusGame(list: List<Points>) {
        list.size.let {
            if (it < limitRound.div(2)) {
                _statusGame.value = "subindo"
            } else if (it == limitRound.div(2)) {
                _statusGame.value = "repetir"
            } else {
                _statusGame.value = "descendo"
            }
        }
    }

    fun setPhoneId(phoneId: String) { this.phoneId = phoneId }

    fun savePlayers() {
        database.savePlayers(phoneId, day, match.toString(), _listPoints.value!!)
    }

    fun updatePlayer(player: PlayerModel) {
        val currentList = _score.value ?: mutableListOf()
        val newList = currentList.map {
            if (it.name == player.name)
                PlayerModel(
                    name = it.name,
                    score = player.score,
                    doing = player.doing,
                    done = player.done
                )
            else
                it
        }
        _score.value = newList
    }

    fun calculatePoints() {
        for (player in _score.value!!) {
            if (player.doing == player.done) {
                player.score += player.done!! + 10
            } else {
                player.score += player.done!!
            }
            addPoints(player)
        }
        calculateStatusGame()
        _players.value = _score.value
    }

    fun calculateStatusGame() {
        when (_statusGame.value) {
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

    private fun addPoints(player: PlayerModel) {
        val currentList = _listPoints.value ?: mutableListOf()
        val newList = currentList.map {
            if (it.name == player.name) {
                val updatedPointsList = it.listPoints.toMutableList().apply {
                    add(Points(player.doing ?: 0, player.done ?: 0, player.score, round.value!!))
                }.toList()
                it.copy(listPoints = updatedPointsList)
            } else {
                it
            }
        }
        _listPoints.value = newList
        savePlayers()
    }

    override fun onCleared() {
        super.onCleared()

    }
}