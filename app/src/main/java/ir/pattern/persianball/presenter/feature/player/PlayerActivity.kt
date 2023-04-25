package ir.pattern.persianball.presenter.feature.player

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.content.res.ResourcesCompat
import dagger.hilt.android.AndroidEntryPoint
import ir.pattern.persianball.R
import javax.inject.Inject

@AndroidEntryPoint
class PlayerActivity : AppCompatActivity() {

    @Inject
    lateinit var playerRepository: PlayerRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player)

        val haveUrl = intent.getBooleanExtra("HAVE_URL", false)
        if (haveUrl){
            playerRepository.videoUrl = null
            playerRepository.videoUrl = intent.getStringExtra("URL")
        }
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        applyFullscreenConfiguration()
    }

    private fun applyFullscreenConfiguration() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            window?.decorView?.systemUiVisibility = (
                    View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            or View.SYSTEM_UI_FLAG_FULLSCREEN
                            or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)
        }
    }
}