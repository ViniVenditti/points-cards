package com.vinivenditti.pointscards.games.bisca.points

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.vinivenditti.pointscards.databinding.FragmentPointsBinding
import com.vinivenditti.pointscards.games.bisca.model.PlayerModel
import com.vinivenditti.pointscards.games.bisca.BiscaViewModel
import com.vinivenditti.pointscards.games.bisca.BiscaViewModelFactory
import com.vinivenditti.pointscards.games.bisca.adapter.PlayerGameAdapter
import com.vinivenditti.pointscards.games.bisca.listener.BiscaListener
import com.vinivenditti.pointscards.start.StartViewModelSingleton

class PointsFragment : Fragment() {

    private var _binding: FragmentPointsBinding? = null
    private val adapter: PlayerGameAdapter = PlayerGameAdapter()
    private val biscaViewModel: BiscaViewModel by lazy {
        val biscaViewModelFactory = BiscaViewModelFactory(StartViewModelSingleton.getInstance(requireActivity()))
        ViewModelProvider(requireActivity(), biscaViewModelFactory)[BiscaViewModel::class.java]
    }
    private val pointsViewModel: PointsViewModel = PointsViewModel()

    // This property is only valid between onCreateView and
    // onDestroyView.
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
        pointsViewModel.calculateTotalRounds(biscaViewModel.players.value!!)
        adapter.attachListener(listener)
        addListeners()
        observe()
        return binding.root
    }

    private fun addListeners() {
        binding.buttonCalculate.setOnClickListener {
            if(verifyEditText()){
                pointsViewModel.calculateRound()
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
        pointsViewModel.round.observe(viewLifecycleOwner) {
            binding.textRound.text = "Rodada $it"
            if (it == 0) {
                binding.buttonCalculate.isEnabled = false
            }
        }
        pointsViewModel.statusGame.observe(viewLifecycleOwner) {
            binding.textRoundDirection.text = it
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