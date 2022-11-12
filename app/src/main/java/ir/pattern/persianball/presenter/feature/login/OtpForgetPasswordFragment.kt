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
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import ir.pattern.persianball.R
import ir.pattern.persianball.data.model.Resource
import ir.pattern.persianball.data.model.RetryCode
import ir.pattern.persianball.data.model.User
import ir.pattern.persianball.data.model.VerifyUser
import ir.pattern.persianball.databinding.FragmentForgetPasswordBinding
import ir.pattern.persianball.databinding.FragmentOtpForgetPasswordBinding
import kotlinx.coroutines.launch

@AndroidEntryPoint
class OtpForgetPasswordFragment : Fragment() {

    lateinit var binding: FragmentOtpForgetPasswordBinding
    private val viewModel: RegisterViewModel by viewModels()
    private val args: OtpForgetPasswordFragmentArgs by navArgs()
    var code = 0
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(activity),
            R.layout.fragment_otp_forget_password,
            container,
            false
        )

        binding.otpView.setOnFinishListener {
            code = it.toInt()
        }

        binding.submitBtn.setOnClickListener {
            lifecycleScope.launch {
                viewModel.verifyUser(verifyUser = VerifyUser(phoneNumber = args.phone, code = code))
            }
        }

        binding.retryCode.setOnClickListener {
            lifecycleScope.launch {
                viewModel.retryCode(RetryCode(phoneNumber = args.phone))
            }
        }

        lifecycleScope.launch {
            viewModel.verifyState.collect {
                when (it) {
                    is Resource.Success -> {
                        val action =
                            OtpForgetPasswordFragmentDirections.actionOtpForgetPasswordFragmentToChangePasswordFragment(
                                args.email,
                                args.phone
                            )
                        findNavController().navigate(action)
                    }
                    is Resource.Failure -> {
                        Toast.makeText(activity, it.error.toString(), Toast.LENGTH_LONG).show()
                    }
                    else -> {

                    }
                }
            }
        }
        return binding.root
    }


}