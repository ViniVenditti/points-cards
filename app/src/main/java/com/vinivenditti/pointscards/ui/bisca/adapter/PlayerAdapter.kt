package com.vinivenditti.pointscards.ui.bisca.adapter

import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vinivenditti.pointscards.databinding.PlayerLayoutBinding
import com.vinivenditti.pointscards.model.PlayerModel
import com.vinivenditti.pointscards.ui.start.StartViewModel

class PlayerAdapter(var players: Int, private val viewModel: StartViewModel, var match: Int) : RecyclerView.Adapter<PlayerAdapter.ViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view = PlayerLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind()
        holder.item.editTextPlayer.hint = "Jogador ${position + 1}"
    }

    override fun getItemCount(): Int = players

    inner class ViewHolder(internal val item: PlayerLayoutBinding): RecyclerView.ViewHolder(item.root) {
        fun bind() {
            item.editTextPlayer.setOnKeyListener { v, keyCode, event ->
                if((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER) && !item.editTextPlayer.text.toString().isEmpty()) {
                    viewModel.addPlayer(PlayerModel(name = item.editTextPlayer.text.toString(), match = match))
                }
                false
            }
        }
    }
}

