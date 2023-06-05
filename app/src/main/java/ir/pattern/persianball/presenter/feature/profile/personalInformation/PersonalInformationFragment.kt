package ir.pattern.persianball.presenter.feature.profile.personalInformation

import android.graphics.Color
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
import calendar.CivilDate
import calendar.DateConverter
import calendar.PersianDate
import dagger.hilt.android.AndroidEntryPoint
import ir.hamsaa.persiandatepicker.PersianDatePickerDialog
import ir.hamsaa.persiandatepicker.api.PersianPickerDate
import ir.hamsaa.persiandatepicker.api.PersianPickerListener
import ir.pattern.persianball.R
import ir.pattern.persianball.data.model.Resource
import ir.pattern.persianball.data.model.profile.InfoType
import ir.pattern.persianball.databinding.FragmentPersonalInformationBinding
import ir.pattern.persianball.presenter.feature.profile.EditGenderDialogFragment
import ir.pattern.persianball.presenter.feature.profile.EditInfoDialogFragment
import ir.pattern.persianball.presenter.feature.profile.EditInfoViewModel
import ir.pattern.persianball.utils.UiUtils
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


@AndroidEntryPoint
class PersonalInformationFragment : Fragment() {

    lateinit var binding: FragmentPersonalInformationBinding
    val viewModel: PersonalInfoViewModel by viewModels({ requireParentFragment() })
    private val dialogViewModel: EditInfoViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(activity),
            R.layout.fragment_personal_information,
            container,
            false
        )
        viewLifecycleOwner.lifecycleScope.launch {
            if (viewModel.profileRepository.user == null) {
                viewModel.getPersonalData()
            }else {
                viewModel._userPersonalData.value = viewModel.profileRepository.user
            }
        }
        binding.genderPersianBallImageButton.setOnClickListener {
            val dialog = EditGenderDialogFragment.newInstance(
                viewModel.personalData.gender,
                InfoType.GENDER
            )
            dialog.show(childFragmentManager, "edit_info_dialog")
        }

        binding.namePersianBallImageButton.setOnClickListener {
            val dialog = EditInfoDialogFragment.newInstance(
                viewModel.personalData.firstName,
                InfoType.NAME
            )
            dialog.show(childFragmentManager, "edit_info_dialog")
        }

        binding.familyPersianBallImageButton.setOnClickListener {
            val dialog = EditInfoDialogFragment.newInstance(
                viewModel.personalData.lastName,
                InfoType.FAMILY_NAME
            )
            dialog.show(childFragmentManager, "edit_info_dialog")
        }

        binding.latinNamePersianBallImageButton.setOnClickListener {
            val dialog = EditInfoDialogFragment.newInstance(
                viewModel.personalData.firstNameLatin,
                InfoType.LATIN_NAME
            )
            dialog.show(childFragmentManager, "edit_info_dialog")
        }

        binding.latinFamilyPersianBallImageButton.setOnClickListener {
            val dialog = EditInfoDialogFragment.newInstance(
                viewModel.personalData.lastNameLatin,
                InfoType.LATIN_FAMILY_NAME
            )
            dialog.show(childFragmentManager, "edit_info_dialog")
        }

        binding.nationalCodePersianBallImageButton.setOnClickListener {
            val dialog = EditInfoDialogFragment.newInstance(
                viewModel.personalData.nationCode.toString(),
                InfoType.NATIONAL_CODE
            )
            dialog.show(childFragmentManager, "edit_info_dialog")
        }

        binding.bithdatePersianBallImageButton.setOnClickListener {
            val picker = PersianDatePickerDialog(requireActivity())
                .setPositiveButtonString("باشه")
                .setNegativeButton("بیخیال")
                .setTodayButton("امروز")
                .setTodayButtonVisible(true)
                .setMinYear(1300)
                .setMaxYear(PersianDatePickerDialog.THIS_YEAR)
                .setMaxMonth(12)
                .setMaxDay(PersianDatePickerDialog.THIS_DAY)
                .setInitDate(1370, PersianDatePickerDialog.THIS_MONTH, 13)
                .setActionTextColor(Color.BLACK)
                .setTitleType(PersianDatePickerDialog.WEEKDAY_DAY_MONTH_YEAR)
                .setShowInBottomSheet(true)
                .setListener(object : PersianPickerListener {
                    override fun onDateSelected(persianPickerDate: PersianPickerDate) {
                        val month = persianPickerDate.persianMonth.let {
                            if (it.toString().length == 1) {
                                "0$it"
                            } else {
                                it
                            }
                        }
                        val day = persianPickerDate.persianDay.let {
                            if (it.toString().length == 1) {
                                "0$it"
                            } else {
                                it
                            }
                        }
                        val year = persianPickerDate.persianYear
                        val date =
                            "$year-$month-$day"
                        binding.bithdateContent.text = UiUtils.convertToPersianNumber(date)
                        binding.bithdateContent.setTextColor(
                            ContextCompat.getColor(
                                requireContext(),
                                R.color.black
                            )
                        )
                        val civilDate = DateConverter.persianToCivil(
                            PersianDate(
                                persianPickerDate.persianYear,
                                persianPickerDate.persianMonth,
                                persianPickerDate.persianDay
                            )
                        )
                        viewModel.personalData.birthDate =
                            "${civilDate.year}-${civilDate.month}-${civilDate.dayOfMonth}"
                        Toast.makeText(
                            context,
                            date,
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    override fun onDismissed() {}
                })

            picker.show()
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.userPersonalData.collectLatest { personalDto ->
                    personalDto?.also { dto ->
                        with(dto) {
                            viewModel.personalData.username = username
                            firstName?.also {
                                viewModel.personalData.firstName = it
                                binding.nameContent.text = it
                            }
                            lastName?.also {
                                viewModel.personalData.lastName = it
                                binding.familyContent.text = it
                            }
                            firstNameLatin?.also {
                                viewModel.personalData.firstNameLatin = it
                                binding.latinNameContent.text = it
                            }
                            lastNameLatin?.also {
                                viewModel.personalData.lastNameLatin = it
                                binding.latinFamilyContent.text = it
                            }
                            birthDate?.also {
                                viewModel.personalData.birthDate = it
                                binding.bithdateContent.text = getPersianDate(it.toString())
                            }
                            gender?.also {
                                viewModel.personalData.gender = it
                                binding.genderContent.text =
                                    UiUtils.convertGenderToPersianString(it)
                            }
                            nationCode?.also {
                                viewModel.personalData.nationCode = it
                                binding.nationalCodeContent.text = it.toString()
                            }
                            nationality?.also {
                                viewModel.personalData.nationality = it
                                binding.nationContent.text = it.toString()
                            }
                        }
                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                dialogViewModel.dialogResult.collectLatest { bundle ->
                    bundle.getString(InfoType.NAME.type, null)
                        ?.also {
                            binding.nameContent.text = it
                            binding.nameContent.setTextColor(
                                ContextCompat.getColor(
                                    requireContext(),
                                    R.color.black
                                )
                            )
                            viewModel.personalData.firstName = it
                        }
                    bundle.getString(InfoType.FAMILY_NAME.type, null)
                        ?.also {
                            binding.familyContent.text = it
                            binding.familyContent.setTextColor(
                                ContextCompat.getColor(
                                    requireContext(),
                                    R.color.black
                                )
                            )
                            viewModel.personalData.lastName = it
                        }
                    bundle.getString(InfoType.LATIN_NAME.type, null)
                        ?.also {
                            binding.latinNameContent.text = it
                            binding.latinNameContent.setTextColor(
                                ContextCompat.getColor(
                                    requireContext(),
                                    R.color.black
                                )
                            )
                            viewModel.personalData.firstNameLatin = it
                        }
                    bundle.getString(InfoType.LATIN_FAMILY_NAME.type, null)
                        ?.also {
                            binding.latinFamilyContent.text = it
                            binding.latinFamilyContent.setTextColor(
                                ContextCompat.getColor(
                                    requireContext(),
                                    R.color.black
                                )
                            )
                            viewModel.personalData.lastNameLatin = it
                        }
                    bundle.getString(InfoType.NATION.type, null)
                        ?.also {
                            binding.nationContent.text = it
                            binding.nationContent.setTextColor(
                                ContextCompat.getColor(
                                    requireContext(),
                                    R.color.black
                                )
                            )
                            viewModel.personalData.nationality = it.toInt()
                        }

                    bundle.getString(InfoType.NATIONAL_CODE.type, null)
                        ?.also {
                            if (it.length == 10) {
                                binding.nationalCodeContent.text = it
                                binding.nationalCodeContent.setTextColor(
                                    ContextCompat.getColor(
                                        requireContext(),
                                        R.color.black
                                    )
                                )
                                viewModel.personalData.nationCode = it.toLong()
                            } else {
                                Toast.makeText(
                                    requireContext(),
                                    "کد ملی ده رقمی را به درستی وارد کنید.",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        }

                    bundle.getString(InfoType.GENDER.type, null)
                        ?.also {
                            binding.genderContent.text = it
                            binding.genderContent.setTextColor(
                                ContextCompat.getColor(
                                    requireContext(),
                                    R.color.black
                                )
                            )
                            viewModel.personalData.gender = if (it == "مرد") "male" else "female"
                        }
                }
            }
        }
        binding.submitBtn.setOnClickListener {
            viewModel.updatePersonalData(viewModel.personalData)
            viewModel.getPersonalData()
        }
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.userUpdatePersonalData.collectLatest {
                    when (it) {
                        is Resource.Success -> {
                            it.data?.also { dto ->
                                viewModel.personalData = dto
                            }
                            Toast.makeText(
                                requireActivity(),
                                "اطلاعات با موفقیت ذخیره شد",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                        is Resource.Loading -> {
                            Toast.makeText(
                                requireActivity(),
                                "در حال ذخیره اطلاعات",
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
                    }
                }
            }
        }

    }

    fun getPersianDate(date: String?): String {
        if (date.isNullOrEmpty()) {
            return ""
        } else {
            val format = SimpleDateFormat("yyyy-MM-dd")
            val date: Date = format.parse(date)
            val c = Calendar.getInstance()
            c.time = date
            val persianDate = DateConverter.civilToPersian(CivilDate(c))
            return "${persianDate.year}/${persianDate.month}/${persianDate.dayOfMonth}"
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
            PersonalInformationFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}