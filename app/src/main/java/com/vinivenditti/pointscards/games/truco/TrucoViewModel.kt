package com.vinivenditti.pointscards.games.truco

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class TrucoViewModel : ViewModel() {
    private val _weScore = MutableStateFlow(0)
    val weScore: StateFlow<Int> = _weScore

    private val _themScore = MutableStateFlow(0)
    val themScore: StateFlow<Int> = _themScore

    fun incrementWe(points: Int) {
        _weScore.value = (_weScore.value + points).coerceAtMost(12)
    }

    fun incrementThem(points: Int) {
        _themScore.value = (_themScore.value + points).coerceAtMost(12)
    }

    fun setWeScore(points: Int) {
        _weScore.value = points.coerceIn(0, 12)
    }

    fun setThemScore(points: Int) {
        _themScore.value = points.coerceIn(0, 12)
    }

    fun reset() {
        _weScore.value = 0
        _themScore.value = 0
    }
}
