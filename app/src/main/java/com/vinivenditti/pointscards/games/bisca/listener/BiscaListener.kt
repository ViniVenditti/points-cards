package com.vinivenditti.pointscards.games.bisca.listener

import com.vinivenditti.pointscards.model.PlayerModel

interface BiscaListener {
    fun updatePlayer(player: PlayerModel)
}