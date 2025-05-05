package com.vinivenditti.pointscards.repository

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.vinivenditti.pointscards.model.PlayerModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = [PlayerModel::class], version = 1)
abstract class PointsDatabase : RoomDatabase() {

    abstract fun matchesDAO(): MatchesDAO

    companion object {
        private lateinit var instance: PointsDatabase

        fun getInstance(context: Context): PointsDatabase {
            if (!::instance.isInitialized) {
                synchronized(PointsDatabase::class) {
                    instance = Room
                        .databaseBuilder(context, PointsDatabase::class.java, "matches")
                        .fallbackToDestructiveMigration(true)
                        .addCallback(DatabaseCallback(context))
                        .allowMainThreadQueries()
                        .build()
                }
            }
            return instance
        }
    }

    private class DatabaseCallback(val context: Context) : Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)

            CoroutineScope(Dispatchers.IO).launch {
                getInstance(context).matchesDAO()
            }

        }
    }
}