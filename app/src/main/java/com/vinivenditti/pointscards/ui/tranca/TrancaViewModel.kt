package com.vinivenditti.pointscards.ui.tranca

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TrancaViewModel: ViewModel() {
    private val _wePoints = MutableLiveData<List<Int>>(listOf<Int>(0))
    val wePoints: LiveData<List<Int>> = _wePoints

    private val _themPoints = MutableLiveData<List<Int>>(listOf<Int>(0))
    val themPoints: LiveData<List<Int>> = _themPoints

    fun addPoints(we: Int, them: Int) {
        val currentWeList = _wePoints.value ?: emptyList()
        val currentThemList = _themPoints.value ?: emptyList()

        _wePoints.value = currentWeList + we
        _themPoints.value = currentThemList + them
    }

    fun sumWePoints(): Int {
        return _wePoints.value?.sum() ?: 0
    }

    fun sumThemPoints(): Int {
        return _themPoints.value?.sum() ?: 0
    }
}