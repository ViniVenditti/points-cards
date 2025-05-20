package com.vinivenditti.pointscards.games.cacheta.listener

import com.vinivenditti.pointscards.games.cacheta.model.PlayerCachetaModel

interface CachetaListener {
    fun calculateCachetaPoints(player: PlayerCachetaModel)
    fun updateCachetaPlayer(player: PlayerCachetaModel)
}