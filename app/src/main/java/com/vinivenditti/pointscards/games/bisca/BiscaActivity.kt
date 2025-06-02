package com.vinivenditti.pointscards.games.bisca

import android.os.Bundle
import android.provider.Settings
import android.view.WindowManager
import androidx.activity.OnBackPressedCallback
import androidx.activity.SystemBarStyle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
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
import com.vinivenditti.pointscards.start.StartViewModelSingleton

class BiscaActivity : AppCompatActivity() {

    private val binding: ActivityBiscaBinding by lazy { ActivityBiscaBinding.inflate(layoutInflater) }
    private val viewModel: BiscaViewModel by lazy {
        val biscaViewModelFactory = BiscaViewModelFactory(StartViewModelSingleton.getInstance(this))
        ViewModelProvider(this, biscaViewModelFactory)[BiscaViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val colorId = ContextCompat.getColor(this, R.color.md_theme_primary)
        enableEdgeToEdge(statusBarStyle = SystemBarStyle.light(colorId, colorId),
            navigationBarStyle = SystemBarStyle.light(colorId,colorId))

        viewModel.setPhoneId(Settings.Secure.getString(contentResolver, Settings.Secure.ANDROID_ID))

        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                AlertDialog.Builder(this@BiscaActivity)
                    .setTitle("Sair do jogo")
                    .setMessage("Deseja sair do jogo?")
                    .setPositiveButton("Sim") { _, _ ->
                        finish()
                    }
                    .setNegativeButton("Não", null)
                    .show()
            }
        })
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        supportActionBar?.hide()
        setContentView(binding.root)
        setUpNavigation()
    }

    private fun setUpNavigation() {
        val navView: BottomNavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        val appBarConfiguration =
            AppBarConfiguration(setOf(R.id.navigation_points, R.id.navigation_score, R.id.navigation_historical))

        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }
}