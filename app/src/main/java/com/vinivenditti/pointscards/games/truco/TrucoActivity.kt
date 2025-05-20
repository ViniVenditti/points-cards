package com.vinivenditti.pointscards.games.truco

import android.os.Bundle
import android.view.WindowManager
import androidx.activity.OnBackPressedCallback
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.vinivenditti.pointscards.R
import com.vinivenditti.pointscards.databinding.ActivityTrucoBinding
import com.vinivenditti.pointscards.games.truco.adapter.TrucoAdapter

class TrucoActivity : AppCompatActivity() {

    private val binding: ActivityTrucoBinding by lazy { ActivityTrucoBinding.inflate(layoutInflater) }
    private var adapter: TrucoAdapter = TrucoAdapter(12, R.drawable.container_truco_we_points)

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
        binding.recyclerviewWe.adapter = adapter
        adapter = TrucoAdapter(12, R.drawable.container_truco_them_points)
        binding.recyclerviewThem.adapter = adapter

        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                AlertDialog.Builder(this@TrucoActivity)
                    .setTitle("Sair do jogo")
                    .setMessage("Deseja sair do jogo?")
                    .setPositiveButton("Sim") { _, _ ->
                        finish()
                    }
                    .setNegativeButton("Não", null)
                    .show()
            }
        })
    }

}