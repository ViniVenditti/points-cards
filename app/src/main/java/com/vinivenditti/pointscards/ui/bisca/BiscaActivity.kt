package com.vinivenditti.pointscards.ui.bisca

import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.vinivenditti.pointscards.R
import com.vinivenditti.pointscards.databinding.ActivityBiscaBinding
import com.vinivenditti.pointscards.ui.start.StartViewModelSingleton

class BiscaActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBiscaBinding
    private val viewModel: BiscaViewModel by lazy {
        val biscaViewModelFactory = BiscaViewModelFactory(StartViewModelSingleton.getInstance(this))
        ViewModelProvider(this, biscaViewModelFactory)[BiscaViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityBiscaBinding.inflate(layoutInflater)
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        setSupportActionBar(binding.toolbar)

        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                AlertDialog.Builder(this@BiscaActivity)
                    .setTitle("Sair do jogo")
                    .setMessage("Deseja sair do jogo?")
                    .setPositiveButton("Sim") { _, _ ->
                        StartViewModelSingleton.getInstance(this@BiscaActivity)
                        finish()
                    }
                    .setNegativeButton("Não", null)
                    .show()
            }
        })

        supportActionBar?.hide()
        setContentView(binding.root)
        setUpNavigation()
    }

    private fun setUpNavigation() {
        val navView: BottomNavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        val appBarConfiguration =
            AppBarConfiguration(setOf(R.id.navigation_points, R.id.navigation_score))

        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }
}