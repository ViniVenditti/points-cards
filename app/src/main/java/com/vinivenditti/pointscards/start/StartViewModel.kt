package com.vinivenditti.pointscards.start

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.vinivenditti.pointscards.games.bisca.model.MatchModel
import com.vinivenditti.pointscards.games.bisca.model.ScoreBiscaModel
import java.time.LocalDate


class StartViewModel(application: Application) : AndroidViewModel(application) {

    private val _listPlayers = MutableLiveData<List<String>>()
    val listPlayers: LiveData<List<String>> = _listPlayers
    private val _continueGame = MutableLiveData<Pair<Pair<String, String>, MatchModel>>()
    val continueGame: LiveData<Pair<Pair<String, String>, MatchModel>> = _continueGame

    fun setContinueGame(continueGame: Pair<Pair<String, String>, MatchModel>, callback: () -> Unit) {
        _continueGame.postValue(continueGame)
        callback()
    }

    fun addPlayer(player: String){
        val currentList = _listPlayers.value ?: mutableListOf()
        val newList = (currentList + player).toSet().toList()
        _listPlayers.value = newList
    }

    fun deletePlayer(name: String) {
        val currentList = _listPlayers.value ?: mutableListOf()
        val player = currentList.find { it == name }
        val newList = currentList.minus(player!!)
        _listPlayers.value = newList
    }

   fun clearContinueGame(match: Int) {
       _continueGame.value = Pair(Pair(LocalDate.now().toString(), "false"), MatchModel(match+1, emptyList()))
    }
}