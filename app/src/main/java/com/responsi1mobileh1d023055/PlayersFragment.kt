package com.responsi1mobileh1d023055

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.responsi1mobileh1d023055.data.model.Player
import com.responsi1mobileh1d023055.databinding.DialogPlayerDetailBinding
import com.responsi1mobileh1d023055.databinding.FragmentPlayersBinding

class PlayersFragment : Fragment() {
    private var _binding: FragmentPlayersBinding? = null
    private val binding get() = _binding!!
    private lateinit var position: String
    private var playersList: List<Player> = emptyList()

    companion object {
        private const val ARG_POSITION = "position"

        fun newInstance(position: String): PlayersFragment {
            val fragment = PlayersFragment()
            val args = Bundle()
            args.putString(ARG_POSITION, position)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            position = it.getString(ARG_POSITION, "")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPlayersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        // Filter pemain berdasarkan posisi (tidak digunakan lagi dalam implementasi baru)
        val filteredPlayers = playersList.filter { player ->
            player.position?.contains(position, ignoreCase = true) == true
        }

        binding.rvPlayers.layoutManager = LinearLayoutManager(requireContext())
        binding.rvPlayers.adapter = PlayersAdapter(filteredPlayers) { player ->
            showPlayerDetailDialog(player)
        }
    }

    private fun showPlayerDetailDialog(player: Player) {
        val dialogBinding = DialogPlayerDetailBinding.inflate(LayoutInflater.from(requireContext()))

        dialogBinding.tvDialogPlayerName.text = player.name
        dialogBinding.tvDialogPosition.text = "Position: ${player.position ?: "N/A"}"
        dialogBinding.tvDialogNationality.text = "Nationality: ${player.nationality ?: "N/A"}"
        dialogBinding.tvDialogDob.text = "Date of Birth: ${player.dateOfBirth ?: "N/A"}"

        val dialog = com.google.android.material.bottomsheet.BottomSheetDialog(requireContext())
        dialog.setContentView(dialogBinding.root)

        dialogBinding.btnClose.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
