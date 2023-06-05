package ir.pattern.persianball.presenter

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Rect
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.AttributeSet
import android.util.DisplayMetrics
import android.view.View
import android.widget.FrameLayout
import android.widget.PopupMenu
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.bumptech.glide.Glide
import com.google.android.material.badge.BadgeDrawable
import com.google.firebase.FirebaseApp
import com.google.firebase.messaging.FirebaseMessaging
import dagger.hilt.android.AndroidEntryPoint
import ir.pattern.persianball.R
import ir.pattern.persianball.data.model.DeviceDto
import ir.pattern.persianball.data.repository.DashboardRepository
import ir.pattern.persianball.databinding.ActivityMainBinding
import ir.pattern.persianball.manager.AccountManager
import ir.pattern.persianball.presenter.feature.login.LoginActivity
import ir.pattern.persianball.presenter.feature.message.PushMessageActivity
import ir.pattern.persianball.presenter.feature.profile.ProfileFragment
import ir.pattern.persianball.presenter.feature.shopping.ShoppingCartsActivity
import ir.pattern.persianball.utils.SharedPreferenceUtils
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
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
        const val GALLERY_SELECT_BACK_IMAGE_REQUEST_CODE = 3000
        const val LOGIN_REQUEST_CODE = 3
    }

    private lateinit var navController: NavController
    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainActivityViewModel by viewModels()

    private val _changeAvatar = MutableSharedFlow<Boolean>()
    val changeAvatar = _changeAvatar.asSharedFlow()

    @Inject
    lateinit var accountManager: AccountManager

    @Inject
    lateinit var dashboardRepository: DashboardRepository
    lateinit var sharedPreferenceUtils: SharedPreferenceUtils

    override fun onResume() {
        super.onResume()
        lifecycleScope.launch {
            viewModel.getShoppingCart()
        }
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
        sharedPreferenceUtils = SharedPreferenceUtils(application)
        navController = findNavController(R.id.my_nav_host_fragment)
        setupNavigation()
        setupSmoothBottomMenu()
        showToolbar(accountManager.isLogin)
        lifecycleScope.launch {
            viewModel.shopBadge.collect {
                setShopBadge(it)
            }
        }

        lifecycleScope.launch {
            viewModel.notificationBadge.collect {
                var diffrence = 0
                val list = sharedPreferenceUtils.getMessagesId()
                if (list.isEmpty()) {
                    diffrence = it.size
                } else {
                    it.map { id ->
                        var isDiffrent = true
                        for (i in list) {
                            if (id == i) {
                                isDiffrent = false
                            }
                        }
                        if (isDiffrent) {
                            diffrence += 1
                        }
                    }
                }
                setNotificationBadge(diffrence)
            }
        }

        lifecycleScope.launch {
            viewModel.avatar.collect {
                if (!it.isNullOrEmpty()) {
                    Glide.with(this@MainActivity)
                        .load("https://api.persianball.ir/media/$it")
                        .centerCrop()
                        .into(binding.profileImage)
                    sharedPreferenceUtils.updateProfileImage(it)
                } else {
                    binding.profileImage.setImageDrawable(resources.getDrawable(R.drawable.ic_upload))
                }
            }
        }

        lifecycleScope.launch {
            viewModel.isLogin.collect {
                showToolbar(it)
                if (!it) {
                    sharedPreferenceUtils.clearCredentials()
                }
            }
        }

        lifecycleScope.launch {
            changeAvatar.collect {
                if (sharedPreferenceUtils.getUserCredentials().profileImageUrl.isNotEmpty()) {
                    if ("https://api.persianball.ir/media/" == viewModel.sharedPreferenceUtils.getUserCredentials().profileImageUrl) {
                        binding.profileImage.setImageDrawable(resources.getDrawable(R.drawable.ic_upload))
                    } else {
                        Glide.with(this@MainActivity)
                            .load(viewModel.sharedPreferenceUtils.getUserCredentials().profileImageUrl)
                            .centerCrop()
                            .into(binding.profileImage)
                    }
                } else {
                    binding.profileImage.setImageDrawable(resources.getDrawable(R.drawable.ic_upload))
                }
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
        lifecycleScope.launch {
            dashboardRepository.getDashboard().collect{}
        }
        //setupActionBarWithNavController(navController)
        navController.addOnDestinationChangedListener { _, destination, _ ->

            when (destination.id) {
                R.id.homeFragment, R.id.academyFragment, R.id.dashboardFragment -> {
                    lifecycleScope.launch {
                        viewModel.getShoppingCart()
                        viewModel.getMessage()
                    }
                    binding.toolbar.visibility = View.VISIBLE
                    binding.bottomBar.visibility = View.VISIBLE
                    lifecycleScope.launch {
                        _changeAvatar.emit(true)
                    }
                }

                R.id.storeFragment -> {
                    binding.toolbar.visibility = View.GONE
                    binding.bottomBar.visibility = View.VISIBLE
                }

                R.id.profileFragment -> {
                    binding.toolbar.visibility = View.GONE
                }

                else -> {
                    binding.toolbar.visibility = View.GONE
                    binding.bottomBar.visibility = View.GONE
                }
            }
        }
    }

    @SuppressLint("ResourceType")
    private fun showToolbar(isLogin: Boolean) {
        binding.toolbar.isVisible = true
        if (isLogin) {
            binding.loginLayout.visibility = View.GONE
            binding.welcomeLayout.visibility = View.VISIBLE
            lifecycleScope.launch {
                if (viewModel.getProfileImage()
                        .isEmpty() || viewModel.getProfileImage() == "https://api.persianball.ir/media/"
                ) {
                    viewModel.getUser()
                } else {
                    Glide.with(this@MainActivity)
                        .load(viewModel.getProfileImage())
                        .centerCrop()
                        .into(binding.profileImage)
                }
            }
        } else {
            binding.loginLayout.visibility = View.VISIBLE
            binding.welcomeLayout.visibility = View.GONE
            binding.signup.setOnClickListener {
                val intent = Intent(this, LoginActivity::class.java)
                intent.putExtra("IS_LOGIN", false)
                startActivityForResult(intent, LOGIN_REQUEST_CODE)
            }
            binding.login.setOnClickListener {
                val intent = Intent(this, LoginActivity::class.java)
                intent.putExtra("IS_LOGIN", true)
                startActivityForResult(intent, LOGIN_REQUEST_CODE)
            }
        }

        binding.shopIcon.setOnClickListener {
            val intent = Intent(this, ShoppingCartsActivity::class.java)
            startActivityForResult(intent, LOGIN_REQUEST_CODE)
        }

        binding.notificationIcon.setOnClickListener {
            val intent = Intent(this, PushMessageActivity::class.java)
            startActivity(intent)
        }

        binding.profileImage.setOnClickListener {

        }
    }

    @SuppressLint("UnsafeOptInUsageError")
    private fun setNotificationBadge(count: Int) {
        val badgeDrawable = BadgeDrawable.create(this@MainActivity)
        badgeDrawable.number = count
        badgeDrawable.backgroundColor = resources.getColor(R.color.white)
        badgeDrawable.badgeTextColor = resources.getColor(R.color.notification_icon_color)
        badgeDrawable.badgeGravity = BadgeDrawable.TOP_START
        badgeDrawable.setBoundsFor(
            binding.notificationBadge,
            binding.frame
        )
        binding.frame.foreground =
            badgeDrawable
    }

    private fun setShopBadge(count: Int) {
        val badgeDrawable = BadgeDrawable.create(this@MainActivity)
        badgeDrawable.number = count
        badgeDrawable.backgroundColor = resources.getColor(R.color.white)
        badgeDrawable.badgeTextColor = resources.getColor(R.color.notification_icon_color)
        badgeDrawable.badgeGravity = BadgeDrawable.TOP_START
        badgeDrawable.setBoundsFor(
            binding.shopBadge,
            binding.frameShop
        )
        binding.frameShop.foreground =
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

        if (requestCode == LOGIN_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                showToolbar(accountManager.isLogin)
                FirebaseApp.initializeApp(this.applicationContext)
                FirebaseMessaging.getInstance().token.addOnCompleteListener { it ->
                    if (it.isSuccessful) {
                        lifecycleScope.launch {
                            if (accountManager.isLogin) {
                                FirebaseApp.initializeApp(this@MainActivity.applicationContext)
                                FirebaseMessaging.getInstance().token.addOnCompleteListener { itt ->
                                    if (itt.isSuccessful) {
                                        lifecycleScope.launch {
                                            viewModel.sendDevice(getDeviceDto(itt.result))
                                        }
                                    }
                                }

                            }
                        }
                        return@addOnCompleteListener
                    }
                }
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

    private fun getDeviceDto(fcmToken: String): DeviceDto {
        val packageInfo = packageManager.getPackageInfo(packageName, 0)
        val versionName = packageInfo.versionName
        val versionCode =
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.P) {
                packageInfo.longVersionCode
            } else {
                packageInfo.versionCode.toLong()
            }
        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        val height = displayMetrics.heightPixels
        val width = displayMetrics.widthPixels
        return DeviceDto(
            Build.VERSION.SDK_INT,
            Build.VERSION.RELEASE,
            "$width * $height",
            Build.MANUFACTURER,
            Build.MODEL,
            versionName,
            versionCode.toString(),
            Settings.Secure.getString(this.contentResolver, Settings.Secure.ANDROID_ID),
            fcmToken,
            "mobile"
        )
    }
}