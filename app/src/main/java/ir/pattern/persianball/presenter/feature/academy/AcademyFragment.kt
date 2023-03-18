package ir.pattern.persianball.presenter.feature.academy

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import ir.pattern.persianball.R
import ir.pattern.persianball.data.model.Resource
import ir.pattern.persianball.databinding.FragmentAcademyBinding
import ir.pattern.persianball.databinding.FragmentHomeBinding
import ir.pattern.persianball.presenter.adapter.BasePagingAdapter
import ir.pattern.persianball.presenter.adapter.BaseViewHolder
import ir.pattern.persianball.presenter.feature.BaseFragment
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AcademyFragment : BaseFragment() {

    lateinit var binding: FragmentAcademyBinding
    var pagingAdapter: BasePagingAdapter? = null
    private val viewModel: AcademyViewModel by viewModels()


    override fun getChildView(inflater: LayoutInflater, container: ViewGroup?): View {
        binding = FragmentAcademyBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recycler.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        pagingAdapter = AcademyAdapter().also {
            binding.recycler.adapter = it

            it.onCourseClickListener =
                BaseViewHolder.OnClickListener { view, viewHolder, recyclerData ->
                    val directions =
                        AcademyFragmentDirections.actionAcademyFragmentToMovieDetailFragment(
                            recyclerData.academy
                        )
                    findNavController().navigate(directions)
                }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.cartList.collect {
                when (it) {
                    is Resource.Success -> {
                        showLoading(false)
                        binding.recycler.isVisible = true
                    }
                    is Resource.Failure -> {
                        showLoading(false)
                        showTryAgainView(true)
                        binding.recycler.isVisible = false
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
                viewModel.getAcademy()
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
}