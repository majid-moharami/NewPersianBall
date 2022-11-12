package ir.pattern.persianball.presenter.feature.profile.personalInformation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import ir.pattern.persianball.R
import ir.pattern.persianball.data.model.profile.ItemInfoDto
import ir.pattern.persianball.databinding.FragmentPersonalInformationBinding
import ir.pattern.persianball.presenter.adapter.BasePagingAdapter
import ir.pattern.persianball.presenter.adapter.BaseViewHolder
import ir.pattern.persianball.presenter.feature.profile.EditInfoDialogFragment
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class PersonalInformationFragment : Fragment() {

    lateinit var binding: FragmentPersonalInformationBinding
    private val viewModel: PersonalInfoViewModel by viewModels()
    var pagingAdapter: BasePagingAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
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
//        pagingAdapter = PersonalInfoDataAdapter().also {
//            binding.recyclerView.adapter = it
//        }.apply {
//            onEditClickListener = BaseViewHolder.OnClickListener { view, viewHolder, recyclerData ->
////                val dialog = EditInfoDialogFragment.newInstance(recyclerData.addressDto,
////                    object : OnSubmitClickListener {
////                        override fun onSubmitClicked(info: ItemInfoDto) {
////                            viewModel.updateList(info)
////                        }
////                    }, null)
////                dialog.show(requireActivity().supportFragmentManager, "edit_info_dialog")
//
//            }
//        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.recyclerItems.collectLatest {
                it?.let { recyclerData ->
                    pagingAdapter?.submitData(recyclerData)
                }
            }
        }
        return binding.root
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