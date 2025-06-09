package com.vinivenditti.pointscards.start

import android.content.Intent
import android.graphics.Canvas
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.view.WindowManager
import android.widget.AdapterView
import android.widget.Toast
import androidx.activity.SystemBarStyle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.vinivenditti.pointscards.R
import com.vinivenditti.pointscards.databinding.ActivityStartBinding
import com.vinivenditti.pointscards.games.bisca.BiscaActivity
import com.vinivenditti.pointscards.games.cacheta.CachetaActivity
import com.vinivenditti.pointscards.games.tranca.TrancaActivity
import com.vinivenditti.pointscards.games.truco.TrucoActivity
import com.vinivenditti.pointscards.repository.FirebaseRepository
import com.vinivenditti.pointscards.start.adapter.PlayerAdapter
import com.vinivenditti.pointscards.start.listener.PlayerListener
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator


class StartActivity : AppCompatActivity() {
    private val binding: ActivityStartBinding by lazy { ActivityStartBinding.inflate(layoutInflater) }
    private val adapter: PlayerAdapter by lazy { PlayerAdapter() }
    private lateinit var startViewModel: StartViewModel
    private val phoneId: String by lazy { Settings.Secure.getString(contentResolver, Settings.Secure.ANDROID_ID) }
    private val database = FirebaseRepository()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val colorId = ContextCompat.getColor(this, R.color.md_theme_primary)
        enableEdgeToEdge(statusBarStyle = SystemBarStyle.light(colorId, colorId),
            navigationBarStyle = SystemBarStyle.light(colorId,colorId))
        database.loginUser()

        supportActionBar?.hide()
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        setContentView(binding.root)

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

        val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
                return false
            }
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                listener.deletePlayer(adapter.list[viewHolder.adapterPosition])
            }
            override fun onChildDraw(c: Canvas, recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, dX: Float, dY: Float, actionState: Int, isCurrentlyActive: Boolean) {
                RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                    .addBackgroundColor(
                        ContextCompat.getColor(
                            this@StartActivity,
                            R.color.md_theme_error
                        )
                    )
                    .addCornerRadius(1, 15)
                    .addActionIcon(R.drawable.ic_delete)
                    .create()
                    .decorate()
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
            }
        }
        val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
        itemTouchHelper.attachToRecyclerView(binding.recyclerViewPlayers)
        adapter.attachListener(listener)
        setListeners()
        observe()
    }

    private fun verifyGame() {
        database.verifyGame(phoneId) {
            if(it.second.listPlayers.isNotEmpty()){
                AlertDialog.Builder(this@StartActivity)
                    .setTitle("Jogo em andamento")
                    .setMessage("Existe um jogo não finalizado com os jogadores" +
                            " ${it.second.listPlayers.joinToString { player -> player.name }} " +
                            "\nDeseja continuar?"
                    )
                    .setPositiveButton("Sim") { _, _ ->
                            startViewModel.setContinueGame(it) {
                            startActivity(Intent(this@StartActivity, BiscaActivity::class.java))
                        }
                    }
                    .setNegativeButton("Não") { _,_ ->
                        startViewModel.clearContinueGame(it.second.match)
                    }
                    .show()
            }
        }
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
                    1 -> verifyGame()
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

    override fun onResume() {
        super.onResume()
        binding.spinnerGame.setSelection(0)
         //startViewModel.clearContinueGame()
    }

}
