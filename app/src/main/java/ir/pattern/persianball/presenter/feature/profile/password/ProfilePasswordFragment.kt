package ir.pattern.persianball.presenter.feature.profile.password

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
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProfilePasswordFragment : Fragment() {

    lateinit var binding: FragmentProfilePasswordBinding
    private val viewModel: PasswordViewModel by viewModels()

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
            viewModel.changePassState.collect{
                when(it){
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
                    else -> {}
                }
            }
        }
        binding.submitBtn.setOnClickListener {
            viewLifecycleOwner.lifecycleScope.launch {
                viewModel.changePassword(
                    ChangePasswordDto(
                        binding.lastPasswordEditText.text.toString(),
                        binding.newPasswordEditText.text.toString()
                    )
                )
            }
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