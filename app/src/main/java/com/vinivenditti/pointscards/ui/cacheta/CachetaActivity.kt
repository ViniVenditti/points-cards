package com.vinivenditti.pointscards.ui.cacheta

import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.vinivenditti.pointscards.R
import com.vinivenditti.pointscards.databinding.ActivityCachetaBinding
import com.vinivenditti.pointscards.ui.bisca.BiscaActivity
import com.vinivenditti.pointscards.ui.cacheta.adapter.CachetaAdapter
import com.vinivenditti.pointscards.ui.start.StartViewModel
import com.vinivenditti.pointscards.ui.start.StartViewModelSingleton

class CachetaActivity : AppCompatActivity() {

    private val binding: ActivityCachetaBinding by lazy { ActivityCachetaBinding.inflate(layoutInflater) }
    private lateinit var startViewModel: StartViewModel
    private lateinit var adapter: CachetaAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        startViewModel = StartViewModelSingleton.getInstance(this)

        adapter = CachetaAdapter(startViewModel.playersCacheta.value!!)
        binding.recyclerViewPlayers.layoutManager = LinearLayoutManager(this)
        binding.recyclerViewPlayers.adapter = adapter

        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                AlertDialog.Builder(this@CachetaActivity)
                    .setTitle("Sair do jogo")
                    .setMessage("Deseja sair do jogo?")
                    .setPositiveButton("Sim") { _, _ ->
                        StartViewModelSingleton.getInstance(this@CachetaActivity).resetPlayers()
                        finish()
                    }
                    .setNegativeButton("Não", null)
                    .show()
            }
        })


    }
}