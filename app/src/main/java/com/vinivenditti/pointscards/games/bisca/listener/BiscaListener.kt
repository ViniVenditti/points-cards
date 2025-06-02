package com.vinivenditti.pointscards.games.bisca.listener

import com.vinivenditti.pointscards.games.bisca.model.PlayerModel
import com.vinivenditti.pointscards.games.bisca.model.Points

interface BiscaListener {
    fun updatePlayer(player: PlayerModel)
}