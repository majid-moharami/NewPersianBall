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
import ir.pattern.persianball.data.model.ChangePassword
import ir.pattern.persianball.data.model.Resource
import ir.pattern.persianball.databinding.FragmentChangePasswordBinding
import ir.pattern.persianball.manager.AccountManager
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class ChangePasswordFragment : Fragment() {

    lateinit var binding: FragmentChangePasswordBinding
    private val viewModel: RegisterViewModel by viewModels()
    private val args: ChangePasswordFragmentArgs by navArgs()
    @Inject
    lateinit var accountManager: AccountManager
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(activity),
            R.layout.fragment_change_password,
            container,
            false
        )

        binding.getCodeBtn.setOnClickListener {
            lifecycleScope.launch {
                viewModel.changePassword(
                    ChangePassword(
                        phoneNumber = args.phone,
                        password = binding.passwordEditText.text.toString()
                    )
                )
            }
        }

        lifecycleScope.launch {
            viewModel.changePasswordState.collect {
                when (it) {
                    is Resource.Success -> {
                        accountManager.updatePassword(binding.passwordEditText.text.toString())
                        activity?.finish()
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