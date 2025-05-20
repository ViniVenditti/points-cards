package com.vinivenditti.pointscards.games.bisca.repository

import android.content.Context
import com.vinivenditti.pointscards.games.bisca.model.PlayerModel

class PlayerRepository private constructor(context: Context) {

    private val database = PointsDatabase.getInstance(context).matchesDAO()

    companion object {
        private lateinit var instance: PlayerRepository
        fun getInstance(context: Context): PlayerRepository {
            synchronized(this) {
                if (!::instance.isInitialized) {
                    instance = PlayerRepository(context)
                }
                return instance
            }
        }
    }

    suspend fun savePlayers(players: List<PlayerModel>) {
        players.forEach {
            database.savePlayer(it)
        }
    }

}