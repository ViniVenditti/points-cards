package com.vinivenditti.pointscards.games.bisca.score

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.vinivenditti.pointscards.databinding.TableScoreBinding
import com.vinivenditti.pointscards.model.ScoreBiscaModel

class ScoreAdapter: RecyclerView.Adapter<ScoreAdapter.ViewHolder>() {
    private var listPlayers = listOf<ScoreBiscaModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = TableScoreBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listPlayers[position])
    }

    override fun getItemCount(): Int = listPlayers.size

    fun updateList(list: List<ScoreBiscaModel>) {
        listPlayers = list
        notifyDataSetChanged()
    }

    class ViewHolder(private val item: TableScoreBinding) : RecyclerView.ViewHolder(item.root) {
        fun bind(player: ScoreBiscaModel) {
            item.playerName.text = player.name
            item.recyclerPointsPlayers.layoutManager = LinearLayoutManager(itemView.context,LinearLayoutManager.VERTICAL, false)
            item.recyclerPointsPlayers.adapter = PointsScoreAdapter(player.listPoints)
        }
    }
}