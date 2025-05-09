package com.vinivenditti.pointscards.ui.truco.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vinivenditti.pointscards.R
import com.vinivenditti.pointscards.databinding.PointsTrucoLayoutBinding

class TrucoAdapter(private val points: Int, private val drawable: Int): RecyclerView.Adapter<TrucoAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemBinding = PointsTrucoLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(position, drawable)
    }

    override fun getItemCount(): Int = points

    class ViewHolder(private val item: PointsTrucoLayoutBinding): RecyclerView.ViewHolder(item.root) {
        fun bind(pos: Int, drawable: Int) {
            var enable = false
            item.trucoPoints.text = (pos+1).toString()
            item.trucoPoints.setOnClickListener {
                if (enable) {
                    item.trucoPoints.setBackgroundResource(drawable)
                } else {
                    item.trucoPoints.setBackgroundResource(R.drawable.container_truco_points)
                }
                enable = !enable
            }
        }
    }
}