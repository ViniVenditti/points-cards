package com.vinivenditti.pointscards.start.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vinivenditti.pointscards.databinding.PlayerLayoutBinding
import com.vinivenditti.pointscards.listener.PlayerListener

class PlayerAdapter: RecyclerView.Adapter<PlayerAdapter.ViewHolder>() {
    internal var list = listOf<String>()
    private lateinit var listener: PlayerListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = PlayerLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view, listener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position], position)
    }

    override fun getItemCount(): Int = list.size

    fun updateList(players: List<String>) {
        this.list = players
        notifyDataSetChanged()
    }

    fun attachListener(listener: PlayerListener) {
        this.listener = listener
    }

    class ViewHolder(internal val item: PlayerLayoutBinding, val listener: PlayerListener): RecyclerView.ViewHolder(item.root) {
        fun bind(name: String, pos: Int) {
            item.textPlayer.text = "Jogador ${pos+1}: "
            item.textPlayerName.text = name
        }

    }

}