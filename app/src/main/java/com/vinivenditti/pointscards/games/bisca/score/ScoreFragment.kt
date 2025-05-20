package com.vinivenditti.pointscards.games.bisca.score

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.vinivenditti.pointscards.databinding.FragmentScoreBinding
import com.vinivenditti.pointscards.games.bisca.BiscaViewModel
import com.vinivenditti.pointscards.games.bisca.BiscaViewModelFactory
import com.vinivenditti.pointscards.start.StartViewModelSingleton
import kotlin.getValue

class ScoreFragment : Fragment() {

    private var _binding: FragmentScoreBinding? = null
    private val adapter: ScoreAdapter by lazy { ScoreAdapter() }
    //private val scoreViewModel: ScoreViewModel by lazy { ScoreViewModel(BiscaViewModel(StartViewModelSingleton.getInstance(requireActivity()))) }
    private val biscaViewModel: BiscaViewModel by lazy {
        val biscaViewModelFactory = BiscaViewModelFactory(StartViewModelSingleton.getInstance(requireActivity()))
        ViewModelProvider(requireActivity(), biscaViewModelFactory)[BiscaViewModel::class.java]
    }
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentScoreBinding.inflate(inflater, container, false)

        binding.recyclerViewPlayers.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.recyclerViewPlayers.adapter = adapter

        observe()
        return binding.root
    }

    private fun observe() {
        biscaViewModel.listPoints.observe(viewLifecycleOwner) {
            adapter.updateList(it)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}