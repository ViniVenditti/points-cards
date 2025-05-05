package com.vinivenditti.pointscards.ui.points

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.vinivenditti.pointscards.model.PlayerModel

class PointsViewModel : ViewModel() {
    private val _score = MutableLiveData<Int>()
    val score: LiveData<Int> = _score
    private val _round = MutableLiveData<Int>()
    val round: LiveData<Int> = _round

    val players: List<PlayerModel> = listOf()

    fun calculatePoints() {

    }

}