package com.vinivenditti.pointscards.repository

import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.GenericTypeIndicator
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import com.vinivenditti.pointscards.games.bisca.model.MatchModel
import com.vinivenditti.pointscards.games.bisca.model.ScoreBiscaModel
import java.time.LocalDate

class FirebaseRepository {
    private val reference = Firebase.database.reference

    fun getListPointsHistorical(
        phoneId: String,
        day: String,
        match: String,
        callback: (List<ScoreBiscaModel>) -> Unit
    ) {
        reference.child(phoneId).child(day).child(match)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val list = snapshot.children.mapNotNull { it.getValue(ScoreBiscaModel::class.java) }
                    callback(list)
                }
                override fun onCancelled(error: DatabaseError) {}
            })
    }

    fun savePlayers(phoneId: String, day: String?, match: String, listPoints: List<ScoreBiscaModel>) {
        reference.child(phoneId)
            .child(day?: LocalDate.now().toString())
            .child(match)
            .setValue(listPoints)
    }

    fun setMatch(phoneId: String, callback: (Int) -> Unit) {
        reference.child(phoneId)
            .child(LocalDate.now().toString())
            .orderByKey()
            .limitToLast(1)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val lastMatch = snapshot.children.lastOrNull()?.key?.toIntOrNull()
                    callback(lastMatch ?: 0)
                }
                override fun onCancelled(error: DatabaseError) {}
            })
    }

    fun uploadDays(phoneId: String, callback: (List<String>) -> Unit) {
        reference.child(phoneId)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val days = snapshot.children.mapNotNull { it.key }
                    callback(days)
                }
                override fun onCancelled(error: DatabaseError) {}
            })
    }

    fun uploadMatches(phoneId: String, day: String, callback: (List<String>) -> Unit) {
        reference.child(phoneId).child(day)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val games = snapshot.children.mapNotNull { it.key }
                    callback(games)
                }
                override fun onCancelled(error: DatabaseError) {}
            })
    }

    fun verifyGame(phoneId: String, callback: (Pair<Pair<String, String>, MatchModel>) -> Unit) {
        reference.child(phoneId)
            .orderByKey()
            .limitToLast(1)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val typeIndicator = object : GenericTypeIndicator<List<ScoreBiscaModel>>() {}
                    val lastMatch = snapshot.children.lastOrNull()?.children?.lastOrNull()
                    val players = lastMatch?.getValue(typeIndicator) ?: emptyList()
                    val match = lastMatch?.key?.toIntOrNull() ?: 0
                    val day = snapshot.children.last().key ?: LocalDate.now().toString()
                    callback(Pair(Pair(day, "true"), MatchModel(match = match, listPlayers = players)))
                }
                override fun onCancelled(error: DatabaseError) {}
            })
    }
}