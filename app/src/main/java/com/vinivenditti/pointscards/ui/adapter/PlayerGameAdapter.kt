package com.vinivenditti.pointscards.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vinivenditti.pointscards.databinding.PointsLayoutBinding
import com.vinivenditti.pointscards.model.PlayerModel

class PlayerGameAdapter : RecyclerView.Adapter<PlayerGameAdapter.PlayerGameViewHolder>() {

    private var playerModels: List<PlayerModel> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayerGameViewHolder {
        val view = PointsLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PlayerGameViewHolder(view)
    }

    override fun onBindViewHolder(holder: PlayerGameViewHolder, position: Int) {
        holder.bind(playerModels[position])
    }

    fun updatePlayer(list: List<PlayerModel>) {
        playerModels = list
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = playerModels.size

    class PlayerGameViewHolder(private val item: PointsLayoutBinding) : RecyclerView.ViewHolder(item.root) {
        fun bind(playerModel: PlayerModel) {
            item.textPlayer.text = playerModel.name
        }

    }
}