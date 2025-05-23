package com.vinivenditti.pointscards.games.bisca.historical

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import com.vinivenditti.pointscards.games.bisca.model.ScoreBiscaModel

class HistoricalViewModel : ViewModel() {
    private val database = Firebase.database.reference

    private val _day = MutableLiveData<String>()
    val day: LiveData<String> = _day
    private val _matches = MutableLiveData<List<String>>()
    val matches: LiveData<List<String>> = _matches
    private val _listPoints = MutableLiveData<List<ScoreBiscaModel>>()
    val listPoints: LiveData<List<ScoreBiscaModel>> = _listPoints

    fun setDay(day: String) {
        this._day.value = day
    }

    fun getListPoints(match: String) {
        database.child(day.value!!).child(match).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val list = mutableListOf<ScoreBiscaModel>()
                for (dado in snapshot.children) {
                    val score = dado.getValue(ScoreBiscaModel::class.java)
                    score?.let { list.add(it) }
                }
                _listPoints.value = list
            }
            override fun onCancelled(error: DatabaseError) {}
        })
    }

}