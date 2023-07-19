package ir.pattern.persianball.presenter.feature.login

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
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
import ir.pattern.persianball.data.model.VerifyUser
import ir.pattern.persianball.databinding.FragmentOtpForgetPasswordBinding
import ir.pattern.persianball.data.model.ErrorDTO
import kotlinx.coroutines.launch

@AndroidEntryPoint
class OtpForgetPasswordFragment : Fragment() {

    lateinit var binding: FragmentOtpForgetPasswordBinding
    private val viewModel: RegisterViewModel by viewModels()
    private val args: OtpForgetPasswordFragmentArgs by navArgs()
    var code = ""
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
            code = it
        }

        binding.submitBtn.setOnClickListener {
            lifecycleScope.launch {
                viewModel.verifyUser(verifyUser = VerifyUser(phoneNumber = args.phone, code = code))
            }
        }

        binding.instagram.setOnClickListener {
            val uri = Uri.parse("http://Instagram.com/ahmadrezafstv")
            val likeIng = Intent(Intent.ACTION_VIEW, uri)

            likeIng.setPackage("com.instagram.android")

            try {
                startActivity(likeIng)
            } catch (e: ActivityNotFoundException) {
                startActivity(
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("http://Instagram.com/ahmadrezafstv")
                    )
                )
            }
        }

        binding.youtube.setOnClickListener {
            var intent: Intent
            try {
                intent = Intent(Intent.ACTION_VIEW)
                intent.setPackage("com.google.android.youtube")
                intent.data = Uri.parse("https://youtube.com/@AhmadrezaElNino")
                startActivity(intent)
            } catch (e: ActivityNotFoundException) {
                intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse("https://youtube.com/@AhmadrezaElNino")
                startActivity(intent)
            }
        }

        binding.whatsapp.setOnClickListener {
            val url = "https://api.whatsapp.com/send?phone=09367007740"
            val i = Intent(Intent.ACTION_VIEW)
            i.data = Uri.parse(url)
            startActivity(i)
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