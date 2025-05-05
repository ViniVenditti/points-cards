package com.vinivenditti.pointscards.ui.adapter

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.recyclerview.widget.RecyclerView
import com.vinivenditti.pointscards.databinding.PlayerLayoutBinding
import com.vinivenditti.pointscards.model.PlayerModel
import com.vinivenditti.pointscards.ui.start.StartViewModel
import java.time.LocalDate

class PlayerAdapter(var players: Int, private val viewModelStoreOwner: ViewModelStoreOwner) : RecyclerView.Adapter<PlayerAdapter.PlayerViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayerViewHolder {
        val view = PlayerLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PlayerViewHolder(view, viewModelStoreOwner)
    }

    override fun onBindViewHolder(holder: PlayerViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int = players

    class PlayerViewHolder(private val item: PlayerLayoutBinding, viewModelStoreOwner: ViewModelStoreOwner) : RecyclerView.ViewHolder(item.root) {
        private val viewModel = ViewModelProvider(viewModelStoreOwner)[StartViewModel::class.java]
        fun bind(position: Int) {
            item.editTextPlayer.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence,start: Int,count: Int,after: Int) {}
                override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
                override fun afterTextChanged(s: Editable) {
                    viewModel.addPlayer(position, PlayerModel(null, LocalDate.now().toString(), s.toString(), 0, 0, 0))
                }
            })
        }
    }
}