package com.vinivenditti.pointscards.ui.bisca.points

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.vinivenditti.pointscards.databinding.FragmentPointsBinding
import com.vinivenditti.pointscards.ui.bisca.adapter.PlayerGameAdapter
import com.vinivenditti.pointscards.ui.start.StartViewModel
import com.vinivenditti.pointscards.ui.start.StartViewModelSingleton

class PointsFragment : Fragment() {

    private var _binding: FragmentPointsBinding? = null
    private val adapter: PlayerGameAdapter = PlayerGameAdapter()
    private lateinit var startViewModel: StartViewModel
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private var round: Int = 1

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPointsBinding.inflate(inflater, container, false)
        startViewModel = StartViewModelSingleton.getInstance(requireActivity())
        adapter.updateGame(startViewModel.players.value!!)

        binding.recyclerViewPlayers.layoutManager = LinearLayoutManager(context)
        binding.recyclerViewPlayers.adapter = adapter

        addListeners()

        return binding.root
    }

    private fun addListeners() {
        binding.buttonCalculate.setOnClickListener {
            calculateRound()
            startViewModel.calculatePoints()
            adapter.updateGame(startViewModel.players.value!!)
        }
    }

    private fun calculateRound() {
        val limitRound: Int
        when (52 % startViewModel.players.value!!.size) {
            0 -> limitRound = (52 / startViewModel.players.value!!.size)-1
            else -> limitRound = (52 / startViewModel.players.value!!.size)
        }

        when (binding.textRoundDirection.text){
            "subindo" -> {
                round++
                binding.textRound.text = "Rodada $round"
                if (round == limitRound) {
                    binding.textRoundDirection.text = "repetir"
                }
            }
            "repetir" -> {
                binding.textRound.text = "Rodada $round"
                round--
                binding.textRoundDirection.text = "descendo"
            }
            else -> {
                binding.textRound.text = "Rodada $round"
                round--
            }
        }

        if(round == -1) {
            binding.buttonCalculate.isEnabled = false
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }
}