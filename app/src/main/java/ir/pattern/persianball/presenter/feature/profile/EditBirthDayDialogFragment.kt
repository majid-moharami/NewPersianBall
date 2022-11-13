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
import ir.pattern.persianball.databinding.EditBirthdayDialogBinding
import ir.pattern.persianball.databinding.EditInfoDialogBinding
import ir.pattern.persianball.utils.UiUtils
import javax.inject.Inject

@AndroidEntryPoint
class EditBirthDayDialogFragment(var birthday: Int, val infoType: InfoType) :
    DialogFragment() {

    lateinit var binding: EditBirthdayDialogBinding
    val years = ArrayList<Int>()

    private val dialogViewModel: EditInfoViewModel by viewModels({ requireParentFragment() })

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(requireActivity()),
            R.layout.edit_birthday_dialog,
            container,
            false
        )

        binding.submitBtn.setOnClickListener {
            dialogViewModel.setDialogResult(
                bundleOf(
                    Pair(
                        infoType.type,
                        years[binding.numberPicker.value].toString()
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initNumberPicker(birthday)
    }

    private fun initNumberPicker(birthday: Int) {
        val numbers = ArrayList<String>()
        for (i in 1335 until 1395) {
            numbers.add(UiUtils.convertToPersianNumber("$i"))
            years.add(i)
        }
        val arrayNumber = arrayOfNulls<String>(numbers.size)
        numbers.toArray(arrayNumber)
        binding.numberPicker.wrapSelectorWheel = false
        binding.numberPicker.minValue = 0
        binding.numberPicker.maxValue = numbers.size - 1
        val birthdayIndex = numbers.indexOf(UiUtils.convertToPersianNumber("$birthday"))
        binding.numberPicker.value = birthdayIndex
        binding.numberPicker.displayedValues = arrayNumber
    }

    companion object {
        private const val BUNDLE_KEY_MESSAGE = "BUNDLE_KEY_INFO_MESSAGE"
        private const val TAG = "EditInfoDialog"
        fun newInstance(
            birthday: Int = 1336,
            infoType: InfoType
        ): EditBirthDayDialogFragment {
            return EditBirthDayDialogFragment(birthday, infoType)
        }
    }
}