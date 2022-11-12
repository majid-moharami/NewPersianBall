package ir.pattern.persianball.presenter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Rect
import android.os.Build
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.PopupMenu
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.google.android.material.badge.BadgeDrawable
import com.google.android.material.badge.BadgeUtils
import dagger.hilt.android.AndroidEntryPoint
import ir.pattern.persianball.R
import ir.pattern.persianball.databinding.ActivityMainBinding
import ir.pattern.persianball.manager.AccountManager
import ir.pattern.persianball.presenter.feature.login.LoginActivity
import ir.pattern.persianball.views.PersianBallImageButton
import ir.pattern.persianball.views.PersianBallTextView
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private lateinit var binding: ActivityMainBinding

    @Inject
    lateinit var accountManager: AccountManager

    override fun onResume() {
        super.onResume()

        if (accountManager.isLogin) {
            binding.toolbar.findViewById<LinearLayout>(R.id.login_layout).visibility = View.GONE
            binding.toolbar.findViewById<LinearLayout>(R.id.welcome_layout).visibility = View.VISIBLE
        } else {
            binding.toolbar.findViewById<LinearLayout>(R.id.login_layout).visibility = View.VISIBLE
            binding.toolbar.findViewById<LinearLayout>(R.id.welcome_layout).visibility = View.GONE
            binding.toolbar.findViewById<PersianBallTextView>(R.id.signup).setOnClickListener {
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
            }
        }
    }

    override fun onStart() {
        super.onStart()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        //this.supportActionBar?.hide()
        setContentView(binding.root)
        navController = findNavController(R.id.my_nav_host_fragment)
        setupNavigation()
        setupSmoothBottomMenu()
        setBadge()

        if (accountManager.isLogin) {
            binding.toolbar.findViewById<LinearLayout>(R.id.login_layout).visibility = View.GONE
            binding.toolbar.findViewById<LinearLayout>(R.id.welcome_layout).visibility = View.VISIBLE
        } else {
            binding.toolbar.findViewById<LinearLayout>(R.id.login_layout).visibility = View.VISIBLE
            binding.toolbar.findViewById<LinearLayout>(R.id.welcome_layout).visibility = View.GONE
            binding.toolbar.findViewById<PersianBallTextView>(R.id.signup).setOnClickListener {
                val intent = Intent(this, LoginActivity::class.java)
                intent.putExtra("IS_LOGIN", false)
                startActivity(intent)
            }
            binding.toolbar.findViewById<PersianBallTextView>(R.id.login).setOnClickListener{
                val intent = Intent(this, LoginActivity::class.java)
                intent.putExtra("IS_LOGIN", true)
                startActivity(intent)
            }
        }

    }

    override fun onCreateView(name: String, context: Context, attrs: AttributeSet): View? {
        return super.onCreateView(name, context, attrs)
    }

    private fun setupSmoothBottomMenu() {
        val popupMenu = PopupMenu(this, null)
        popupMenu.inflate(R.menu.bottom_navigation_menu)
        val menu = popupMenu.menu
        binding.bottomBar.setupWithNavController(menu, navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    private fun setupNavigation() {
        //setupActionBarWithNavController(navController)
        navController.addOnDestinationChangedListener { _, destination, _ ->
//            when (destination.id) {
//                R.id.homeFragment, R.id.academyFragment,
//                R.id.storeFragment, R.id.settingFragment -> binding.toolbar.visibility =
//                    View.VISIBLE
//                else -> binding.toolbar.visibility = View.GONE
//            }
        }
    }

    @SuppressLint("UnsafeOptInUsageError")
    private fun setBadge() {
        val badgeDrawable = BadgeDrawable.create(this@MainActivity)
        badgeDrawable.number = 1
        badgeDrawable.backgroundColor = resources.getColor(R.color.white)
        badgeDrawable.badgeTextColor = resources.getColor(R.color.notification_icon_color)
        badgeDrawable.badgeGravity = BadgeDrawable.BOTTOM_END
        badgeDrawable.setBoundsFor(
            binding.toolbar.findViewById(R.id.notification_badge),
            binding.toolbar.findViewById(R.id.frame)
        )
        binding.toolbar.findViewById<FrameLayout>(R.id.notification_badge).foreground =
            badgeDrawable
    }

    private fun BadgeDrawable.setBoundsFor(anchor: View, parent: FrameLayout) {
        val rect = Rect()
        parent.getDrawingRect(rect)
        this.bounds = rect
        this.updateBadgeCoordinates(anchor, parent)
    }
}