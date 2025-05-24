package com.vinivenditti.pointscards.games.bisca

import android.provider.Settings
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import com.vinivenditti.pointscards.games.bisca.model.PlayerModel
import com.vinivenditti.pointscards.games.bisca.model.Points
import com.vinivenditti.pointscards.games.bisca.model.ScoreBiscaModel
import com.vinivenditti.pointscards.start.StartViewModel
import java.time.LocalDate

class BiscaViewModel(startViewModel: StartViewModel): ViewModel() {

    private val database = Firebase.database.reference

    private val _players = MutableLiveData<List<PlayerModel>>()
    val players: LiveData<List<PlayerModel>> = _players
    private val _score = MutableLiveData<List<PlayerModel>>()
    val score: LiveData<List<PlayerModel>> = _score
    private val _listPoints = MutableLiveData<List<ScoreBiscaModel>>()
    val listPoints: LiveData<List<ScoreBiscaModel>> = _listPoints
    private var phoneId = ""
    private var match = 0

    val listPlayers = MediatorLiveData<String>().apply {
        addPlayer(startViewModel.listPlayers)
    }

    fun setPhoneId(phoneId: String) {
        this.phoneId = phoneId
    }

    fun addPlayer(data: LiveData<List<String>>) {
        data.observeForever { it ->
            val listPlayers = it.map { name ->
                PlayerModel(name = name, score = 0, doing = null, done = null)
            }
            val listPoints = it.map { name ->
                ScoreBiscaModel(name = name, listPoints = listOf(Points(0, 0, 0)))
            }
            _players.value = listPlayers
            _score.value = listPlayers
            _listPoints.value = listPoints
        }
    }
    fun setMatch(){
        database.child(phoneId).child(LocalDate.now().toString()).orderByKey().limitToLast(1).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val lastMatch = snapshot.children.lastOrNull()?.key?.toIntOrNull()
                if (lastMatch != null) {
                    match = lastMatch+1
                    savePlayers()
                }
            }
            override fun onCancelled(error: DatabaseError) {}
        })
    }

    fun savePlayers(){
        database.child(phoneId).child(LocalDate.now().toString()).child(match.toString()).setValue(_listPoints.value)
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
        savePlayers()
    }

}