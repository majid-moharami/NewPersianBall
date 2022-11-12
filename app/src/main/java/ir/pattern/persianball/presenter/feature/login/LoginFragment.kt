package ir.pattern.persianball.presenter.feature.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import ir.pattern.persianball.R
import ir.pattern.persianball.data.model.Login
import ir.pattern.persianball.data.model.Resource
import ir.pattern.persianball.data.model.User
import ir.pattern.persianball.databinding.FragmentLoginBinding
import ir.pattern.persianball.utils.SharedPreferenceUtils
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class LoginFragment : Fragment(R.layout.fragment_login) {
    lateinit var binding: FragmentLoginBinding
    private val viewModel: RegisterViewModel by viewModels()

    @Inject
    lateinit var sharedPreferenceUtils: SharedPreferenceUtils
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(activity),
            R.layout.fragment_login,
            container,
            false
        )
        binding.loginBtn.setOnClickListener {
            lifecycleScope.launch {
                val username = binding.userNameEditText.text.toString()
                val password = binding.passwordEditText.text.toString()
                viewModel.login(Login(username, password))
            }
        }

        lifecycleScope.launch {
            viewModel.loginState.collect {
                when (it) {
                    is Resource.Success -> {
                        it.data?.also { tokenDto ->
                            sharedPreferenceUtils.putUserCredentials(
                                User(
                                    binding.userNameEditText.text.toString(),
                                    binding.passwordEditText.text.toString(),
                                    tokenDto.access, tokenDto.refresh)
                            )
                            activity?.finish()
                        }
                    }
                    is Resource.Failure -> {
                        Toast.makeText(activity, it.error.toString(), Toast.LENGTH_LONG).show()
                    }
                    else -> {

                    }
                }
            }
        }

        binding.registerTxt.setOnClickListener {
            val action = LoginFragmentDirections.actionLoginFragmentToSignUpFragment()
            findNavController().navigate(action)
        }
        binding.forgetPasswordTxt.setOnClickListener {
            val action = LoginFragmentDirections.actionLoginFragmentToForgerPasswordFragment()
            findNavController().navigate(action)
        }

        return binding.root
    }
}