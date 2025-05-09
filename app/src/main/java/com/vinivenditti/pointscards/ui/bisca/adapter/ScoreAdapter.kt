package com.vinivenditti.pointscards.ui.bisca.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vinivenditti.pointscards.databinding.TableScoreBinding
import com.vinivenditti.pointscards.model.PlayerModel

class ScoreAdapter(private val listPlayers: List<PlayerModel>) : RecyclerView.Adapter<ScoreAdapter.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view = TableScoreBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listPlayers[position])
    }

    override fun getItemCount(): Int = listPlayers.size

    class ViewHolder(private val item: TableScoreBinding) : RecyclerView.ViewHolder(item.root) {
        fun bind(playerModel: PlayerModel) {
            item.playerName.text = playerModel.name
        }
    }
}