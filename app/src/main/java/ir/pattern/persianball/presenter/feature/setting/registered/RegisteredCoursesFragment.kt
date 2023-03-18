package ir.pattern.persianball.presenter.feature.setting.registered

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import ir.pattern.persianball.R
import ir.pattern.persianball.data.model.Resource
import ir.pattern.persianball.databinding.FragmentRegisteredCoursesBinding
import ir.pattern.persianball.presenter.adapter.BasePagingAdapter
import ir.pattern.persianball.presenter.feature.BaseFragment
import ir.pattern.persianball.presenter.feature.profile.password.ProfilePasswordFragment
import ir.pattern.persianball.presenter.feature.setting.registered.RegisteredCoursesViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RegisteredCoursesFragment : BaseFragment() {
    lateinit var binding: FragmentRegisteredCoursesBinding
    var pagingAdapter: BasePagingAdapter? = null
    private val viewModel: RegisteredCoursesViewModel by viewModels()

    override fun getChildView(inflater: LayoutInflater, container: ViewGroup?): View {
        binding = FragmentRegisteredCoursesBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        pagingAdapter = RegisteredCoursesAdapter().also {
            binding.recyclerView.adapter = it
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.isEmpty.collect{
                showEmptyLayout(it)
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.cartList.collect {
                when (it) {
                    is Resource.Success -> {
                        showLoading(false)
                        binding.recyclerView.isVisible = true
                    }
                    is Resource.Failure -> {
                        showLoading(false)
                        showTryAgainView(true)
                        binding.recyclerView.isVisible = false
                    }
                    else -> {
                        showLoading(true)
                    }
                }
            }
        }

        baseBinding.tryAgainBtn.setOnClickListener {
            showTryAgainView(false)
            viewLifecycleOwner.lifecycleScope.launch {
                viewModel.getDashboard()
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.recyclerItems.collectLatest {
                it?.let { recyclerData ->
                    pagingAdapter?.submitData(recyclerData)
                }
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            RegisteredCoursesFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}