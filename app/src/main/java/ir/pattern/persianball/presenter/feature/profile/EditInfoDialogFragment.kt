package ir.pattern.persianball.presenter.feature.profile

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import ir.pattern.persianball.R
import ir.pattern.persianball.data.model.profile.Address
import ir.pattern.persianball.data.model.profile.InfoType
import ir.pattern.persianball.data.repository.ProfileRepository
import ir.pattern.persianball.databinding.EditInfoDialogBinding
import ir.pattern.persianball.presenter.feature.profile.address.AddressViewModel
import ir.pattern.persianball.presenter.feature.profile.personalInformation.recycler.InfoItemData
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class EditInfoDialogFragment(var address: Address?, val infoType: InfoType) :
    DialogFragment() {

    @Inject
    lateinit var profileRepository: ProfileRepository
    lateinit var binding: EditInfoDialogBinding
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog =
        Dialog(requireActivity(), R.style.DialogTheme)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(activity),
            R.layout.edit_info_dialog,
            container,
            false
        )
        binding.dialogTitle.text = infoType.type
        binding.infoEditText.setHint(R.string.recovery_by_email)
        binding.submitBtn.setOnClickListener {
            when (infoType.type) {
                InfoType.ADDRESS.type -> {
                    address?.address = binding.infoEditText.text.toString()
                }
                InfoType.PHONE_NUMBER.type -> {
                    address?.mobilePhone = binding.infoEditText.text.toString()
                }
                InfoType.HOME_PHONE.type -> {
                    address?.homePhone = binding.infoEditText.text.toString()
                }
                InfoType.EMAIL.type -> {
                    address?.email = binding.infoEditText.text.toString()
                }
                InfoType.POSTAL_CODE.type -> {
                    address?.postalCode = binding.infoEditText.text.toString().toInt()
                }
            }
            viewLifecycleOwner.lifecycleScope.launch {
                profileRepository.setAddress(address)
            }
            dialog?.cancel()
        }
        return binding.root
    }


    companion object {
        private const val BUNDLE_KEY_MESSAGE = "BUNDLE_KEY_INFO_MESSAGE"
        private const val TAG = "EditInfoDialog"
        fun newInstance(
            address: Address?,
            infoType: InfoType
        ): EditInfoDialogFragment {
            return EditInfoDialogFragment(address, infoType)
        }
    }
}