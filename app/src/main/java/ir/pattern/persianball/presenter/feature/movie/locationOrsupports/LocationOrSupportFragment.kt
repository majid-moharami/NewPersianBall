package ir.pattern.persianball.presenter.feature.movie.locationOrsupports

import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import ir.pattern.persianball.R
import ir.pattern.persianball.databinding.FragmentLessonsBinding
import ir.pattern.persianball.databinding.FragmentLocationOrSupportBinding
import ir.pattern.persianball.presenter.adapter.BasePagingAdapter
import ir.pattern.persianball.presenter.feature.movie.courses.LessonsViewModel
import ir.pattern.persianball.presenter.feature.setting.progress.ProgressFragment

class LocationOrSupportFragment : Fragment() {

    lateinit var binding: FragmentLocationOrSupportBinding
    var pagingAdapter: BasePagingAdapter? = null
    private val viewModel: LocationOrSupportViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(activity),
            R.layout.fragment_location_or_support,
            container,
            false
        )
        binding.txt.text = arguments?.getString("DESCRIPTION")
        binding.txt.movementMethod = ScrollingMovementMethod()
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
        fun newInstance(description: String?) =
            LocationOrSupportFragment().apply {
                arguments = Bundle().apply {
                    putString("DESCRIPTION", description)
                }
            }
    }
}