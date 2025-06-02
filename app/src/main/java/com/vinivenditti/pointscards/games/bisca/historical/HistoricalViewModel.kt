package com.vinivenditti.pointscards.games.bisca.historical

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.vinivenditti.pointscards.games.bisca.model.ScoreBiscaModel
import com.vinivenditti.pointscards.repository.FirebaseRepository

class HistoricalViewModel : ViewModel() {
    private val database = FirebaseRepository()

    private val _day = MutableLiveData<String>()
    val day: LiveData<String> = _day
    private val _matches = MutableLiveData<List<String>>()
    val matches: LiveData<List<String>> = _matches
    private val _listPoints = MutableLiveData<List<ScoreBiscaModel>>()
    val listPoints: LiveData<List<ScoreBiscaModel>> = _listPoints

    fun setDay(day: String) {
        this._day.value = day
    }

    fun getListPoints(match: String, phoneId: String) {
        database.getListPointsHistorical(phoneId, day.value!!, match) {
            _listPoints.value = it
        }
    }

}