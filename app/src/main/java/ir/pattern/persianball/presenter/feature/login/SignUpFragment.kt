package ir.pattern.persianball.presenter.feature.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TableLayout
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.tabs.TabLayout
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
            if (binding.emailLayout.isVisible && (binding.emailEditText.text.isNullOrBlank() || binding.userNameEditText.text.isNullOrBlank() || binding.passwordEditText.text.isNullOrBlank())){
                Toast.makeText(requireActivity(), "لطفا همه موارد را وارد کنید.", Toast.LENGTH_SHORT).show()
            }else if (binding.phoneNumberLayout.isVisible && (binding.phoneNumberEditText.text.isNullOrBlank() || binding.userNameEditText.text.isNullOrBlank() || binding.passwordEditText.text.isNullOrBlank())){
                Toast.makeText(requireActivity(), "لطفا همه موارد را وارد کنید.", Toast.LENGTH_SHORT).show()
            }else{
                if (binding.emailLayout.isVisible){
                    val signUp = SignUp(
                        userName = binding.userNameEditText.text.toString(),
                        password = binding.passwordEditText.text.toString()
                    ).apply {
                        email = binding.emailEditText.text.toString()
                    }
                    lifecycleScope.launch {
                        viewModel.signUpUser(signUp)
                    }
                }else{
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
            }
        }

        lifecycleScope.launch {
            viewModel.signUpState.collect {
                when (it) {
                    is Resource.Success -> {
                        val action =
                            SignUpFragmentDirections.actionSignUpFragmentToVerityUserFragment(
                                binding.phoneNumberEditText.text.toString(),
                                binding.emailEditText.text.toString(),
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
                            ErrorDTO.EMAIL_EXIST -> {
                                resources.getString(R.string.email_exist)
                            }
                            ErrorDTO.INVALID_EMAIL -> {
                                resources.getString(R.string.invalid_email)
                            }
                            else -> {
                                resources.getString(R.string.error)
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tabLayout.addOnTabSelectedListener( object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when(tab?.position){
                    0 -> {
                        binding.phoneNumberLayout.isVisible = true
                        binding.emailLayout.isVisible = false
                    }
                    1 -> {
                        binding.phoneNumberLayout.isVisible = false
                        binding.emailLayout.isVisible = true
                    }
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

        })
    }

    private fun validatePhoneNumber(showError : Boolean){

    }


}