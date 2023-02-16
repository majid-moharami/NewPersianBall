package ir.pattern.persianball.presenter.feature.player

import com.google.android.exoplayer2.Player
import ir.pattern.persianball.di.OtherComponent

abstract class VideoController {

    var videoControllerListener: VideoControllerListener? = null
    var isPlaying = true

    abstract fun showController(show: Boolean)
    abstract fun onDestroyView()
    abstract fun showPlayButton(show: Boolean)
    abstract fun playButtonState(isPlaying: Boolean)

    open fun handlePlayerState(playWhenReady: Boolean, playbackState: Int) {
        when (playbackState) {
            Player.STATE_READY -> {
                showController(true)
                showPlayButton(true)
            }
            else -> {
                showPlayButton(false)
            }
        }
    }

   // fun activityComponent() : OtherComponent = ApplicationLauncher.getInstance().appComponent()

    fun handlePlayButtonClick() {
        isPlaying = !isPlaying
        videoControllerListener?.play(isPlaying)
        playButtonState(isPlaying)
    }
}

interface Seekable {
    fun setSeekbar()
    fun showSeekbar(show: Boolean)
    fun showTaskbar(show: Boolean)
}

interface Rotatable {
    fun showFullscreenButton(show: Boolean)
}

interface VideoControllerListener {
    fun play(play: Boolean)
    fun changeOrientation()
    fun runnableCheck()
}