package ir.pattern.persianball.presenter.feature.player

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import android.view.animation.AnimationSet
import android.view.animation.DecelerateInterpolator
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.ui.StyledPlayerView
import ir.pattern.persianball.R
import ir.pattern.persianball.databinding.FragmentPlayerBinding
import ir.pattern.persianball.databinding.PlayerControllerBinding


class PlayerFragment : Fragment(), IMovieVideoController, VideoControllerListener {

    lateinit var binding: FragmentPlayerBinding
    lateinit var player: ExoPlayer

    private var controller: MovieController? = null
    private var movieVideoController: MovieController
        set(value) {
            controller = value
        }
        get() = controller!!

    private var iMovieVideoController: IMovieVideoController
        set(value) {
            iMovieController = value
        }
        get() = iMovieController!!
    private var iMovieController: IMovieVideoController? = null

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
        iMovieVideoController = this
        DataBindingUtil.bind<PlayerControllerBinding>(binding.root.findViewById(R.id.controller_layout))
            ?.let {
                movieVideoController =
                    MovieController(iMovieVideoController, it, requireContext())
                movieVideoController.videoControllerListener = this@PlayerFragment
            }
        binding.playerView.controllerAutoShow = false
        binding.playerView.player = player
        binding.controller.player = player
        val mediaItem =
            MediaItem.fromUri("https://media.geeksforgeeks.org/wp-content/uploads/20201217163353/Screenrecorder-2020-12-17-16-32-03-350.mp4")
        player.addMediaItem(mediaItem)
        player.prepare()
        movieVideoController.showController(true)
        setListener()
        player.playWhenReady = true
        return binding.root
    }

    private fun setListener() {
        binding.dummyView.setOnClickListener {
            showController(!binding.controller.isShown)
        }

        player.addListener(object : Player.Listener {
            override fun onIsPlayingChanged(isPlaying: Boolean) {
                setPlayerControllerTimeout(isPlaying)
            }
        })
        binding.playerView.setOnClickListener {
            showController(!binding.controller.isShown)
        }
    }

    private fun setPlayerControllerTimeout(isPlaying: Boolean) {
        binding.controller.showTimeoutMs = if (isPlaying) CONTROLLER_SHOW_TIMEOUT_MS else -1
        binding.playerView.controllerShowTimeoutMs =
            if (isPlaying) CONTROLLER_SHOW_TIMEOUT_MS else -1
    }

    private fun showController(show: Boolean) {
        binding.playerView.useController = false
        setPlayerControllerTimeout(player.isPlaying)
        val alphaAnimation = if (show) {
            binding.controller.show()
            AlphaAnimation(0f, 1f)
        } else {
            binding.controller.hide()
            AlphaAnimation(1f, 0f)
        }.apply {
            interpolator = DecelerateInterpolator()
            duration = 500
        }
        val animation = AnimationSet(false)
        animation.addAnimation(alphaAnimation)
        binding.controller.animation = animation
    }

    override fun seekForward() {
        player.currentPosition.plus(MOVE_TIME).let {
            if (it < player.duration)
                player.seekTo(it)
            else
                player.seekTo(player.duration)
        }
    }

    override fun seekBackward() {
        player.currentPosition.minus(MOVE_TIME).let {
            if (it > 0)
                player.seekTo(it)
            else
                player.seekTo(0L)
        }
    }

    override fun getPlayerPosition(): Long = player.currentPosition

    override fun getVideoDuration(): Long = player.duration

    companion object {
        private const val MOVE_TIME = 10000
        private const val CONTROLLER_SHOW_TIMEOUT_MS = 6000
    }

    override fun play(play: Boolean) {
        player.playWhenReady = play
        player.playbackState
    }

    override fun changeOrientation() {

    }

    override fun runnableCheck() {

    }

}