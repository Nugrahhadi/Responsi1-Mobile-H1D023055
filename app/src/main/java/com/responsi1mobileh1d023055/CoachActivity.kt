package com.responsi1mobileh1d023055

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.responsi1mobileh1d023055.data.api.RetrofitClient
import com.responsi1mobileh1d023055.databinding.ActivityCoachBinding
import kotlinx.coroutines.launch

class CoachActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCoachBinding
    private val teamId = 18 // Borussia Mönchengladbach ID

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCoachBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = getString(R.string.head_coach)

        binding.btnBack.setOnClickListener {
            finish()
        }

        loadCoachData()
    }

    private fun loadCoachData() {
        binding.progressBar.visibility = View.VISIBLE

        lifecycleScope.launch {
            try {
                val response = RetrofitClient.apiService.getTeam(teamId)
                if (response.isSuccessful) {
                    val team = response.body()
                    team?.coach?.let { coach ->
                        displayCoachData(coach)
                    } ?: run {
                        binding.tvError.visibility = View.VISIBLE
                        binding.tvError.text = "Coach data not available"
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

    private fun displayCoachData(coach: com.responsi1mobileh1d023055.data.model.Coach) {
        binding.tvCoachName.text = coach.name ?: "N/A"

        val dob = coach.dateOfBirth ?: "N/A"
        binding.tvCoachDob.text = getString(R.string.date_of_birth, dob)

        val nationality = coach.nationality ?: "N/A"
        binding.tvCoachNationality.text = getString(R.string.nationality, nationality)

        if (coach.contract != null) {
            val contractText = getString(
                R.string.contract,
                coach.contract.start ?: "N/A",
                coach.contract.until ?: "N/A"
            )
            binding.tvCoachContract.text = contractText
        } else {
            binding.tvCoachContract.text = "Contract: N/A"
        }
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
