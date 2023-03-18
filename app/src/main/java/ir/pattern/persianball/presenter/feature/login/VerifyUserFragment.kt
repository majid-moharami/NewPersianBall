package ir.pattern.persianball.presenter.feature.login

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
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
import ir.pattern.persianball.databinding.FragmentOtpForgetPasswordBinding
import ir.pattern.persianball.databinding.FragmentOtpRegisterBinding
import ir.pattern.persianball.error.ErrorDTO
import ir.pattern.persianball.manager.AccountManager
import ir.pattern.persianball.utils.SharedPreferenceUtils
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class VerifyUserFragment : Fragment() {

    lateinit var binding: FragmentOtpRegisterBinding
    private val viewModel: RegisterViewModel by viewModels({ requireParentFragment() })
    private val args: VerifyUserFragmentArgs by navArgs()
    var code = 0

    @Inject
    lateinit var sharedPreferenceUtils: SharedPreferenceUtils

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(activity),
            R.layout.fragment_otp_register,
            container,
            false
        )

        binding.submitBtn.setOnClickListener {
            lifecycleScope.launch {
                viewModel.verifyUser(verifyUser = VerifyUser(phoneNumber = args.phone, code = code))
            }
        }

        binding.otpView.setOnFinishListener {
            code = it.toInt()
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
                        it.data?.also { tokenDto ->
                            sharedPreferenceUtils.putUserCredentials(
                                User(
                                    args.username,
                                    args.password,
                                    tokenDto.access,
                                    tokenDto.refresh
                                ,"")
                            )
                            val resultIntent = Intent()
                            activity?.setResult(AppCompatActivity.RESULT_OK, resultIntent)
                            activity?.finish()
                        }
                    }
                    is Resource.Failure -> {
                        val massage = when(it.error.code){
                            ErrorDTO.INVALID_CODE -> {
                                resources.getString(R.string.invalid_code)
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
}