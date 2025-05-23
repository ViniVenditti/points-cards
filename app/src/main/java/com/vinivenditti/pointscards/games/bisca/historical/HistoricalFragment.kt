package com.vinivenditti.pointscards.games.bisca.historical

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.core.view.get
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import com.vinivenditti.pointscards.R
import com.vinivenditti.pointscards.databinding.FragmentHistoricalBinding
import com.vinivenditti.pointscards.games.bisca.BiscaViewModel
import com.vinivenditti.pointscards.games.bisca.BiscaViewModelFactory
import com.vinivenditti.pointscards.games.bisca.score.ScoreAdapter
import com.vinivenditti.pointscards.start.StartViewModelSingleton
import kotlin.jvm.java

class HistoricalFragment : Fragment() {

    private var _binding: FragmentHistoricalBinding? = null
    private val binding get() = _binding!!
    private val viewModel: HistoricalViewModel by viewModels()
    private val database = Firebase.database.reference
    private val adapter: ScoreAdapter by lazy { ScoreAdapter() }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentHistoricalBinding.inflate(inflater, container, false)

        binding.recyclerViewPlayers.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.recyclerViewPlayers.adapter = adapter

        uploadDays()
        setListeners()
        setObservers()
        return binding.root
    }

    private fun setObservers() {
        viewModel.listPoints.observe(viewLifecycleOwner) {
            adapter.updateList(it)
        }
    }

    private fun setListeners() {
        binding.spinnerDay.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                viewModel.setDay(parent?.getItemAtPosition(position).toString())
                uploadMatchs(parent?.getItemAtPosition(position).toString())
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
        binding.spinnerMatch.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                viewModel.getListPoints(parent?.getItemAtPosition(position).toString())
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }

    private fun uploadDays() {
        val days = mutableListOf<String>()

        database.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                days.clear()
                for (dado in snapshot.children) {
                    val day = dado.key
                    day?.let { days.add(it) }
                }
                val adapter = ArrayAdapter(context!!, android.R.layout.simple_spinner_item, days)
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                binding.spinnerDay.adapter = adapter
            }
            override fun onCancelled(error: DatabaseError) {}
        })
    }

    private fun uploadMatchs(day: String) {
        database.child(day).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val games = mutableListOf<String>()
                for (dado in snapshot.children) {
                    val game = dado.key
                    game?.let { games.add(it) }
                }
                val adapter = ArrayAdapter(context!!, android.R.layout.simple_spinner_item, games)
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                binding.spinnerMatch.adapter = adapter
            }

            override fun onCancelled(error: DatabaseError) {}

        })
    }
}