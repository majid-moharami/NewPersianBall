package ir.pattern.persianball.presenter.feature.player

import android.content.Context
import android.os.Handler
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.exoplayer2.ui.TimeBar
import ir.pattern.persianball.databinding.PlayerControllerBinding
import ir.pattern.persianball.utils.UiUtils
import java.util.*
import kotlin.math.floor
import kotlin.math.max

class MovieController(
    private val iMovieVideoController: IMovieVideoController,
    val controllerBinding: PlayerControllerBinding,
    val context: Context
) : VideoController(), Seekable {

    private var progressHandler: Handler? = null
    private var progressRunnable: Runnable? = null
    private val timeBarCallback = object : TimeBar.OnScrubListener {
        override fun onScrubStart(timeBar: TimeBar, position: Long) {
        }

        override fun onScrubMove(timeBar: TimeBar, position: Long) {
//            controllerBinding.exoPosition.text = convertTimeToString(position.toInt())
            progressRunnable?.let { progressHandler?.removeCallbacksAndMessages(null) }
        }

        override fun onScrubStop(timeBar: TimeBar, position: Long, canceled: Boolean) {
            setSeekbar()
        }
    }
    val englishToPersian: Map<Char, Char> =
        mapOf('0' to '۰', '1' to '۱', '2' to '۲', '3' to '۳', '4' to '۴', '5' to '۵', '6' to '۶',
            '7' to '۷', '8' to '۸', '9' to '۹')

    init {
        //activityComponent().inject(this@MovieVideoController)
        initView()
        setListeners()
        setSeekbar()
    }

    private fun initView() {
        controllerBinding.exoProgress.addListener(timeBarCallback)
//        val rightParams = controllerBinding.guidelineRight.layoutParams as ConstraintLayout.LayoutParams
//        val leftParams = controllerBinding.guidelineLeft.layoutParams as ConstraintLayout.LayoutParams
//        if (languageHelper.isLanguageRtl()) {
//            rightParams.guidePercent = 0.25f
//            leftParams.guidePercent = 0.75f
//        } else if (languageHelper.isLanguageLtr()) {
//            rightParams.guidePercent = 0.75f
//            leftParams.guidePercent = 0.25f
//        }
//        controllerBinding.halfPrice.compoundDrawablePadding =
//            context.resources.getDimensionPixelSize(R.dimen.margin_default_v2)
//        (controllerBinding.exoProgress.layoutParams as (ConstraintLayout.LayoutParams)).apply {
//            bottomMargin = context.resources.getDimensionPixelOffset(
//                R.dimen.margin_default_v2_triple) + context.resources.getDimensionPixelOffset(
//                R.dimen.margin_default_v2_half)
//        }
    }

    private fun setListeners() {
        controllerBinding.exoPlay.setOnClickListener {
            handlePlayButtonClick()
        }
        controllerBinding.exoPause.setOnClickListener {
            handlePlayButtonClick()
        }
        controllerBinding.imgFwd.setOnClickListener {
            iMovieVideoController.seekForward()
        }
        controllerBinding.imgBwd.setOnClickListener {
            iMovieVideoController.seekBackward()
        }
    }

    override fun showController(show: Boolean) {
        controllerBinding.controllerLayout.visibility = if (show) View.VISIBLE else View.GONE
    }

    override fun onDestroyView() {
        controllerBinding.exoProgress.removeListener(timeBarCallback)
        progressRunnable?.let { progressHandler?.removeCallbacksAndMessages(null) }
    }

    override fun showPlayButton(show: Boolean) {
        controllerBinding.exoPlay.visibility = if (show) View.VISIBLE else View.GONE
        controllerBinding.exoPause.visibility = if (show) View.VISIBLE else View.GONE
    }

    override fun playButtonState(isPlaying: Boolean) {
        when (isPlaying) {
            true -> {
                controllerBinding.exoPlay.visibility = View.GONE
                controllerBinding.exoPause.visibility = View.VISIBLE
            }
            false -> {
                controllerBinding.exoPlay.visibility = View.VISIBLE
                controllerBinding.exoPause.visibility = View.GONE
            }
        }
    }

    override fun setSeekbar() {
        progressHandler = Handler()
        progressRunnable = object : Runnable {
            override fun run() {
                refreshTimes()
                progressHandler?.postDelayed(this, 500)
            }
        }
        progressRunnable?.let { progressHandler?.post(it) }
        //Make sure you update Seekbar on UI thread
    }

    override fun showSeekbar(show: Boolean) {
        controllerBinding.exoProgress.visibility = if (show) View.VISIBLE else View.GONE
    }

    private fun refreshTimes() {
//        controllerBinding.exoPosition.text =
//            convertTimeToString(iMovieVideoController.getPlayerPosition().toInt())
        controllerBinding.exoDuration.text =
            convertTimeToString(max(iMovieVideoController.getVideoDuration() - iMovieVideoController.getPlayerPosition(),
                0).toInt())
        videoControllerListener?.runnableCheck()
    }

    override fun showTaskbar(show: Boolean) {

    }

    private fun convertTimeToString(timeMs: Int): String {
        val totalSeconds = timeMs / 1000
        val seconds = totalSeconds % 60
        val minutes = (totalSeconds / 60) % 60
        val hours = totalSeconds / 3600
        val stringBuilder = StringBuilder()
        val formatter = Formatter(stringBuilder, Locale.getDefault())
        stringBuilder.setLength(0)
        return if (hours > 0) {
            convertToPersianNumber(formatter.format("%d:%02d:%02d", hours, minutes, seconds).toString())
        } else {
            convertToPersianNumber(formatter.format("%02d:%02d", minutes, seconds).toString())
        }
    }

    private fun convertToPersianNumber(str: String?): String {
        if (str.isNullOrBlank() || str.equals("null", true)) {
            return ""
        }

        val charArray = str.toCharArray()
//        charArray.forEachIndexed { index, item ->
//            if (englishToPersian.containsKey(item)) {
//                charArray[index] = englishToPersian[item] ?: item
//            }
//        }
        return String(charArray)
    }
}

interface IMovieVideoController {
    fun seekForward()
    fun seekBackward()
    fun getPlayerPosition(): Long
    fun getVideoDuration(): Long
}