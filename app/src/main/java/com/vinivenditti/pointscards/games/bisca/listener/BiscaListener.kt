package com.vinivenditti.pointscards.games.bisca.listener

import com.vinivenditti.pointscards.games.bisca.model.PlayerModel

interface BiscaListener {
    fun updatePlayer(player: PlayerModel)
}