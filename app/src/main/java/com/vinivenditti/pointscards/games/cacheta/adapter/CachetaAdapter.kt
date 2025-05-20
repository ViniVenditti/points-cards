package com.vinivenditti.pointscards.games.cacheta.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vinivenditti.pointscards.R
import com.vinivenditti.pointscards.databinding.PlayersCachetaLayoutBinding
import com.vinivenditti.pointscards.games.cacheta.listener.CachetaListener
import com.vinivenditti.pointscards.games.cacheta.model.PlayerCachetaModel

class CachetaAdapter: RecyclerView.Adapter<CachetaAdapter.CachetaViewHolder>() {
    private var listPlayers = listOf<PlayerCachetaModel>()
    private lateinit var listener: CachetaListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CachetaViewHolder {
        val view = PlayersCachetaLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CachetaViewHolder(view, listener)
    }

    override fun onBindViewHolder(holder: CachetaViewHolder, position: Int) {
        holder.bind(listPlayers[position])
    }

    override fun getItemCount(): Int = listPlayers.size

    fun updateList(players: List<PlayerCachetaModel>) {
        this.listPlayers = players
        notifyDataSetChanged()
    }

    fun attachListener(listener: CachetaListener) {
        this.listener = listener
    }

    class CachetaViewHolder(private val item: PlayersCachetaLayoutBinding, val listener: CachetaListener) : RecyclerView.ViewHolder(item.root) {
        fun bind(player: PlayerCachetaModel) {
            if(player.points <= 0) {
                item.viewCachetaPlayer.setBackgroundResource(R.color.md_theme_errorContainer)
                item.checkboxPlay.isEnabled = false
                item.buttonCalculate.isEnabled = false
                item.textPoints.text = "perdeu"
            }else{
                item.textPoints.text = player.points.toString()
            }
            item.textPlayers.text = player.name
            item.checkboxPlay.isChecked = player.played
            item.buttonCalculate.isEnabled = player.played

            item.checkboxPlay.setOnCheckedChangeListener { _, isChecked ->
                item.buttonCalculate.isEnabled = isChecked
                listener.updateCachetaPlayer(PlayerCachetaModel(player.name, isChecked, player.points))
            }

            item.buttonCalculate.setOnClickListener {
                listener.calculateCachetaPoints(player)
            }
        }
    }
}