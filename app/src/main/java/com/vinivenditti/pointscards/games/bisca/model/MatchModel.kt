package com.vinivenditti.pointscards.games.bisca.model

data class MatchModel(
    val match: Int,
    val listPlayers: List<ScoreBiscaModel>
) {
    constructor() : this(0, emptyList())
}

data class ScoreBiscaModel(
    val name: String,
    var listPoints: List<Points>
) {
    constructor() : this("", emptyList())
}

data class Points(
    val doing: Int,
    val done: Int,
    val score: Int,
    val round: Int
) {
    constructor() : this(0, 0, 0, 0)
}
