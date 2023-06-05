package ir.pattern.persianball.presenter.feature.movie

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.PagingData
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import ir.pattern.persianball.R
import ir.pattern.persianball.data.model.RecyclerItem
import ir.pattern.persianball.data.model.Resource
import ir.pattern.persianball.data.model.academy.AcademyDto
import ir.pattern.persianball.data.model.base.RecyclerData
import ir.pattern.persianball.databinding.FragmentMovieDetailBinding
import ir.pattern.persianball.databinding.FragmentMovieListBinding
import ir.pattern.persianball.presenter.adapter.BasePagingAdapter
import ir.pattern.persianball.presenter.adapter.BaseViewHolder
import ir.pattern.persianball.presenter.feature.home.recycler.HomeProductData
import ir.pattern.persianball.presenter.feature.movie.dialog.DetailChooseDialogFragment
import ir.pattern.persianball.presenter.feature.movie.dialog.SendToSupportDialog
import ir.pattern.persianball.presenter.feature.movie.dialog.recycler.LocationItemData
import ir.pattern.persianball.presenter.feature.movie.recycler.PosterData
import ir.pattern.persianball.presenter.feature.movie.recycler.SectionHeaderData
import ir.pattern.persianball.presenter.feature.player.PlayerActivity
import ir.pattern.persianball.presenter.feature.player.PlayerRepository
import ir.pattern.persianball.presenter.feature.profile.personalInformation.PersonalInfoViewModel
import ir.pattern.persianball.presenter.feature.store.StoreAdapter
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class SectionListFragment : Fragment() {

    lateinit var binding: FragmentMovieListBinding
    private val viewModel: SectionListViewModel by viewModels()
    var pagingAdapter: BasePagingAdapter? = null
    private lateinit var args: SectionListFragmentArgs
    lateinit var movie: AcademyDto

    @Inject
    lateinit var playerRepository: PlayerRepository

    override fun onAttach(context: Context) {
        super.onAttach(context)
        args = SectionListFragmentArgs.fromBundle(requireArguments())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(activity),
            R.layout.fragment_movie_list,
            container,
            false
        )
        showLoading(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.getAcademyById(args.id)
        }
        binding.recyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        pagingAdapter = SectionDataAdapter().also {
            binding.recyclerView.adapter = it

            it.onSectionHeaderClickListener =
                BaseViewHolder.OnClickListener { view, viewHolder, recyclerData ->
                    viewModel.updateSections(recyclerData)
                    viewHolder.binding.arrow.setImageDrawable(resources.getDrawable(if (recyclerData.isOpen) R.drawable.ic_up else R.drawable.ic_down))
                }

            it.onMovieClickListener =
                BaseViewHolder.OnClickListener { view, viewHolder, recyclerData ->
                    if (recyclerData.section.isLocked && playerRepository.soldVariant?.coach==null) {
                        Toast.makeText(
                            activity,
                            "برای مشاهده کامل دوره آن را خریداری کنید.",
                            Toast.LENGTH_LONG
                        ).show()
                    }else if(recyclerData.section.isLocked && playerRepository.soldVariant?.coach!=null){
                        Toast.makeText(
                            activity,
                            "تمرین های ارسالی شما درحال بررسی است. این فرایند ممکن است زمان بر باشد.",
                            Toast.LENGTH_LONG
                        ).show()
                    } else {
                        playerRepository.videoUrl = null
                        playerRepository.videoUrl = recyclerData.section.videoUrl
                        val intent = Intent(requireActivity(), PlayerActivity::class.java)
                        intent.putExtra("HAVE_URL", false)
                        intent.putExtra("URL", "")
                        startActivity(intent)
                    }
                }

            it.onSendClickListener =
                BaseViewHolder.OnClickListener { view, viewHolder, recyclerData ->
                    SendToSupportDialog.newInstance()
                        .show(childFragmentManager, "send_dialog")
                }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.academyDto.collect {
                when (it) {
                    is Resource.Success -> {
                        showLoading(false)
                        movie = it.data
                        if (movie.category?.nameFarsi == "کلاس ها") {
                            viewModel.allList.add(
                                RecyclerItem(
                                    PosterData(
                                        movie.image,
                                        movie.weekCount,
                                        movie.courseDuration,
                                        movie.category?.nameFarsi
                                    )
                                )
                            )
                        }else{
                            viewModel.allList.add(
                                RecyclerItem(
                                    PosterData(
                                        movie.image,
                                        movie.weekCount,
                                        movie.courseDuration,
                                        movie.category?.nameFarsi
                                    )
                                )
                            )
                        }
                        viewModel.allList.addAll(viewModel.detail.sections.map {
                            RecyclerItem(
                                SectionHeaderData(false, it)
                            )
                        })
                        viewModel._recyclerItems.value =
                            RecyclerData(flowOf(PagingData.from(viewModel.allList)))
                    }
                    is Resource.Failure -> {
                    }
                    else -> {
                    }
                }
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