package com.vinivenditti.pointscards.repository

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.vinivenditti.pointscards.model.PlayerModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = [PlayerModel::class], version = 2)
abstract class PointsDatabase : RoomDatabase() {

    abstract fun matchesDAO(): MatchesDAO

    companion object {
        private lateinit var instance: PointsDatabase

        fun getInstance(context: Context): PointsDatabase {
            if (!::instance.isInitialized) {
                synchronized(PointsDatabase::class) {
                    instance = Room
                        .databaseBuilder(context, PointsDatabase::class.java, "matches")
                        .addMigrations(Migrations.migrationV1toV2)
                        .fallbackToDestructiveMigration(true)
                        .addCallback(DatabaseCallback(context))
                        .build()
                }
            }
            return instance
        }
    }

    private object Migrations {
        val migrationV1toV2: Migration = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE matches ADD COLUMN match INTEGER")
            }
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