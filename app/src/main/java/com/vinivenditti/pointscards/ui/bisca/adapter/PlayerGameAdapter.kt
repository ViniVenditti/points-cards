package com.vinivenditti.pointscards.ui.bisca.adapter

import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View.OnFocusChangeListener
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
            item.textPoints.text = playerModel.score.toString()
            item.editDone.setText("")
            item.editDoing.setText("")

            item.editDone.setOnKeyListener { v, keyCode, event ->
                if((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER) && !item.editDone.text.toString().isEmpty()) {
                    startViewModel.updatePlayer(PlayerModel(
                        name = playerModel.name,
                        done = item.editDone.text.toString().toInt(),
                        doing = item.editDoing.text.toString().toInt(),
                        score = playerModel.score
                    ))
                }
                false
            }
        }
    }
}