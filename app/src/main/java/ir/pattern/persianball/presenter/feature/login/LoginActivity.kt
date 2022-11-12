package ir.pattern.persianball.presenter.feature.login

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.findNavController
import dagger.hilt.android.AndroidEntryPoint
import ir.pattern.persianball.R
import ir.pattern.persianball.data.model.SignUp
import ir.pattern.persianball.data.repository.LoginRepository
import ir.pattern.persianball.databinding.ActivityLoginBinding
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private lateinit var binding: ActivityLoginBinding
    var isLogin : Boolean = false
    @Inject
    lateinit var repo: LoginRepository

    override fun onStart() {
        super.onStart()
        //AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        navController = findNavController(R.id.login_nav_host_fragment)
        isLogin = intent.getBooleanExtra("IS_LOGIN", false)
        if (isLogin){

        }else navController.navigate(R.id.signUpFragment)
    }

    override fun onCreateView(name: String, context: Context, attrs: AttributeSet): View? {
        return super.onCreateView(name, context, attrs)
    }

}