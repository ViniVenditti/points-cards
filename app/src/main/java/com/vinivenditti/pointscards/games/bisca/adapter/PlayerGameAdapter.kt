package com.vinivenditti.pointscards.games.bisca.adapter

import android.content.res.ColorStateList
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.vinivenditti.pointscards.R
import com.vinivenditti.pointscards.databinding.PointsLayoutBinding
import com.vinivenditti.pointscards.games.bisca.listener.BiscaListener
import com.vinivenditti.pointscards.games.bisca.model.PlayerModel

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
            val colorGreen = ColorStateList.valueOf(
                ContextCompat.getColor(item.root.context, R.color.md_theme_onSecondaryContainer))
            val colorRed = ColorStateList.valueOf(
                ContextCompat.getColor(item.root.context, R.color.md_theme_errorContainer))
            val colorReady = ColorStateList.valueOf(
                ContextCompat.getColor(item.root.context, R.color.md_theme_surfaceDim))
            item.textPlayer.text = playerModel.name
            item.textPoints.text = playerModel.score.toString()
            item.editDoing.setText("")
            item.editDone.setText("")
            item.editDone.isEnabled = false
            item.editDone.backgroundTintList = colorRed

            item.editDoing.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    if (!s.isNullOrEmpty()) {
                        item.editDone.isEnabled = true
                        item.editDone.backgroundTintList = colorGreen
                    } else {
                        item.editDone.isEnabled = false
                        item.editDone.backgroundTintList = colorRed
                    }
                }

                override fun afterTextChanged(s: android.text.Editable?) {}
            })


            item.editDone.setOnKeyListener { v, keyCode, event ->
                if((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER) && !item.editDone.text.toString().isEmpty()) {
                    item.editDone.backgroundTintList = colorReady
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