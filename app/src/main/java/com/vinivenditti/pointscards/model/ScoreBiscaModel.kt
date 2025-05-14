package com.vinivenditti.pointscards.model

data class ScoreBiscaModel(
    val name: String,
    var listPoints: List<Points>
)

data class Points(
    val doing: Int,
    val done: Int,
    val score: Int
)
