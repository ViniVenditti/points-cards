package com.vinivenditti.pointscards.ui.bisca.adapter

import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.vinivenditti.pointscards.databinding.PointsLayoutBinding
import com.vinivenditti.pointscards.model.PlayerCachetaModel
import com.vinivenditti.pointscards.model.PlayerModel
import com.vinivenditti.pointscards.ui.bisca.listener.BiscaListener
import com.vinivenditti.pointscards.ui.cacheta.listener.CachetaListener
import com.vinivenditti.pointscards.ui.start.StartViewModel
import com.vinivenditti.pointscards.ui.start.StartViewModelSingleton

class PlayerGameAdapter: RecyclerView.Adapter<PlayerGameAdapter.PlayerGameViewHolder>() {

    private var listPlayers: List<PlayerModel> = arrayListOf()
    private lateinit var listener: BiscaListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayerGameViewHolder {
        val view = PointsLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PlayerGameViewHolder(view, listener)
    }

    override fun onBindViewHolder(holder: PlayerGameViewHolder, position: Int) {
        holder.bind(listPlayers[position])
    }

    override fun getItemCount(): Int = listPlayers.size

    fun updateList(players: List<PlayerModel>) {
        this.listPlayers = players
        notifyDataSetChanged()
    }

    fun attachListener(listener: BiscaListener) {
        this.listener = listener
    }

    inner class PlayerGameViewHolder(internal val item: PointsLayoutBinding, val listener: BiscaListener) : RecyclerView.ViewHolder(item.root) {

        fun bind(playerModel: PlayerModel) {
            item.textPlayer.text = playerModel.name
            item.textPoints.text = playerModel.score.toString()
            item.editDoing.setText("")
            item.editDone.setText("")

            item.editDone.setOnKeyListener { v, keyCode, event ->
                if((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER) && !item.editDone.text.toString().isEmpty()) {
                    listener.updatePlayer(
                        PlayerModel(
                            name = playerModel.name,
                            done = item.editDone.text.toString().toInt(),
                            doing = item.editDoing.text.toString().toInt(),
                            score = playerModel.score
                        )
                    )
                }
                false
            }
        }
    }
}