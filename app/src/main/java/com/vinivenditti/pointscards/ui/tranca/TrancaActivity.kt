package com.vinivenditti.pointscards.ui.tranca

import android.os.Bundle
import android.view.WindowManager
import androidx.activity.OnBackPressedCallback
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.vinivenditti.pointscards.R
import com.vinivenditti.pointscards.databinding.ActivityTrancaBinding
import com.vinivenditti.pointscards.ui.tranca.adapter.TrancaPointsAdapter
import com.vinivenditti.pointscards.ui.truco.TrucoActivity

class TrancaActivity : AppCompatActivity() {

    private val binding: ActivityTrancaBinding by lazy { ActivityTrancaBinding.inflate(layoutInflater) }
    private val viewModel: TrancaViewModel by viewModels()
    private val adapterWe: TrancaPointsAdapter by lazy { TrancaPointsAdapter() }
    private val adapterThem: TrancaPointsAdapter by lazy { TrancaPointsAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

        binding.recyclerviewWe.layoutManager = LinearLayoutManager(this)
        binding.recyclerviewThem.layoutManager = LinearLayoutManager(this)
        binding.recyclerviewWe.adapter = adapterWe
        binding.recyclerviewThem.adapter = adapterThem

        setListeners()
        observe()
    }

    private fun observe() {
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                AlertDialog.Builder(this@TrancaActivity)
                    .setTitle("Sair do jogo")
                    .setMessage("Deseja sair do jogo?")
                    .setPositiveButton("Sim") { _, _ ->
                        finish()
                    }
                    .setNegativeButton("Não", null)
                    .show()
            }
        })

        viewModel.wePoints.observe(this) {
            adapterWe.updateList(it)
        }
        viewModel.themPoints.observe(this) {
            adapterThem.updateList(it)
        }
    }

    private fun setListeners() {
        binding.buttonCalculate.setOnClickListener {
            val we = binding.editWePoints.text.toString().toIntOrNull() ?: 0
            val them = binding.editThemPoints.text.toString().toIntOrNull() ?: 0
            viewModel.addPoints(we, them)
            binding.textWeTotal.text = "Total: " + viewModel.sumWePoints().toString()
            binding.textThemTotal.text = "Total: " + viewModel.sumThemPoints().toString()
            binding.editWePoints.text?.clear()
            binding.editThemPoints.text?.clear()
        }
    }
}