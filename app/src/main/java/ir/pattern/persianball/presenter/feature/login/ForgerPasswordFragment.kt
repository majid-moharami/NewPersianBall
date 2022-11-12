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
import ir.pattern.persianball.data.model.ForgetPassword
import ir.pattern.persianball.data.model.Resource
import ir.pattern.persianball.data.model.User
import ir.pattern.persianball.databinding.FragmentForgetPasswordBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ForgerPasswordFragment : Fragment() {

    lateinit var binding: FragmentForgetPasswordBinding
    private val viewModel: RegisterViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(activity),
            R.layout.fragment_forget_password,
            container,
            false
        )

        binding.getCodeBtn.setOnClickListener {
            lifecycleScope.launch {
                viewModel.forgetPassword(ForgetPassword(phoneNumber = binding.phoneNumberEditText.text.toString()))
            }
        }

        lifecycleScope.launch {
            viewModel.forgetPasswordState.collect {
                when (it) {
                    is Resource.Success -> {
                        val action =
                            ForgerPasswordFragmentDirections.actionForgetPasswordFragmentToOtpForgetPasswordFragment2(
                                null,
                                binding.phoneNumberEditText.text.toString()
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