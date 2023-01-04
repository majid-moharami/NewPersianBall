package ir.pattern.persianball.presenter.feature.player

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.ui.StyledPlayerView
import ir.pattern.persianball.R
import ir.pattern.persianball.databinding.FragmentPlayerBinding


class PlayerFragment : Fragment() {

    lateinit var binding: FragmentPlayerBinding
    lateinit var player: ExoPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        player = ExoPlayer.Builder(requireContext()).build()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(activity),
            R.layout.fragment_player,
            container,
            false
        )
        binding.playerView.controllerAutoShow = false
        binding.playerView.player = player
        val mediaItem = MediaItem.fromUri("https://media.geeksforgeeks.org/wp-content/uploads/20201217163353/Screenrecorder-2020-12-17-16-32-03-350.mp4")
        player.addMediaItem(mediaItem)
        player.prepare()

        player.playWhenReady = true
        return binding.root
    }


}