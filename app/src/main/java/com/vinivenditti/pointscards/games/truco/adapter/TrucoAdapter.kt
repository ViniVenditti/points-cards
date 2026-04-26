package com.vinivenditti.pointscards.games.truco.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vinivenditti.pointscards.R
import com.vinivenditti.pointscards.databinding.PointsTrucoLayoutBinding

class TrucoAdapter(
    private val points: Int, 
    private val drawable: Int,
    private val onPointClicked: (Int) -> Unit
): RecyclerView.Adapter<TrucoAdapter.ViewHolder>() {
    
    private var currentScore: Int = 0

    fun updateScore(newScore: Int) {
        currentScore = newScore
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemBinding = PointsTrucoLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val isMarked = position < currentScore
        holder.bind(position, isMarked, drawable, onPointClicked)
    }

    override fun getItemCount(): Int = points

    class ViewHolder(private val item: PointsTrucoLayoutBinding): RecyclerView.ViewHolder(item.root) {
        fun bind(pos: Int, isMarked: Boolean, drawable: Int, onPointClicked: (Int) -> Unit) {
            item.trucoPoints.text = (pos + 1).toString()
            
            if (isMarked) {
                item.trucoPoints.setBackgroundResource(drawable)
            } else {
                item.trucoPoints.setBackgroundResource(R.drawable.container_truco_points)
            }
            
            item.trucoPoints.setOnClickListener {
                onPointClicked(pos + 1)
            }
        }
    }
}
