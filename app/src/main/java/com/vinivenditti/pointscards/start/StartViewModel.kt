package com.vinivenditti.pointscards.start

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.vinivenditti.pointscards.games.bisca.model.ScoreBiscaModel
import java.time.LocalDate


class StartViewModel(application: Application) : AndroidViewModel(application) {

    private val _listPlayers = MutableLiveData<List<String>>()
    val listPlayers: LiveData<List<String>> = _listPlayers
    private val _continueGame = MutableLiveData<Pair<MutableMap<String, String>, List<ScoreBiscaModel>?>>()
    val continueGame: LiveData<Pair<MutableMap<String, String>, List<ScoreBiscaModel>?>> = _continueGame

    fun setContinueGame(continueGame: Pair<MutableMap<String, String>, List<ScoreBiscaModel>?>, callback: () -> Unit) {
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

   fun clearContinueGame(infosGame: MutableMap<String, String>) {
       infosGame["day"] = LocalDate.now().toString()
       infosGame["match"] = infosGame["match"]!!.toInt().inc().toString()
       infosGame["continue"] = "false"
       _continueGame.value = Pair(infosGame, emptyList())
    }
}