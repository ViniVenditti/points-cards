package com.vinivenditti.pointscards.ui.cacheta.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vinivenditti.pointscards.R
import com.vinivenditti.pointscards.databinding.PlayersCachetaLayoutBinding
import com.vinivenditti.pointscards.model.PlayerCachetaModel
import com.vinivenditti.pointscards.ui.start.StartViewModel

class CachetaAdapter(private val startViewModel: StartViewModel): RecyclerView.Adapter<CachetaAdapter.CachetaViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CachetaViewHolder {
        val view = PlayersCachetaLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CachetaViewHolder(view)
    }

    override fun onBindViewHolder(holder: CachetaViewHolder, position: Int) {
        holder.bind(startViewModel.playersCacheta.value!![position])
    }

    override fun getItemCount(): Int = startViewModel.playersCacheta.value!!.size

    inner class CachetaViewHolder(private val item: PlayersCachetaLayoutBinding) : RecyclerView.ViewHolder(item.root) {
        fun bind(player: PlayerCachetaModel) {
            if(player.points <= 0) {
                item.viewCachetaPlayer.setBackgroundResource(R.color.md_theme_errorContainer)
                item.checkboxPlay.isEnabled = false
                item.buttonCalculate.isEnabled = false
            }
            item.textPlayers.text = player.name
            item.textPoints.text = if(player.points > 0) player.points.toString() else "perdeu"
            item.checkboxPlay.isChecked = player.played
            item.buttonCalculate.isEnabled = player.played

            item.checkboxPlay.setOnCheckedChangeListener { _, isChecked ->
                item.buttonCalculate.isEnabled = isChecked
            }

            item.checkboxPlay.setOnClickListener {
                startViewModel.updateCachetaPlayer(PlayerCachetaModel(player.name, item.checkboxPlay.isChecked, player.points))
            }

            item.buttonCalculate.setOnClickListener {
                startViewModel.calculateCachetaPoints(player)
                notifyDataSetChanged()
            }
        }
    }
}