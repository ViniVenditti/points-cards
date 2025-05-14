package com.vinivenditti.pointscards.ui.start

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.vinivenditti.pointscards.repository.PlayerRepository


class StartViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = PlayerRepository.getInstance(application)

    private val _listPlayers = MutableLiveData<List<String>>()
    val listPlayers: LiveData<List<String>> = _listPlayers

    fun addPlayer(player: String){
        val currentList = _listPlayers.value ?: mutableListOf()
        val newList = currentList.plus(player)
        _listPlayers.value = newList
    }

    fun deletePlayer(name: String) {
        val currentList = _listPlayers.value ?: mutableListOf()
        val player = currentList.find { it == name }
        val newList = currentList.minus(player!!)
        _listPlayers.value = newList
    }
}