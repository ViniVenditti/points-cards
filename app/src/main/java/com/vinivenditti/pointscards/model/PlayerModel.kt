package com.vinivenditti.pointscards.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "matches")
class PlayerModel(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int? = null,

    @ColumnInfo(name = "date")
    val date: String? = null,

    @ColumnInfo(name = "name")
    val name: String? = null,

    @ColumnInfo(name = "doing")
    var doing: Int? = null,

    @ColumnInfo(name = "done")
    var done: Int? = null,

    @ColumnInfo(name = "score")
    var score: Int? = null
)
