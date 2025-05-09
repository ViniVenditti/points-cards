package com.vinivenditti.pointscards.ui.cacheta.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vinivenditti.pointscards.databinding.PlayersCachetaLayoutBinding
import com.vinivenditti.pointscards.model.PlayerCachetaModel

class CachetaAdapter(private val players: List<PlayerCachetaModel>): RecyclerView.Adapter<CachetaAdapter.CachetaViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CachetaViewHolder {
        val view = PlayersCachetaLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CachetaViewHolder(view)
    }

    override fun onBindViewHolder(holder: CachetaViewHolder, position: Int) {
        holder.bind(players[position])
    }

    override fun getItemCount(): Int = players.size

    class CachetaViewHolder(private val item: PlayersCachetaLayoutBinding) : RecyclerView.ViewHolder(item.root) {
        fun bind(player: PlayerCachetaModel) {
            item.textPlayers.text = player.name
            item.textPoints.text = player.points.toString()
        }
    }
}