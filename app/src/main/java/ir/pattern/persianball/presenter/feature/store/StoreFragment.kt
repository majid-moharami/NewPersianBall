package ir.pattern.persianball.presenter.feature.store

import android.annotation.SuppressLint
import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import ir.pattern.persianball.R
import ir.pattern.persianball.data.model.Resource
import ir.pattern.persianball.data.model.shoppingCart.CartItem
import ir.pattern.persianball.databinding.FragmentStoreBinding
import ir.pattern.persianball.presenter.adapter.BasePagingAdapter
import ir.pattern.persianball.presenter.adapter.BaseViewHolder
import ir.pattern.persianball.presenter.feature.BaseFragment
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@AndroidEntryPoint
class StoreFragment : BaseFragment() {
    lateinit var binding: FragmentStoreBinding
    var pagingAdapter: BasePagingAdapter? = null
    private val viewModel: StoreViewModel by viewModels()

    override fun getChildView(inflater: LayoutInflater, container: ViewGroup?): View {
        binding = FragmentStoreBinding.inflate(inflater)
        return binding.root
    }

    @SuppressLint("ResourceAsColor")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recycler.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.recycler.addItemDecoration(object : RecyclerView.ItemDecoration() {
            override fun getItemOffsets(
                outRect: Rect,
                view: View,
                parent: RecyclerView,
                state: RecyclerView.State
            ) {
                val position: Int = parent.getChildAdapterPosition(view) // item position

                val spanCount = 2
                val spacing = 10 //spacing between views in grid

                if (position >= 0) {
                    val column = position % spanCount // item column
                    outRect.left =
                        spacing - column * spacing / spanCount // spacing - column * ((1f / spanCount) * spacing)
                    outRect.right =
                        (column + 1) * spacing / spanCount // (column + 1) * ((1f / spanCount) * spacing)
                    if (position < spanCount) { // top edge
                        outRect.top = spacing
                    }
                    outRect.bottom = spacing // item bottom
                } else {
                    outRect.left = 0
                    outRect.right = 0
                    outRect.top = 0
                    outRect.bottom = 0
                }
            }
        })
        pagingAdapter = StoreAdapter().also {
            binding.recycler.adapter = it

            it.onProductPageClickListener =
                BaseViewHolder.OnClickListener { view, viewHolder, recyclerData ->
                    if (recyclerData.storeDto.isAcademy) {
                        recyclerData.storeDto.academyDto?.also { academyDto ->
                            val action =
                                StoreFragmentDirections.actionStoreFragmentToMovieDetailFragment(
                                    academyDto.id
                                )
                            findNavController().navigate(action)
                        }
                    } else {
                        recyclerData.storeDto.product?.also { product ->
                            val action =
                                StoreFragmentDirections.actionStoreFragmentToProductDetailFragment(
                                    product.id
                                )
                            findNavController().navigate(action)

                        }
                    }
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
                viewModel.getCourses()
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.recyclerItems.collectLatest {
                it?.let { recyclerData ->
                    pagingAdapter?.submitData(recyclerData)
                }
            }
        }

        binding.classesFilter.setOnClickListener {
            viewModel.isClassFilter = !viewModel.isClassFilter
            if (viewModel.isClassFilter) {
                binding.classes.setBackgroundColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.search_filter_selected
                    )
                )
            } else {
                binding.classes.setBackgroundColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.search_filter_unselected
                    )
                )
            }
            viewModel.filterProducts()
        }

        binding.coursesFilter.setOnClickListener {
            viewModel.isCourseFilter = !viewModel.isCourseFilter
            if (viewModel.isCourseFilter) {
                binding.courses.setBackgroundColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.search_filter_selected
                    )
                )
            } else {
                binding.courses.setBackgroundColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.search_filter_unselected
                    )
                )
            }
            viewModel.filterProducts()
        }

        binding.productsFilter.setOnClickListener {
            viewModel.isProductFilter = !viewModel.isProductFilter
            if (viewModel.isProductFilter) {
                binding.products.setBackgroundColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.search_filter_selected
                    )
                )
            } else {
                binding.products.setBackgroundColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.search_filter_unselected
                    )
                )
            }
            viewModel.filterProducts()
        }

        binding.search.setOnClickListener {
            val direction = StoreFragmentDirections.actionStoreFragmentToStoreSearchFragment()
            findNavController().navigate(direction)
        }
    }
}