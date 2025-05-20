package com.vinivenditti.pointscards.games.bisca.score

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vinivenditti.pointscards.databinding.PointsPlayersBinding
import com.vinivenditti.pointscards.model.Points

class PointsScoreAdapter(private val listPoints: List<Points>) : RecyclerView.Adapter<PointsScoreAdapter.PointsScoreViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PointsScoreViewHolder {
        val view = PointsPlayersBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PointsScoreViewHolder(view)
    }

    override fun onBindViewHolder(holder: PointsScoreViewHolder, position: Int) {
        holder.bind(listPoints[position])
    }

    override fun getItemCount(): Int = listPoints.size

    class PointsScoreViewHolder(private val item: PointsPlayersBinding) : RecyclerView.ViewHolder(item.root) {
        fun bind(points: Points) {
            item.textDoing.text = points.doing.toString()
            item.textDone.text = points.done.toString()
            item.textScore.text = points.score.toString()
        }
    }
}