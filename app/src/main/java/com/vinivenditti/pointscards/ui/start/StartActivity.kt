package com.vinivenditti.pointscards.ui.start

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.AdapterView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.vinivenditti.pointscards.MainActivity
import com.vinivenditti.pointscards.R
import com.vinivenditti.pointscards.databinding.ActivityStartBinding
import com.vinivenditti.pointscards.ui.adapter.PlayerAdapter

class StartActivity : AppCompatActivity() {
    private val binding: ActivityStartBinding by lazy { ActivityStartBinding.inflate(layoutInflater) }
    private lateinit var adapter: PlayerAdapter
    private lateinit var startViewModel: StartViewModel
    private var match = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)

        startViewModel = StartViewModelSingleton.getInstance(this)

        adapter = PlayerAdapter(3, startViewModel, match)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding.recyclerViewPlayers.layoutManager = LinearLayoutManager(this)
        binding.recyclerViewPlayers.adapter = adapter

        setListeners()
    }

    private fun setListeners() {
        binding.spinnerPlayers.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, pos: Int, id: Long) {
                uploadRecyclerView(pos + 3)
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        binding.buttonPlay.setOnClickListener {
            match++
            //startViewModel.savePlayers()
            uploadRecyclerView(3)
            startActivity(Intent(this, MainActivity::class.java))
        }
    }

    fun uploadRecyclerView(playersCount: Int){
        val rv: RecyclerView = binding.recyclerViewPlayers
        adapter = PlayerAdapter(playersCount, startViewModel, match)
        rv.setHasFixedSize(false)
        rv.adapter = adapter
    }
}