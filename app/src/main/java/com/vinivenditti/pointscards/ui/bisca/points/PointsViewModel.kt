package com.vinivenditti.pointscards.ui.bisca.points

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.vinivenditti.pointscards.model.PlayerModel
import com.vinivenditti.pointscards.ui.start.StartViewModel

class PointsViewModel : ViewModel() {
    private val _score = MutableLiveData<Int>()
    val score: LiveData<Int> = _score
    private val _round = MutableLiveData<Int>()
    val round: LiveData<Int> = _round

    fun calculatePoints() {

    }

}