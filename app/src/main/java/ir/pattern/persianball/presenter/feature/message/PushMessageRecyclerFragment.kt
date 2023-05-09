package ir.pattern.persianball.presenter.feature.message

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.PagingData
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import ir.pattern.persianball.R
import ir.pattern.persianball.data.model.RecyclerItem
import ir.pattern.persianball.data.model.Resource
import ir.pattern.persianball.data.model.base.RecyclerData
import ir.pattern.persianball.databinding.FragmentMovieListBinding
import ir.pattern.persianball.databinding.FragmentPushMessageRecyclerBinding
import ir.pattern.persianball.presenter.adapter.BasePagingAdapter
import ir.pattern.persianball.presenter.feature.movie.SectionDataAdapter
import ir.pattern.persianball.presenter.feature.movie.SectionListViewModel
import ir.pattern.persianball.presenter.feature.movie.recycler.PosterData
import ir.pattern.persianball.presenter.feature.movie.recycler.SectionHeaderData
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch


@AndroidEntryPoint
class PushMessageRecyclerFragment : Fragment() {
    lateinit var binding: FragmentPushMessageRecyclerBinding
    private val viewModel: PushMessageViewModel by viewModels()
    var pagingAdapter: BasePagingAdapter? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(activity),
            R.layout.fragment_push_message_recycler,
            container,
            false
        )
        showLoading(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        pagingAdapter = MessageAdapter().also {
            binding.recyclerView.adapter = it
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.recyclerItems.collectLatest {
                it?.let { recyclerData ->
                    showLoading(false)
                    pagingAdapter?.submitData(recyclerData)
                }
            }
        }
    }

    private fun showLoading(show: Boolean) {
        if (show) {
            binding.frameLayout.visibility = View.VISIBLE
            binding.loading.isIndeterminate = true
            binding.loading.visibility = View.VISIBLE
        } else {
            binding.frameLayout.visibility = View.GONE
            binding.loading.isIndeterminate = false
            binding.loading.visibility = View.GONE
        }
    }
}