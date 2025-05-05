package com.vinivenditti.pointscards.repository

import android.content.Context
import com.vinivenditti.pointscards.model.PlayerModel

class MatchesRepository private constructor(context: Context) {

    private val database = PointsDatabase.getInstance(context).matchesDAO()

    companion object {
        private lateinit var instance: MatchesRepository
        fun getInstance(context: Context): MatchesRepository {
            synchronized(this) {
                if (!::instance.isInitialized) {
                    instance = MatchesRepository(context)
                }
                return instance
            }
        }
    }

    suspend fun savePoints(playerModel: PlayerModel) {
        database.savePoints(playerModel)
    }
}