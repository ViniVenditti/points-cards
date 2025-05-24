package com.vinivenditti.pointscards.games.cacheta

import android.os.Bundle
import android.view.WindowManager
import androidx.activity.OnBackPressedCallback
import androidx.activity.SystemBarStyle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.vinivenditti.pointscards.R
import com.vinivenditti.pointscards.databinding.ActivityCachetaBinding
import com.vinivenditti.pointscards.games.cacheta.model.PlayerCachetaModel
import com.vinivenditti.pointscards.games.cacheta.adapter.CachetaAdapter
import com.vinivenditti.pointscards.games.cacheta.listener.CachetaListener
import com.vinivenditti.pointscards.start.StartViewModelSingleton

class CachetaActivity : AppCompatActivity() {

    private val binding: ActivityCachetaBinding by lazy { ActivityCachetaBinding.inflate(layoutInflater) }
    private val cachetaViewModel: CachetaViewModel by lazy { CachetaViewModel(StartViewModelSingleton.getInstance(this)) }
    private val adapter: CachetaAdapter by lazy { CachetaAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val colorId = ContextCompat.getColor(this, R.color.md_theme_primary)
        enableEdgeToEdge(statusBarStyle = SystemBarStyle.light(colorId, colorId),
            navigationBarStyle = SystemBarStyle.light(colorId,colorId))

        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        supportActionBar?.hide()
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.recyclerViewPlayers.layoutManager = LinearLayoutManager(this)
        binding.recyclerViewPlayers.adapter = adapter

        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                AlertDialog.Builder(this@CachetaActivity)
                    .setTitle("Sair do jogo")
                    .setMessage("Deseja sair do jogo?")
                    .setPositiveButton("Sim") { _, _ ->
                        StartViewModelSingleton.getInstance(this@CachetaActivity)
                        finish()
                    }
                    .setNegativeButton("Não", null)
                    .show()
            }
        })

        val listener = object : CachetaListener {
            override fun calculateCachetaPoints(player: PlayerCachetaModel) {
                cachetaViewModel.calculateCachetaPoints(player)
            }
            override fun updateCachetaPlayer(player: PlayerCachetaModel) {
                cachetaViewModel.updateCachetaPlayer(player)
            }
        }
        adapter.attachListener(listener)
        observer()
    }

    private fun observer() {
        cachetaViewModel.players.observe(this) {
            binding.recyclerViewPlayers.post {
                adapter.updateList(it)
            }
        }
    }
}