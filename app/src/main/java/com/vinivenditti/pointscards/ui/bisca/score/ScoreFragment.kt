package com.vinivenditti.pointscards.ui.bisca.score

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.vinivenditti.pointscards.databinding.FragmentScoreBinding
import com.vinivenditti.pointscards.ui.bisca.adapter.ScoreAdapter
import com.vinivenditti.pointscards.ui.start.StartViewModelSingleton

class ScoreFragment : Fragment() {

    private var _binding: FragmentScoreBinding? = null
    private lateinit var adapter: ScoreAdapter
    private val scoreViewModel: ScoreViewModel by viewModels()
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentScoreBinding.inflate(inflater, container, false)

        val startViewModel = StartViewModelSingleton.getInstance(requireActivity())
        scoreViewModel.addPlayer(startViewModel.players.value!!)
        adapter = ScoreAdapter(scoreViewModel.players.value!!)

        binding.recyclerViewPlayers.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.recyclerViewPlayers.adapter = adapter

        observe()
        return binding.root
    }

    private fun observe() {
        scoreViewModel.players.observe(viewLifecycleOwner) {
            adapter.notifyDataSetChanged()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}