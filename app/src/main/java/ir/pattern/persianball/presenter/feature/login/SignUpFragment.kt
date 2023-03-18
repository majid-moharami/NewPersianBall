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
import ir.pattern.persianball.data.model.Resource
import ir.pattern.persianball.data.model.SignUp
import ir.pattern.persianball.databinding.FragmentSignUpBinding
import ir.pattern.persianball.error.ErrorDTO
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SignUpFragment : Fragment() {

    lateinit var binding: FragmentSignUpBinding
    private val viewModel: RegisterViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(activity),
            R.layout.fragment_sign_up,
            container,
            false
        )

        binding.signupBtn.setOnClickListener {
            val signUp = SignUp(
                userName = binding.userNameEditText.text.toString(),
                password = binding.passwordEditText.text.toString()
            ).apply {
                phoneNumber = binding.phoneNumberEditText.text.toString()
            }
            lifecycleScope.launch {
                viewModel.signUpUser(signUp)
            }
        }

        lifecycleScope.launch {
            viewModel.signUpState.collect {
                when (it) {
                    is Resource.Success -> {
                        val action =
                            SignUpFragmentDirections.actionSignUpFragmentToVerityUserFragment(
                                binding.phoneNumberEditText.text.toString(),
                                null,
                                binding.userNameEditText.text.toString(),
                                binding.passwordEditText.text.toString())
                        findNavController().navigate(action)
                    }
                    is Resource.Failure -> {
                        val massage : String = when(it.error.code){
                            ErrorDTO.INVALID_MOBILE -> {
                                resources.getString(R.string.invalid_phone_number)
                            }
                            ErrorDTO.MOBILE_EXIST -> {
                                resources.getString(R.string.mobile_exist)
                            }
                            else -> {
                                it.error.toString()
                            }
                        }

                        Toast.makeText(activity, massage, Toast.LENGTH_LONG).show()
                    }
                    else -> {

                    }
                }
            }
        }

        return binding.root
    }

    private fun validatePhoneNumber(showError : Boolean){

    }


}