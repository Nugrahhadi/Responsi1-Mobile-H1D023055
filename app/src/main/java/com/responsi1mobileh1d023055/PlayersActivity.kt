package com.responsi1mobileh1d023055

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.responsi1mobileh1d023055.data.api.RetrofitClient
import com.responsi1mobileh1d023055.data.model.Player
import com.responsi1mobileh1d023055.databinding.ActivityPlayersBinding
import com.responsi1mobileh1d023055.databinding.DialogPlayerDetailBinding
import kotlinx.coroutines.launch

class PlayersActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPlayersBinding
    private val teamId = 18 // Borussia Mönchengladbach ID

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlayersBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = getString(R.string.players_list)

        binding.btnBack.setOnClickListener {
            finish()
        }

        setupRecyclerView()
        loadPlayersData()
    }

    private fun setupRecyclerView() {
        binding.rvPlayers.layoutManager = LinearLayoutManager(this)
    }

    private fun loadPlayersData() {
        binding.progressBar.visibility = View.VISIBLE

        lifecycleScope.launch {
            try {
                val response = RetrofitClient.apiService.getTeam(teamId)
                if (response.isSuccessful) {
                    val team = response.body()
                    team?.squad?.let { players ->
                        displayPlayers(players)
                    } ?: run {
                        binding.tvError.visibility = View.VISIBLE
                        binding.tvError.text = "Players data not available"
                    }
                } else {
                    showError("Error: ${response.code()} - ${response.message()}")
                }
            } catch (e: Exception) {
                e.printStackTrace()
                showError("Error: ${e.message}")
            } finally {
                binding.progressBar.visibility = View.GONE
            }
        }
    }

    private fun displayPlayers(players: List<Player>) {
        // Urutkan pemain: Goalkeeper -> Defence -> Midfield -> Offence
        val sortedPlayers = players.sortedBy { player ->
            when {
                player.position?.contains("Goalkeeper", ignoreCase = true) == true -> 1
                player.position?.contains("Defence", ignoreCase = true) == true -> 2
                player.position?.contains("Midfield", ignoreCase = true) == true -> 3
                player.position?.contains("Offence", ignoreCase = true) == true -> 4
                else -> 5
            }
        }

        val adapter = PlayersAdapter(sortedPlayers) { player ->
            showPlayerDetailDialog(player)
        }
        binding.rvPlayers.adapter = adapter
    }

    private fun showPlayerDetailDialog(player: Player) {
        val dialogBinding = DialogPlayerDetailBinding.inflate(LayoutInflater.from(this))

        // Set data ke dialog
        dialogBinding.tvDialogPlayerName.text = player.name
        dialogBinding.tvDialogPosition.text = "Position: ${player.position ?: "N/A"}"
        dialogBinding.tvDialogNationality.text = "Nationality: ${player.nationality ?: "N/A"}"
        dialogBinding.tvDialogDob.text = "Date of Birth: ${player.dateOfBirth ?: "N/A"}"

        // Gunakan BottomSheetDialog agar muncul dari bawah
        val dialog = com.google.android.material.bottomsheet.BottomSheetDialog(this)
        dialog.setContentView(dialogBinding.root)

        dialogBinding.btnClose.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }

    private fun showError(message: String) {
        binding.tvError.visibility = View.VISIBLE
        binding.tvError.text = message
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
