package ir.pattern.persianball.presenter.feature.profile.address

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import ir.pattern.persianball.R
import ir.pattern.persianball.data.model.profile.Address
import ir.pattern.persianball.data.model.profile.InfoType
import ir.pattern.persianball.data.model.profile.ItemInfoDto
import ir.pattern.persianball.databinding.FragmentAddressBinding
import ir.pattern.persianball.databinding.FragmentPersonalInformationBinding
import ir.pattern.persianball.presenter.adapter.BasePagingAdapter
import ir.pattern.persianball.presenter.adapter.BaseViewHolder
import ir.pattern.persianball.presenter.feature.profile.EditInfoDialogFragment
import ir.pattern.persianball.presenter.feature.profile.personalInformation.PersonalInfoDataAdapter
import ir.pattern.persianball.presenter.feature.profile.personalInformation.PersonalInfoViewModel
import ir.pattern.persianball.presenter.feature.profile.personalInformation.PersonalInformationFragment
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AddressFragment : Fragment() {

    lateinit var binding: FragmentAddressBinding
    private val viewModel: AddressViewModel by viewModels()
    var pagingAdapter: BasePagingAdapter? = null
    lateinit var address: Address

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(activity),
            R.layout.fragment_address,
            container,
            false
        )

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.addressInfo.collectLatest {
                if (it != null) {
                    address = it
                }
                it?.also { address ->
                    binding.addressContent.text = address.address
                    binding.phoneNumberContent.text = address.mobilePhone
                    binding.homeNumberContent.text = address.homePhone
                    binding.emailContent.text = address.email
                    binding.postalCodeContent.text = address.postalCode.toString()
                }
            }
        }

        binding.addressPersianBallImageButton.setOnClickListener {
            val dialog = EditInfoDialogFragment.newInstance(address, InfoType.ADDRESS)
            dialog.show(requireActivity().supportFragmentManager, "edit_info_dialog")
        }
        binding.homeNumberPersianBallImageButton.setOnClickListener {
            val dialog = EditInfoDialogFragment.newInstance(address, InfoType.HOME_PHONE)
            dialog.show(requireActivity().supportFragmentManager, "edit_info_dialog")
        }
        binding.phoneNumberPersianBallImageButton.setOnClickListener {
            val dialog = EditInfoDialogFragment.newInstance(address, InfoType.PHONE_NUMBER)
            dialog.show(requireActivity().supportFragmentManager, "edit_info_dialog")
        }
        binding.emailPersianBallImageButton.setOnClickListener {
            val dialog = EditInfoDialogFragment.newInstance(address, InfoType.EMAIL)
            dialog.show(requireActivity().supportFragmentManager, "edit_info_dialog")
        }
        binding.postalCodePersianBallImageButton.setOnClickListener {
            val dialog = EditInfoDialogFragment.newInstance(address, InfoType.POSTAL_CODE)
            dialog.show(requireActivity().supportFragmentManager, "edit_info_dialog")
        }
        binding.submit.setOnClickListener {
            viewLifecycleOwner.lifecycleScope.launch {
                viewModel.createAddress(address)
            }
        }
        return binding.root
    }

    interface OnSubmitClickListener {
        fun onSubmitClicked(info: ItemInfoDto)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment AddressFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance() =
            AddressFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}