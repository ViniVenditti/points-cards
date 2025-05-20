package com.vinivenditti.pointscards.games.tranca.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vinivenditti.pointscards.databinding.PointsTrancaLayoutBinding

class TrancaPointsAdapter(): RecyclerView.Adapter<TrancaPointsAdapter.ViewHolder>() {
    private var list = listOf<Int>(0)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = PointsTrancaLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position], position)
    }

    override fun getItemCount(): Int = list.size

    fun updateList(newList: List<Int>) {
        list = newList
        notifyDataSetChanged()
    }

    class ViewHolder(private val item: PointsTrancaLayoutBinding) : RecyclerView.ViewHolder(item.root) {
        fun bind(point: Int, pos: Int) {
            if(pos == 0) item.textPoints.text = ""
            else item.textPoints.text = "rodada ${pos}: ${point}"
        }
    }
}