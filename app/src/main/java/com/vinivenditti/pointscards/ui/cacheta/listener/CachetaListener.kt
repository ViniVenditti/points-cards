package com.vinivenditti.pointscards.ui.cacheta.listener

import com.vinivenditti.pointscards.model.PlayerCachetaModel

interface CachetaListener {
    fun calculateCachetaPoints(player: PlayerCachetaModel)
    fun updateCachetaPlayer(player: PlayerCachetaModel)
}