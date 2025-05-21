package com.vinivenditti.pointscards.start

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData


class StartViewModel(application: Application) : AndroidViewModel(application) {
    private val _listPlayers = MutableLiveData<List<String>>()
    val listPlayers: LiveData<List<String>> = _listPlayers

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
}