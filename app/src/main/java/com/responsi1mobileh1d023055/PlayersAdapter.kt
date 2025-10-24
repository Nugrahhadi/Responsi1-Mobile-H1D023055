package com.responsi1mobileh1d023055

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.responsi1mobileh1d023055.data.model.Player
import com.responsi1mobileh1d023055.databinding.ItemPlayerBinding

class PlayersAdapter(
    private val players: List<Player>,
    private val onPlayerClick: (Player) -> Unit
) : RecyclerView.Adapter<PlayersAdapter.PlayerViewHolder>() {

    inner class PlayerViewHolder(private val binding: ItemPlayerBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(player: Player) {
            binding.tvPlayerName.text = player.name
            binding.tvPlayerNationality.text = player.nationality ?: "N/A"
            val color = when {
                player.position?.contains("Goalkeeper", ignoreCase = true) == true ->
                    ContextCompat.getColor(binding.root.context, R.color.goalkeeper_color) // Kuning

                player.position?.contains("Defence", ignoreCase = true) == true ||
                player.position?.contains("Defender", ignoreCase = true) == true ||
                player.position?.contains("Back", ignoreCase = true) == true ->
                    ContextCompat.getColor(binding.root.context, R.color.defender_color) // Biru

                player.position?.contains("Midfield", ignoreCase = true) == true ||
                player.position?.contains("Midfielder", ignoreCase = true) == true ->
                    ContextCompat.getColor(binding.root.context, R.color.midfielder_color) // Hijau

                else ->
                    ContextCompat.getColor(binding.root.context, R.color.forward_color) // Merah
            }
            binding.cardContainer.setBackgroundColor(color)

            binding.root.setOnClickListener {
                onPlayerClick(player)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayerViewHolder {
        val binding = ItemPlayerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PlayerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PlayerViewHolder, position: Int) {
        holder.bind(players[position])
    }

    override fun getItemCount(): Int = players.size
}
