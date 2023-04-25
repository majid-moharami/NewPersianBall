package ir.pattern.persianball.presenter.feature.store.search

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import ir.pattern.persianball.R
import ir.pattern.persianball.data.model.Resource
import ir.pattern.persianball.databinding.FragmentStoreBinding
import ir.pattern.persianball.databinding.FragmentStoreSearchBinding
import ir.pattern.persianball.presenter.adapter.BasePagingAdapter
import ir.pattern.persianball.presenter.adapter.BaseViewHolder
import ir.pattern.persianball.presenter.feature.BaseFragment
import ir.pattern.persianball.presenter.feature.store.StoreAdapter
import ir.pattern.persianball.presenter.feature.store.StoreFragmentDirections
import ir.pattern.persianball.presenter.feature.store.StoreViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class StoreSearchFragment : BaseFragment() {

    lateinit var binding: FragmentStoreSearchBinding
    var pagingAdapter: BasePagingAdapter? = null
    private val viewModel: StoreViewModel by viewModels()

    private var textWatcher: TextWatcher? = null


    override fun getChildView(inflater: LayoutInflater, container: ViewGroup?): View {
        binding = FragmentStoreSearchBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recycler.layoutManager = GridLayoutManager(requireContext(), 2)
        pagingAdapter = StoreAdapter().also {
            binding.recycler.adapter = it

            it.onProductPageClickListener =
                BaseViewHolder.OnClickListener { view, viewHolder, recyclerData ->
                    if (recyclerData.storeDto.isAcademy) {
                        recyclerData.storeDto.academyDto?.also { academyDto ->
                            val action =
                                StoreSearchFragmentDirections.actionStoreSearchFragmentToMovieDetailFragment(
                                    academyDto.id
                                )
                            findNavController().navigate(action)
                        }
                    } else {
                        recyclerData.storeDto.product?.also { product ->
                            val action =
                                StoreSearchFragmentDirections.actionStoreSearchFragmentToProductDetailFragment(
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


        if (textWatcher == null) {
            textWatcher = object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence,
                    start: Int,
                    count: Int,
                    after: Int
                ) {

                }

                override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {

                }

                override fun afterTextChanged(s: Editable) {
                    viewLifecycleOwner.lifecycleScope.launch {
                        delay(200)
                        viewModel.search(s.toString())
                    }
                }
            }
            binding.search.addTextChangedListener(textWatcher)
        }
    }
}