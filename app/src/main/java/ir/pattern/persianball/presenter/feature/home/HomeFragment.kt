package ir.pattern.persianball.presenter.feature.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import ir.pattern.persianball.R
import ir.pattern.persianball.data.model.Resource
import ir.pattern.persianball.databinding.FragmentHomeBinding
import ir.pattern.persianball.presenter.adapter.BasePagingAdapter
import ir.pattern.persianball.presenter.adapter.BaseViewHolder
import ir.pattern.persianball.presenter.feature.BaseFragment
import ir.pattern.persianball.presenter.feature.player.PlayerActivity
import ir.pattern.persianball.views.TryAgainView
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@AndroidEntryPoint
class HomeFragment : BaseFragment() {
    lateinit var binding: FragmentHomeBinding
    private val viewModel: HomeViewModel by viewModels()
    var pagingAdapter: BasePagingAdapter? = null

    override fun getChildView(inflater: LayoutInflater, container: ViewGroup?): View {
        binding = FragmentHomeBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        pagingAdapter = HomeDataAdapter().also {
            binding.recyclerView.adapter = it
            it.onCourseClickListener =
                BaseViewHolder.OnClickListener { view, viewHolder, recyclerData ->
                    val directions =
                        HomeFragmentDirections.actionHomeFragmentToMovieDetailFragment(recyclerData.academy)
                    findNavController().navigate(directions)
                }
            baseBinding.loading
            it.onProductClickListener =
                BaseViewHolder.OnClickListener { view, viewHolder, recyclerData ->
                    val intent = Intent(requireActivity(), PlayerActivity::class.java)
                    startActivity(intent)
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
                viewModel.getGallery()
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

    var onTryAgainListener: TryAgainView.OnTryAgainListener = object :
        TryAgainView.OnTryAgainListener {
        override fun onTryAgain() {

        }
    }
}