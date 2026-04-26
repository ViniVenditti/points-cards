package com.vinivenditti.pointscards.games.truco

import android.os.Bundle
import android.view.WindowManager
import androidx.activity.OnBackPressedCallback
import androidx.activity.SystemBarStyle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.vinivenditti.pointscards.R
import com.vinivenditti.pointscards.databinding.ActivityTrucoBinding
import com.vinivenditti.pointscards.games.truco.adapter.TrucoAdapter
import kotlinx.coroutines.launch

class TrucoActivity : AppCompatActivity() {

    private val binding: ActivityTrucoBinding by lazy { ActivityTrucoBinding.inflate(layoutInflater) }
    private val viewModel: TrucoViewModel by viewModels()
    
    private lateinit var adapterWe: TrucoAdapter
    private lateinit var adapterThem: TrucoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val colorId = ContextCompat.getColor(this, R.color.md_theme_primary)
        enableEdgeToEdge(statusBarStyle = SystemBarStyle.light(colorId, colorId),
            navigationBarStyle = SystemBarStyle.light(colorId,colorId))

        supportActionBar?.hide()
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setupAdapters()
        setupListeners()
        observeViewModel()

        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                AlertDialog.Builder(this@TrucoActivity)
                    .setTitle(getString(R.string.reset_dialog_title)) // Reusing title or just "Sair"
                    .setMessage("Deseja sair do jogo?")
                    .setPositiveButton(getString(R.string.yes)) { _, _ ->
                        finish()
                    }
                    .setNegativeButton(getString(R.string.no), null)
                    .show()
            }
        })
    }

    private fun setupAdapters() {
        adapterWe = TrucoAdapter(12, R.drawable.container_truco_we_points) { clickedPoint ->
            val current = viewModel.weScore.value
            if (clickedPoint == current) {
                viewModel.setWeScore(current - 1)
            } else {
                viewModel.setWeScore(clickedPoint)
            }
        }

        adapterThem = TrucoAdapter(12, R.drawable.container_truco_them_points) { clickedPoint ->
            val current = viewModel.themScore.value
            if (clickedPoint == current) {
                viewModel.setThemScore(current - 1)
            } else {
                viewModel.setThemScore(clickedPoint)
            }
        }

        binding.recyclerviewWe.layoutManager = LinearLayoutManager(this)
        binding.recyclerviewThem.layoutManager = LinearLayoutManager(this)
        binding.recyclerviewWe.adapter = adapterWe
        binding.recyclerviewThem.adapter = adapterThem
    }

    private fun setupListeners() {
        binding.btnPlus1We.setOnClickListener { viewModel.incrementWe(1) }
        binding.btnPlus3We.setOnClickListener { viewModel.incrementWe(3) }
        binding.btnPlus1Them.setOnClickListener { viewModel.incrementThem(1) }
        binding.btnPlus3Them.setOnClickListener { viewModel.incrementThem(3) }

        binding.btnReset.setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle(getString(R.string.reset_dialog_title))
                .setMessage(getString(R.string.reset_dialog_message))
                .setPositiveButton(getString(R.string.yes)) { _, _ ->
                    viewModel.reset()
                }
                .setNegativeButton(getString(R.string.no), null)
                .show()
        }
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            viewModel.weScore.collect { score ->
                adapterWe.updateScore(score)
            }
        }
        lifecycleScope.launch {
            viewModel.themScore.collect { score ->
                adapterThem.updateScore(score)
            }
        }
    }
}
