package com.vinivenditti.pointscards.ui.start

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.vinivenditti.pointscards.ui.bisca.BiscaActivity
import com.vinivenditti.pointscards.R
import com.vinivenditti.pointscards.databinding.ActivityStartBinding
import com.vinivenditti.pointscards.listener.PlayerListener
import com.vinivenditti.pointscards.model.PlayerCachetaModel
import com.vinivenditti.pointscards.model.PlayerModel
import com.vinivenditti.pointscards.ui.start.adapter.PlayerAdapter
import com.vinivenditti.pointscards.ui.cacheta.CachetaActivity
import com.vinivenditti.pointscards.ui.tranca.TrancaActivity
import com.vinivenditti.pointscards.ui.truco.TrucoActivity

class StartActivity : AppCompatActivity() {
    private val binding: ActivityStartBinding by lazy { ActivityStartBinding.inflate(layoutInflater) }
    private val adapter: PlayerAdapter by lazy { PlayerAdapter() }
    private lateinit var startViewModel: StartViewModel
    private var match = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        supportActionBar?.hide()

        startViewModel = StartViewModelSingleton.getInstance(this)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding.recyclerViewPlayers.layoutManager = LinearLayoutManager(this)
        binding.recyclerViewPlayers.adapter = adapter

        val listener = object : PlayerListener {
            override fun deletePlayer(name: String) {
                startViewModel.deletePlayer(name)
            }
        }

        adapter.attachListener(listener)
        setListeners()
        observe()
    }

    private fun observe() {
        startViewModel.listPlayers.observe(this) {
            adapter.updateList(it)
        }
    }

    private fun setListeners() {
        binding.buttonAdd.setOnClickListener {
            if (binding.editTextPlayer.text.toString().isNotEmpty()) {
                startViewModel.addPlayer(binding.editTextPlayer.text.toString())
                binding.editTextPlayer.text?.clear()
            } else {
                Toast.makeText(this, "Preencha o nome do jogador", Toast.LENGTH_LONG).show()
            }
        }
        binding.spinnerGame.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, pos: Int, id: Long) {
                when (pos) {
                    3 -> AlertDialog.Builder(this@StartActivity)
                        .setTitle("Jogar Tranca/Buraco?")
                        .setMessage("Deseja iniciar um jogo de tranca/buraco?")
                        .setPositiveButton("Sim") { _, _ ->
                            startActivity(Intent(this@StartActivity, TrancaActivity::class.java))
                        }
                        .setNegativeButton("Não", null)
                        .show()
                    4 -> AlertDialog.Builder(this@StartActivity)
                        .setTitle("Jogar Truco?")
                        .setMessage("Deseja iniciar um jogo de truco?")
                        .setPositiveButton("Sim") { _, _ ->
                            startActivity(Intent(this@StartActivity, TrucoActivity::class.java))
                        }
                        .setNegativeButton("Não", null)
                        .show()
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        binding.buttonPlay.setOnClickListener {
            match++
            //startViewModel.savePlayers()
            when(binding.spinnerGame.selectedItemPosition){
                0 -> {
                    Toast.makeText(this, "Selecione um jogo", Toast.LENGTH_LONG).show()
                }
                1 -> {
                    startActivity(Intent(this, BiscaActivity::class.java))
                }
                2 -> {
                    startActivity(Intent(this, CachetaActivity::class.java))
                }
            }
        }
    }
}