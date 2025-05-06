package com.vinivenditti.pointscards.ui.adapter

import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.vinivenditti.pointscards.databinding.PointsLayoutBinding
import com.vinivenditti.pointscards.model.PlayerModel
import com.vinivenditti.pointscards.ui.start.StartViewModel
import com.vinivenditti.pointscards.ui.start.StartViewModelSingleton

class PlayerGameAdapter: RecyclerView.Adapter<PlayerGameAdapter.PlayerGameViewHolder>() {

    private var listPlayers: List<PlayerModel> = arrayListOf()
    private lateinit var startViewModel: StartViewModel

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayerGameViewHolder {
        startViewModel = StartViewModelSingleton.getInstance(parent.context as FragmentActivity)
        val view = PointsLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PlayerGameViewHolder(view)
    }

    override fun onBindViewHolder(holder: PlayerGameViewHolder, position: Int) {
        holder.bind(listPlayers[position])
    }

    override fun getItemCount(): Int = listPlayers.size

    fun updateGame(list: List<PlayerModel>){
        listPlayers = list
        notifyDataSetChanged()
    }

    inner class PlayerGameViewHolder(private val item: PointsLayoutBinding) : RecyclerView.ViewHolder(item.root) {

        fun bind(playerModel: PlayerModel) {
            item.textPlayer.text = playerModel.name
            item.editDone.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
                override fun afterTextChanged(s: Editable?) {
                    if (!s.toString().isEmpty()) {
                        startViewModel.updatePlayer(PlayerModel(name = playerModel.name, done = s.toString().toInt(), doing = item.editDoing.text.toString().toInt()))
                    }
                }
            })
        }

        fun resetEditTextFields() {
            item.editDone.setText("")
            item.editDoing.setText("")
        }
    }
}