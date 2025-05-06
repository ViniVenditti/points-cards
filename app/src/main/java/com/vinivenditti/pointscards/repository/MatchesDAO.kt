package com.vinivenditti.pointscards.repository

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.vinivenditti.pointscards.model.PlayerModel
import kotlinx.coroutines.flow.Flow

@Dao
interface MatchesDAO {

    @Query("SELECT * FROM matches")
    fun getAll(): List<PlayerModel>

    @Query("SELECT * FROM matches WHERE date = :date")
    fun getByDate(date: String): Flow<List<PlayerModel>>

    @Insert
    suspend fun savePoints(playerModel: PlayerModel)

    @Insert
    suspend fun savePlayer(playerModel: PlayerModel)

}