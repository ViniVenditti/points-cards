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
    private val pointsViewModel: PointsViewModel = PointsViewModel()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

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
        pointsViewModel.calculateTotalRounds(startViewModel.players.value!!)

        addListeners()
        observe()
        return binding.root
    }

    private fun addListeners() {
        binding.buttonCalculate.setOnClickListener {
            pointsViewModel.calculateRound()
            startViewModel.calculatePoints()
            adapter.updateGame(startViewModel.players.value!!)
        }
    }

    fun observe() {
        pointsViewModel.round.observe(viewLifecycleOwner) {
            binding.textRound.text = "Rodada $it"
            if (it == 0) {
                binding.buttonCalculate.isEnabled = false
            }
        }
        pointsViewModel.statusGame.observe(viewLifecycleOwner) {
            binding.textRoundDirection.text = it
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }
}