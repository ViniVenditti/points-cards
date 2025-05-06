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
    private val adapter: PlayerGameAdapter = PlayerGameAdapter()
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
        adapter.updateGame(startViewModel.players.value!!)

        binding.recyclerViewPlayers.layoutManager = LinearLayoutManager(context)
        binding.recyclerViewPlayers.adapter = adapter


        addListeners()

        return binding.root
    }

    private fun addListeners() {
        binding.buttonCalculate.setOnClickListener {
            startViewModel.calculatePoints()
            adapter.updateGame(startViewModel.players.value!!)
            resetEditTextFields()
        }
    }

    fun resetEditTextFields() {
        val recyclerView = binding.recyclerViewPlayers
        for (i in 0 until recyclerView.childCount) {
            val viewHolder = recyclerView.findViewHolderForAdapterPosition(i) as? PlayerGameAdapter.PlayerGameViewHolder
            viewHolder?.resetEditTextFields()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}