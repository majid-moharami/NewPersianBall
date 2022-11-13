package ir.pattern.persianball.presenter.feature.profile

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.TypedValue
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import ir.pattern.persianball.R
import ir.pattern.persianball.data.model.profile.InfoType
import ir.pattern.persianball.data.repository.ProfileRepository
import ir.pattern.persianball.databinding.EditGenderDialogBinding
import ir.pattern.persianball.utils.UiUtils
import javax.inject.Inject

@AndroidEntryPoint
class EditGenderDialogFragment(var input: String?, val infoType: InfoType) :
    DialogFragment() {

    lateinit var binding: EditGenderDialogBinding

    private val genders = ArrayList<String>()
    private var checkedItem = -1

    private val dialogViewModel: EditInfoViewModel by viewModels({ requireParentFragment() })

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(requireActivity()),
            R.layout.edit_gender_dialog,
            container,
            false
        )

        binding.submitBtn.setOnClickListener {
            if (checkedItem != -1) {
                dialogViewModel.setDialogResult(bundleOf(Pair(infoType.type, genders[checkedItem])))
            }
            dismiss()
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initNumberPicker(input)
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

    private fun initNumberPicker(gender: String?) {
        genders.add("مرد")
        genders.add("زن")
        val arrayNumber = arrayOfNulls<String>(genders.size)
        genders.toArray(arrayNumber)
        val selectedIndex =
            genders.indexOf(gender?.let { UiUtils.convertGenderToPersianString(it) })
        createRadioButton(selectedIndex)
        binding.radioGroup.setOnCheckedChangeListener { _, checkedId ->
            checkedItem = checkedId
        }
    }

    private fun createRadioButton(selectedIndex: Int) {
        genders.forEachIndexed { index, s ->
            val radioButton = RadioButton(requireContext())
            val param = RadioGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            param.setMargins(
                resources.getDimension(R.dimen.margin_default_v2).toInt(),
                resources.getDimension(R.dimen.margin_default_v2).toInt(),
                resources.getDimension(R.dimen.margin_default_v2).toInt(),
                resources.getDimension(R.dimen.margin_default_v2).toInt()
            )
            radioButton.layoutParams = param
            radioButton.id = index
            radioButton.text = s
            radioButton.gravity = Gravity.RIGHT
            radioButton.compoundDrawablePadding =
                resources.getDimension(R.dimen.font_size_large).toInt()
            radioButton.typeface =
                ResourcesCompat.getFont(requireContext(), R.font.iran_sans)
            radioButton.setTextSize(
                TypedValue.COMPLEX_UNIT_PX,
                resources.getDimension(R.dimen.font_size_large)
            )
            radioButton.buttonDrawable = null
            radioButton.setCompoundDrawablesWithIntrinsicBounds(
                null,
                null,
                ContextCompat.getDrawable(requireContext(), R.drawable.radio_btn_selector),
                null
            )
            radioButton.compoundDrawablePadding =
                resources.getDimension(R.dimen.margin_default_v2).toInt()
            binding.radioGroup.addView(radioButton)
        }
        binding.radioGroup.check(selectedIndex)
    }

    companion object {
        private const val BUNDLE_KEY_MESSAGE = "BUNDLE_KEY_INFO_MESSAGE"
        private const val TAG = "EditInfoDialog"
        fun newInstance(
            input: String?,
            infoType: InfoType
        ): EditGenderDialogFragment {
            return EditGenderDialogFragment(input, infoType)
        }
    }
}