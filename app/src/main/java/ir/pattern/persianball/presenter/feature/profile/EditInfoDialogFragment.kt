package ir.pattern.persianball.presenter.feature.profile

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import ir.pattern.persianball.R
import ir.pattern.persianball.data.model.profile.Address
import ir.pattern.persianball.data.model.profile.InfoType
import ir.pattern.persianball.data.repository.ProfileRepository
import ir.pattern.persianball.databinding.EditInfoDialogBinding
import javax.inject.Inject

@AndroidEntryPoint
class EditInfoDialogFragment(var input: String?, val infoType: InfoType) :
    DialogFragment() {

    lateinit var binding: EditInfoDialogBinding

    private val dialogViewModel: EditInfoViewModel by viewModels({ requireParentFragment() })

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(requireActivity()),
            R.layout.edit_info_dialog,
            container,
            false
        )
        binding.dialogTitle.text = infoType.type
        binding.infoEditText.hint = infoType.type
        if (!input.isNullOrEmpty() && !input.equals("null")) {
            binding.infoEditText.setText(input)
        }
        binding.submitBtn.setOnClickListener {
            dialogViewModel.setDialogResult(
                bundleOf(
                    Pair(
                        infoType.type,
                        binding.infoEditText.text.toString()
                    )
                )
            )
            dismiss()
        }
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        val dialog: Dialog? = dialog
        dialog?.let {
            it.setCancelable(true)
            it.window?.also { window ->
                with(window) {
                    setLayout(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                    )
                    setGravity(Gravity.CENTER)
                    setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                }
            }
        }
    }

    companion object {
        private const val BUNDLE_KEY_MESSAGE = "BUNDLE_KEY_INFO_MESSAGE"
        private const val TAG = "EditInfoDialog"
        fun newInstance(
            input: String?,
            infoType: InfoType
        ): EditInfoDialogFragment {
            return EditInfoDialogFragment(input, infoType)
        }
    }
}