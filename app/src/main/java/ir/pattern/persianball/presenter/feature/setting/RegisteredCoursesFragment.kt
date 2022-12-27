package ir.pattern.persianball.presenter.feature.setting

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import ir.pattern.persianball.R
import ir.pattern.persianball.databinding.FragmentRegisteredCoursesBinding
import ir.pattern.persianball.databinding.FragmentSettingBinding
import ir.pattern.persianball.presenter.adapter.BasePagingAdapter

@AndroidEntryPoint
class RegisteredCoursesFragment : Fragment() {
    lateinit var binding: FragmentRegisteredCoursesBinding
    var pagingAdapter: BasePagingAdapter? = null
    private val viewModel: RegisteredCoursesViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(activity),
            R.layout.fragment_registered_courses,
            container,
            false
        )
        return binding.root
    }

}