package com.vinivenditti.pointscards.ui.adapter

import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnFocusChangeListener
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
            item.editTextPlayer.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
                override fun afterTextChanged(s: Editable?) {
                    viewModel.addPlayer(PlayerModel(name = s.toString(), match = match))
                }
            })
        }
    }
}

