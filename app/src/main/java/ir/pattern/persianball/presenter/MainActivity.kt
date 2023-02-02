package ir.pattern.persianball.presenter

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Rect
import android.net.Uri
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import android.widget.PopupMenu
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.google.android.material.badge.BadgeDrawable
import dagger.hilt.android.AndroidEntryPoint
import ir.pattern.persianball.R
import ir.pattern.persianball.databinding.ActivityMainBinding
import ir.pattern.persianball.manager.AccountManager
import ir.pattern.persianball.presenter.feature.login.LoginActivity
import ir.pattern.persianball.presenter.feature.profile.ProfileFragment
import ir.pattern.persianball.presenter.feature.shopping.ShoppingCartsActivity
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    companion object {
        const val CAPTURE_IMAGE_REQUEST_CODE = 1000
        const val GALLERY_SELECT_IMAGE_REQUEST_CODE = 2000
    }

    private lateinit var navController: NavController
    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainActivityViewModel by viewModels()

    @Inject
    lateinit var accountManager: AccountManager

    override fun onResume() {
        super.onResume()
//        lifecycleScope.launch {
//            viewModel.refreshToken()
//        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        //this.supportActionBar?.hide()
        lifecycleScope.launch {
            viewModel.refreshToken()
        }
        setContentView(binding.root)
        navController = findNavController(R.id.my_nav_host_fragment)
        setupNavigation()
        setupSmoothBottomMenu()
        setBadge()
    }

    override fun onCreateView(name: String, context: Context, attrs: AttributeSet): View? {
        lifecycleScope.launch {
            viewModel.isLogin.collect {
                showToolbar(it)
//                binding.frameLayout.visibility = View.GONE
//                binding.loading.isIndeterminate = false
//                binding.loading.visibility = View.GONE
            }
        }
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
            when (destination.id) {
                R.id.homeFragment, R.id.academyFragment,
                R.id.storeFragment, R.id.dashboardFragment -> {
                    binding.toolbar.visibility = View.VISIBLE
                    binding.bottomBar. visibility = View.VISIBLE
                }
                R.id.profileFragment -> {
                    binding.toolbar.visibility = View.GONE
                }
                else -> {
                    binding.toolbar.visibility = View.GONE
                    binding.bottomBar. visibility = View.GONE
                }
            }
        }
    }

    private fun showToolbar(isLogin: Boolean){
        if (isLogin) {
            binding.loginLayout.visibility = View.GONE
            binding.welcomeLayout.visibility =
                View.VISIBLE
        } else {
            binding.loginLayout.visibility = View.VISIBLE
            binding.welcomeLayout.visibility = View.GONE
            binding.signup.setOnClickListener {
                val intent = Intent(this, LoginActivity::class.java)
                intent.putExtra("IS_LOGIN", false)
                startActivity(intent)
            }
            binding.login.setOnClickListener {
                val intent = Intent(this, LoginActivity::class.java)
                intent.putExtra("IS_LOGIN", true)
                startActivity(intent)
            }
        }

        binding.bagIcon.setOnClickListener {
            val intent = Intent(this, ShoppingCartsActivity::class.java)
            startActivity(intent)
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
            binding.notificationBadge,
            binding.frame
        )
        binding.notificationBadge.foreground =
            badgeDrawable
    }

    private fun BadgeDrawable.setBoundsFor(anchor: View, parent: FrameLayout) {
        val rect = Rect()
        parent.getDrawingRect(rect)
        this.bounds = rect
        this.updateBadgeCoordinates(anchor, parent)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CAPTURE_IMAGE_REQUEST_CODE || requestCode == GALLERY_SELECT_IMAGE_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                handleImageUri(data)
            }
        }
    }

    private fun handleImageUri(data: Intent?) {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.my_nav_host_fragment) as NavHostFragment
        val currentFragment = navHostFragment.childFragmentManager.fragments[0]
        if (currentFragment is ProfileFragment) {
            data?.let {
                val pickedImage: Uri = it.data!!
                val imageFile = File(pickedImage.path)
                val filePart: MultipartBody.Part = MultipartBody.Part.createFormData(
                    "avatar",
                    imageFile.name,
                    imageFile.asRequestBody("image/*".toMediaTypeOrNull())
                )
                currentFragment.onImageResult(filePart)
            }
        }
    }
}