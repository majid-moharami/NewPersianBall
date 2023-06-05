package ir.pattern.persianball.presenter.feature.profile

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import ir.pattern.persianball.R
import ir.pattern.persianball.data.model.profile.PersonalDto
import ir.pattern.persianball.databinding.EditInfoDialogBinding
import kotlinx.coroutines.launch

class ChangeUserNameDialog : DialogFragment() {
    lateinit var binding: EditInfoDialogBinding

    private val dialogViewModel: ProfileViewModel by viewModels({ requireParentFragment() })

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
        binding.dialogTitle.text = "نام کاربری"
        binding.infoEditText.hint = "نام کاربری خود را وارد کنید"
        binding.submitBtn.setOnClickListener {
            if (!binding.infoEditText.text.isNullOrBlank()) {
                viewLifecycleOwner.lifecycleScope.launch {
                    dialogViewModel.setName(binding.infoEditText.text.toString())
                }
                dialogViewModel.profileRepository.username = binding.infoEditText.text.toString()
            }
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
        fun newInstance(
        ): ChangeUserNameDialog {
            return ChangeUserNameDialog()
        }
    }
}