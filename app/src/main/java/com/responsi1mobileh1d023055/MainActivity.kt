package com.responsi1mobileh1d023055

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.responsi1mobileh1d023055.data.api.RetrofitClient
import com.responsi1mobileh1d023055.data.model.ApiResponse
import com.responsi1mobileh1d023055.databinding.ActivityMainBinding
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var teamData: ApiResponse? = null
    private val teamId = 18

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setupButtons()
        loadTeamData()
    }

    private fun setupButtons() {
        binding.btnHistory.setOnClickListener {
            val intent = Intent(this, HistoryActivity::class.java)
            startActivity(intent)
        }

        binding.btnCoach.setOnClickListener {
            val intent = Intent(this, CoachActivity::class.java)
            startActivity(intent)
        }

        binding.btnPlayers.setOnClickListener {
            val intent = Intent(this, PlayersActivity::class.java)
            startActivity(intent)
        }
    }

    private fun loadTeamData() {
        binding.progressBar.visibility = View.VISIBLE

        lifecycleScope.launch {
            try {
                val response = RetrofitClient.apiService.getTeam(teamId)
                if (response.isSuccessful) {
                    teamData = response.body()
                    teamData?.let { updateUI(it) }
                } else {
                    showError()
                }
            } catch (e: Exception) {
                e.printStackTrace()
                showError()
            } finally {
                binding.progressBar.visibility = View.GONE
            }
        }
    }

    private fun updateUI(team: ApiResponse) {
        binding.tvFounded.text = getString(R.string.founded, team.founded)
        binding.tvVenue.text = getString(R.string.venue, team.venue)
        binding.tvAddress.text = getString(R.string.address, team.address)
    }

    private fun showError() {
        Toast.makeText(this, R.string.error_load_data, Toast.LENGTH_LONG).show()
    }
}