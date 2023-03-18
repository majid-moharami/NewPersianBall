package ir.pattern.persianball.presenter.feature.profile.address

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import dagger.hilt.android.AndroidEntryPoint
import ir.pattern.persianball.R
import ir.pattern.persianball.data.model.Resource
import ir.pattern.persianball.data.model.address.OrderAddress
import ir.pattern.persianball.data.model.profile.Address
import ir.pattern.persianball.data.model.profile.InfoType
import ir.pattern.persianball.data.model.profile.ItemInfoDto
import ir.pattern.persianball.databinding.FragmentAddressBinding
import ir.pattern.persianball.presenter.adapter.BasePagingAdapter
import ir.pattern.persianball.presenter.feature.profile.EditInfoViewModel
import ir.pattern.persianball.presenter.feature.shopping.address.add.AddAddressActivity
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AddressFragment : Fragment() {

    lateinit var binding: FragmentAddressBinding
    private val viewModel: ProfileAddressViewModel by viewModels({ requireParentFragment() })
    private val dialogViewModel: EditInfoViewModel by viewModels()
    var pagingAdapter: BasePagingAdapter? = null
    var address: Address? = null

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
                    viewModel.address = it
                }
                it?.also { oAddress ->
                    address = oAddress
                    binding.addressContent.text = oAddress.address
                    binding.phoneNumberContent.text = oAddress.mobilePhone
                    binding.homeNumberContent.text = oAddress.homePhone
                    binding.emailContent.text = oAddress.email
                    binding.postalCodeContent.text = oAddress.postalCode.toString()
                }
            }

            viewModel.addressAdded.collectLatest {
                if (it) {
                    viewModel.getAddress()
                    viewModel.setAddressAdded()
                }
            }
        }

//        binding.addressPersianBallImageButton.setOnClickListener {
//            val dialog =
//                EditInfoDialogFragment.newInstance(viewModel.address.address, InfoType.ADDRESS)
//            dialog.show(childFragmentManager, "edit_info_dialog")
//        }
//        binding.homeNumberPersianBallImageButton.setOnClickListener {
//            val dialog =
//                EditInfoDialogFragment.newInstance(viewModel.address.homePhone, InfoType.HOME_PHONE)
//            dialog.show(childFragmentManager, "edit_info_dialog")
//        }
//        binding.phoneNumberPersianBallImageButton.setOnClickListener {
//            val dialog = EditInfoDialogFragment.newInstance(
//                viewModel.address.mobilePhone,
//                InfoType.PHONE_NUMBER
//            )
//            dialog.show(childFragmentManager, "edit_info_dialog")
//        }
//        binding.emailPersianBallImageButton.setOnClickListener {
//            val dialog = EditInfoDialogFragment.newInstance(viewModel.address.email, InfoType.EMAIL)
//            dialog.show(childFragmentManager, "edit_info_dialog")
//        }
//        binding.postalCodePersianBallImageButton.setOnClickListener {
//            val dialog = EditInfoDialogFragment.newInstance(
//                viewModel.address.postalCode.toString(),
//                InfoType.POSTAL_CODE
//            )
//            dialog.show(childFragmentManager, "edit_info_dialog")
//        }
        binding.submitBtn.setOnClickListener {
//            if (viewModel.address.isEmpty()) {
//                Toast.makeText(
//                    requireActivity(),
//                    "هیچ اطلاعاتی وارد نکرده‌اید!",
//                    Toast.LENGTH_SHORT
//                ).show()
//            } else {
//                if (allDataFilled()) {
//                    viewModel.createAddress(viewModel.address)
//                }
//            }
//            viewLifecycleOwner.lifecycleScope.launch {
//            }
            val intent = Intent(activity, AddAddressActivity::class.java).apply {
                address?.also {
                    putExtra(ADDRESS_OBJECT_EXTRA_CODE, it)
                }
            }
            startActivityForResult(intent, ADDRESS_REQUEST_CODE)
        }
        return binding.root
    }

    private fun allDataFilled(): Boolean {
        var isAllRequireDataFilled = false
        if (viewModel.address.address.isNullOrEmpty()) {
            binding.addressContent.text = "وارد کردن این مقدار اجباری است"
            binding.addressContent.setTextColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.red
                )
            )
        } else if (viewModel.address.mobilePhone.isNullOrEmpty()) {
            binding.phoneNumberContent.text = "وارد کردن این مقدار اجباری است"
            binding.phoneNumberContent.setTextColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.red
                )
            )
        } else if (viewModel.address.homePhone.isNullOrEmpty()) {
            binding.homeNumberContent.text = "وارد کردن این مقدار اجباری است"
            binding.homeNumberContent.setTextColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.red
                )
            )
        } else if (viewModel.address.postalCode == null) {
            binding.postalCodeContent.text = "وارد کردن این مقدار اجباری است"
            binding.postalCodeContent.setTextColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.red
                )
            )
        } else {
            isAllRequireDataFilled = true
        }
        return isAllRequireDataFilled
    }

    interface OnSubmitClickListener {
        fun onSubmitClicked(info: ItemInfoDto)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                dialogViewModel.dialogResult.collectLatest { bundle ->
                    bundle.getString(InfoType.ADDRESS.type, null)
                        ?.also {
                            binding.addressContent.text = it
                            binding.addressContent.setTextColor(
                                ContextCompat.getColor(
                                    requireContext(),
                                    R.color.black
                                )
                            )
                            viewModel.address.address = it
                        }
                    bundle.getString(InfoType.HOME_PHONE.type, null)
                        ?.also {
                            binding.homeNumberContent.text = it
                            binding.homeNumberContent.setTextColor(
                                ContextCompat.getColor(
                                    requireContext(),
                                    R.color.black
                                )
                            )
                            viewModel.address.homePhone = it
                        }
                    bundle.getString(InfoType.PHONE_NUMBER.type, null)
                        ?.also {
                            binding.phoneNumberContent.text = it
                            binding.phoneNumberContent.setTextColor(
                                ContextCompat.getColor(
                                    requireContext(),
                                    R.color.black
                                )
                            )
                            viewModel.address.mobilePhone = it
                        }
                    bundle.getString(InfoType.EMAIL.type, null)
                        ?.also {
                            binding.emailContent.text = it
                            binding.emailContent.setTextColor(
                                ContextCompat.getColor(
                                    requireContext(),
                                    R.color.black
                                )
                            )
                            viewModel.address.email = it
                        }
                    bundle.getString(InfoType.POSTAL_CODE.type, null)
                        ?.also {
                            binding.postalCodeContent.text = it
                            binding.postalCodeContent.setTextColor(
                                ContextCompat.getColor(
                                    requireContext(),
                                    R.color.black
                                )
                            )
                            viewModel.address.postalCode = it.toLong()
                        }
                }
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.addressResponse.collectLatest {
                    when (it) {
                        is Resource.Success -> {
                            Toast.makeText(
                                requireActivity(),
                                "اطلاعات با موفقیت ارسال شد!",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                        is Resource.Failure -> {
                            Toast.makeText(
                                requireActivity(),
                                "ذخیره اطلاعات انجام نشد، دوباره تلاش کنید",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                        is Resource.Loading -> {
                            Toast.makeText(
                                requireActivity(),
                                "در حال ارسال اطلاعات...",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            }
        }
    }

    companion object {
        const val ADDRESS_REQUEST_CODE = 1234
        const val ADDRESS_OBJECT_EXTRA_CODE = "ADDRESS_OBJECT_EXTRA_CODE"

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