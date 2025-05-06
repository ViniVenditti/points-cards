package com.vinivenditti.pointscards.listener

import com.vinivenditti.pointscards.model.PlayerModel

interface PlayerListener {
    fun updatePlayer(player: PlayerModel)
}
