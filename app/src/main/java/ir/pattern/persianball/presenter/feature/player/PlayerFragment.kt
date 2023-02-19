package ir.pattern.persianball.presenter.feature.player

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import android.view.animation.AnimationSet
import android.view.animation.DecelerateInterpolator
import android.widget.PopupMenu
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.Format
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.source.hls.HlsMediaSource
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.trackselection.TrackSelectionOverrides
import com.google.android.exoplayer2.ui.DefaultTrackNameProvider
import com.google.android.exoplayer2.ui.StyledPlayerView
import com.google.android.exoplayer2.ui.TrackNameProvider
import com.google.android.exoplayer2.upstream.DefaultHttpDataSource
import dagger.hilt.android.AndroidEntryPoint
import ir.pattern.persianball.R
import ir.pattern.persianball.databinding.FragmentPlayerBinding
import ir.pattern.persianball.databinding.PlayerControllerBinding
import ir.pattern.persianball.presenter.feature.BaseFragment
import ir.pattern.persianball.utils.UiUtils
import javax.inject.Inject

@AndroidEntryPoint
class PlayerFragment : Fragment(), IMovieVideoController, VideoControllerListener, Player.Listener {

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
    private var trackSelector:DefaultTrackSelector?=null
    var qualityList = ArrayList<Pair<String, TrackSelectionOverrides.Builder>>()
    private var qualityPopUp: PopupMenu?=null
    private lateinit var trackNameProvider: TrackNameProvider

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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
        initController()
        initPlayer()
        setListener()
        return binding.root
    }

    private fun initController() {
        iMovieVideoController = this
        DataBindingUtil.bind<PlayerControllerBinding>(binding.root.findViewById(R.id.controller_layout))
            ?.let {
                movieVideoController =
                    MovieController(iMovieVideoController, it, requireContext())
                movieVideoController.videoControllerListener = this@PlayerFragment
            }
    }

    private fun initPlayer() {
        trackSelector = DefaultTrackSelector(requireContext(), AdaptiveTrackSelection.Factory())
        player = ExoPlayer.Builder(requireContext()).setTrackSelector(trackSelector!!).build()
        binding.playerView.controllerAutoShow = false
        binding.playerView.player = player
        binding.controller.player = player
        val mediaItem =
            MediaItem.fromUri("https://stream9.myket.ir/hls/v2/movie/gapfilm/ODM2AR0XBP/ApSxbqTyvSyR5c98YEsg_w/master.m3u8?md5=3HUM3QrWXEJ8Si1ifpHRDw&expires=1677253822&isp=rightel")
        val defaultHttpDataSourceFactory = DefaultHttpDataSource.Factory()
        val mediaSource =
            HlsMediaSource.Factory(defaultHttpDataSourceFactory).createMediaSource(mediaItem)
        player.addMediaSource(mediaSource)
        player.addListener(this)
        player.prepare()
        movieVideoController.showController(true)
        player.playWhenReady = true
        trackNameProvider = DefaultTrackNameProvider(requireContext().resources)
    }

    override fun onPlaybackStateChanged(playbackState: Int) {
        if (playbackState==Player.STATE_READY){
            trackSelector?.generateQualityList()?.let {
                qualityList = it
                setUpQualityList()
            }
        }
    }


    private fun setUpQualityList() {
        qualityPopUp = PopupMenu(requireContext(), controller?.controllerBinding?.hdIcon, Gravity.RIGHT)
        qualityList.let {
            for ((i, videoQuality) in it.withIndex()) {
                qualityPopUp?.menu?.add(0, i, 0, videoQuality.first)
            }
        }
        qualityPopUp?.setOnMenuItemClickListener { menuItem ->
            qualityList[menuItem.itemId].let {
                trackSelector?.apply {
                    parameters = parameters.buildUpon()
                        .setTrackSelectionOverrides(it.second.build())
                        .setTunnelingEnabled(true)
                        .build()
                }
            }
            true
        }
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
        controller?.controllerBinding?.hdIcon?.setOnClickListener {
            qualityPopUp?.show()
        }

        controller?.controllerBinding?.crossIm?.setOnClickListener {
            activity?.finish()
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

    override fun play(play: Boolean) {
        player.playWhenReady = play
        player.playbackState
    }

    override fun changeOrientation() {

    }

    override fun runnableCheck() {

    }

    override fun onDestroy() {
        player.release()
        super.onDestroy()
    }


    companion object {
        private const val MOVE_TIME = 10000
        private const val CONTROLLER_SHOW_TIMEOUT_MS = 6000
    }

}