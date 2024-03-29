package ir.pattern.persianball.presenter.feature.profile.password

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import ir.pattern.persianball.R
import ir.pattern.persianball.data.model.Resource
import ir.pattern.persianball.data.model.profile.ChangePasswordDto
import ir.pattern.persianball.databinding.FragmentProfilePasswordBinding
import ir.pattern.persianball.presenter.feature.login.LoginActivity
import ir.pattern.persianball.presenter.feature.login.PersianBallPrivacyActivity
import ir.pattern.persianball.presenter.feature.profile.ProfileViewModel
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProfilePasswordFragment : Fragment() {

    lateinit var binding: FragmentProfilePasswordBinding
    private val viewModel: PasswordViewModel by viewModels()
    private val profileViewModel: ProfileViewModel by viewModels({ requireParentFragment() })

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(activity),
            R.layout.fragment_profile_password,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.changePassState.collect {
                when (it) {
                    is Resource.Loading -> {
                        Toast.makeText(
                            requireActivity(),
                            "در حال ذخیره اطلاعات",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    is Resource.Success -> {
                        Toast.makeText(
                            requireActivity(),
                            "اطلاعات با موفقیت ذخیره شد",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    is Resource.Failure -> {
                        if (it.error.code == "wrong_password") {
                            Toast.makeText(
                                requireActivity(),
                                "رمز عبور پیشین اشتباه هست.",
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            Toast.makeText(
                                requireActivity(),
                                "مشکلی در عملیات به وجود امده است.",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            }
        }
        binding.submitBtn.setOnClickListener {
            viewLifecycleOwner.lifecycleScope.launch {
                if (binding.newPasswordEditText.text.toString() == binding.repeatPasswordEditText.text.toString()) {
                    viewModel.changePassword(
                        ChangePasswordDto(
                            binding.lastPasswordEditText.text.toString(),
                            binding.newPasswordEditText.text.toString()
                        )
                    )
                } else {
                    Toast.makeText(
                        requireActivity(),
                        "رمزعبور جدید و تکرار آن را یکسان وارد کنید.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }

        binding.logoutBtn.setOnClickListener {
            profileViewModel.logoutUser()
        }

        binding.appPolicy.setOnClickListener{
            startActivity(Intent(requireActivity(), PersianBallPrivacyActivity::class.java))
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ProfilePasswordFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance() =
            ProfilePasswordFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}