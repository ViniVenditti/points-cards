package com.vinivenditti.pointscards.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull
import java.time.LocalDate

@Entity(tableName = "matches")
class PlayerModel(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int? = null,

    @ColumnInfo(name = "date")
    val date: String = LocalDate.now().toString(),

    @ColumnInfo(name = "match")
    val match: Int? = null,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "doing")
    var doing: Int = 0,

    @ColumnInfo(name = "done")
    var done: Int = 0,

    @ColumnInfo(name = "score")
    var score: Int = 0
)
