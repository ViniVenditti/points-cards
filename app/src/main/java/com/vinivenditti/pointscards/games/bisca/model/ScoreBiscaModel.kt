package com.vinivenditti.pointscards.games.bisca.model

data class ScoreBiscaModel(
    val name: String,
    var listPoints: List<Points>
)

data class Points(
    val doing: Int,
    val done: Int,
    val score: Int
)
