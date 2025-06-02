package com.vinivenditti.pointscards.games.bisca.points

import android.content.res.ColorStateList
import android.content.res.Resources
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.vinivenditti.pointscards.R
import com.vinivenditti.pointscards.databinding.FragmentPointsBinding
import com.vinivenditti.pointscards.games.bisca.model.PlayerModel
import com.vinivenditti.pointscards.games.bisca.BiscaViewModel
import com.vinivenditti.pointscards.games.bisca.BiscaViewModelFactory
import com.vinivenditti.pointscards.games.bisca.adapter.PlayerGameAdapter
import com.vinivenditti.pointscards.games.bisca.listener.BiscaListener
import com.vinivenditti.pointscards.games.bisca.model.Points
import com.vinivenditti.pointscards.games.bisca.model.ScoreBiscaModel
import com.vinivenditti.pointscards.start.StartViewModelSingleton

class PointsFragment : Fragment() {

    private var _binding: FragmentPointsBinding? = null
    private val adapter: PlayerGameAdapter = PlayerGameAdapter()
    private val biscaViewModel: BiscaViewModel by lazy {
        val biscaViewModelFactory = BiscaViewModelFactory(StartViewModelSingleton.getInstance(requireActivity()))
        ViewModelProvider(requireActivity(), biscaViewModelFactory)[BiscaViewModel::class.java]
    }

    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentPointsBinding.inflate(inflater, container, false)

        binding.recyclerViewPlayers.layoutManager = LinearLayoutManager(context)
        binding.recyclerViewPlayers.adapter = adapter

        val listener = object : BiscaListener {
            override fun updatePlayer(player: PlayerModel) {
                biscaViewModel.updatePlayer(player)
            }
        }
        binding.progressGame.max = biscaViewModel.limitRound
        adapter.attachListener(listener)
        addListeners()
        observe()
        return binding.root
    }

    private fun addListeners() {
        binding.buttonCalculate.setOnClickListener {
            if(verifyEditText()){
                biscaViewModel.calculatePoints()
            } else Toast.makeText(context, "Preencha todos os campos", Toast.LENGTH_SHORT).show()
        }
    }

    private fun verifyEditText(): Boolean {
        for (i in 0 until binding.recyclerViewPlayers.childCount) {
            val viewHolder = binding.recyclerViewPlayers.findViewHolderForAdapterPosition(i) as? PlayerGameAdapter.PlayerGameViewHolder
            viewHolder?.let {
                if(it.item.editDoing.text.toString().trim().isEmpty() ||
                it.item.editDone.text.toString().trim().isEmpty()) return false
            }
        }
        return true
    }

    fun observe() {
        biscaViewModel.round.observe(viewLifecycleOwner) {
            binding.textRound.text = getString(R.string.text_round).plus(it.toString())
            binding.progressGame.progress = it
            if (it == 0) {
                binding.buttonCalculate.isEnabled = false
            }
        }
        biscaViewModel.statusGame.observe(viewLifecycleOwner) {
            when (it) {
                "repetir" -> {
                    binding.progressGame.progressTintList = ColorStateList.valueOf(resources.getColor(R.color.md_theme_tertiaryFixedDim_highContrast, null))
                }
                "descendo" -> {
                    binding.progressGame.progressTintList = ColorStateList.valueOf(resources.getColor(R.color.md_theme_error, null))
                }
            }
        }
        biscaViewModel.players.observe(viewLifecycleOwner) {
            binding.recyclerViewPlayers.post {
                adapter.updateList(it)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}