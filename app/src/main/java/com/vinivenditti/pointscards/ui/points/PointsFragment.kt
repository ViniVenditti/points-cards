package com.vinivenditti.pointscards.ui.points

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.map
import androidx.recyclerview.widget.LinearLayoutManager
import com.vinivenditti.pointscards.databinding.FragmentPointsBinding
import com.vinivenditti.pointscards.ui.adapter.PlayerGameAdapter
import com.vinivenditti.pointscards.ui.start.StartViewModel
import com.vinivenditti.pointscards.ui.start.StartViewModelSingleton

class PointsFragment : Fragment() {

    private var _binding: FragmentPointsBinding? = null
    private lateinit var adapter: PlayerGameAdapter
    private lateinit var startViewModel: StartViewModel
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
        val list = startViewModel.players.map {
            it.values.toList()
        }
        adapter = PlayerGameAdapter(list.value!!)
        binding.recyclerViewPlayers.layoutManager = LinearLayoutManager(context)
        binding.recyclerViewPlayers.adapter = adapter

        val viewHolder = binding.recyclerViewPlayers.findViewHolderForAdapterPosition(0)

        addListeners()

        return binding.root
    }

    private fun addListeners() {
        binding.buttonCalculate.setOnClickListener {
            val viewHolder =
            startViewModel.calculatePoints()
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}