package com.vinivenditti.pointscards.ui.start

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.vinivenditti.pointscards.MainActivity
import com.vinivenditti.pointscards.R
import com.vinivenditti.pointscards.databinding.ActivityStartBinding
import com.vinivenditti.pointscards.ui.adapter.PlayerAdapter

class StartActivity : AppCompatActivity() {
    private val binding: ActivityStartBinding by lazy { ActivityStartBinding.inflate(layoutInflater) }
    private var adapter: PlayerAdapter = PlayerAdapter(3, this)
    private val startViewModel: StartViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setListeners()

        binding.recyclerViewPlayers.layoutManager = LinearLayoutManager(this)
        binding.recyclerViewPlayers.adapter = adapter
    }

    private fun setListeners() {
        binding.buttonPlay.setOnClickListener {
            handleSave()
            val intent = Intent(this, MainActivity::class.java)
            val bundle = Bundle()
            bundle.putString("game", binding.spinnerGame.selectedItem.toString())
            intent.putExtras(bundle)
            startActivity(intent)
        }

        binding.spinnerPlayers.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, pos: Int, id: Long) {
                val qtdJogadores = pos + 3
                val rv: RecyclerView = binding.recyclerViewPlayers
                adapter = PlayerAdapter(qtdJogadores, this@StartActivity)
                rv.setHasFixedSize(false)
                rv.adapter = adapter
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }

    private fun handleSave() {
        startViewModel.savePoints()
    }


}