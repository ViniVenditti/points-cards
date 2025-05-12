package com.vinivenditti.pointscards.model

class ScoreBiscaModel(
    val name: String,
    val listPoints: List<Points>
)

class Points(
    val doing: Int,
    val done: Int,
    val score: Int
)
