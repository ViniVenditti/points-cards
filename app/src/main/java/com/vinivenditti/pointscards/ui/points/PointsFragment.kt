package com.vinivenditti.pointscards.ui.points

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.vinivenditti.pointscards.databinding.FragmentPointsBinding
import com.vinivenditti.pointscards.ui.adapter.PlayerGameAdapter
import com.vinivenditti.pointscards.ui.start.StartViewModel

class PointsFragment : Fragment() {

    private var _binding: FragmentPointsBinding? = null
    private lateinit var startViewModel: StartViewModel
    private val adapter: PlayerGameAdapter by lazy { PlayerGameAdapter() }
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val pointsViewModel = ViewModelProvider(this)[PointsViewModel::class.java]

        _binding = FragmentPointsBinding.inflate(inflater, container, false)
        binding.textGame.text = arguments?.getString("game")
        binding.recyclerViewPlayers.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewPlayers.adapter = adapter

        startViewModel = ViewModelProvider(requireActivity()).get(StartViewModel::class.java)

        setObservers(pointsViewModel)
        return binding.root
    }

    private fun setObservers(pointsViewModel: PointsViewModel) {
        startViewModel.listPlayerModel.observe(viewLifecycleOwner){
            adapter.updatePlayer(it.values.toList())
        }
        binding.buttonCalculate.setOnClickListener {
            pointsViewModel.calculatePoints()
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}