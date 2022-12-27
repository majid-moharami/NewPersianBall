package ir.pattern.persianball.presenter.feature.setting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import ir.pattern.persianball.R
import ir.pattern.persianball.databinding.FragmentHomeBinding
import ir.pattern.persianball.databinding.FragmentSettingBinding
import ir.pattern.persianball.presenter.adapter.BasePagingAdapter
import ir.pattern.persianball.presenter.feature.BaseFragment
import ir.pattern.persianball.presenter.feature.home.HomeViewModel

@AndroidEntryPoint
class SettingFragment : BaseFragment() {
    lateinit var binding: FragmentSettingBinding
    var pagingAdapter: BasePagingAdapter? = null
    private val viewModel: SettingViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(activity),
            R.layout.fragment_setting,
            container,
            false
        )
        binding.viewPager.setCurrentItem(1, false)
        return binding.root
    }
}