package ir.pattern.persianball.presenter.feature.movie.prerequisites

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import ir.pattern.persianball.R
import ir.pattern.persianball.databinding.FragmentLessonsBinding
import ir.pattern.persianball.databinding.FragmentPreRequisitesBinding
import ir.pattern.persianball.presenter.adapter.BasePagingAdapter
import ir.pattern.persianball.presenter.feature.movie.courses.LessonsViewModel
import ir.pattern.persianball.presenter.feature.setting.progress.ProgressFragment

@AndroidEntryPoint
class PreRequisitesFragment : Fragment() {

    lateinit var binding: FragmentPreRequisitesBinding
    var pagingAdapter: BasePagingAdapter? = null
    private val viewModel: PreRequisitesViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(activity),
            R.layout.fragment_pre_requisites,
            container,
            false
        )
        binding.txt.text = arguments?.getString("REQUISITES")
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
        fun newInstance(requisites: String?) =
            PreRequisitesFragment().apply {
                arguments = Bundle().apply {
                    putString("REQUISITES", requisites)
                }
            }
    }
}