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
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import ir.pattern.persianball.R
import ir.pattern.persianball.data.model.RecyclerItem
import ir.pattern.persianball.data.model.Resource
import ir.pattern.persianball.data.model.academy.AcademyDto
import ir.pattern.persianball.data.model.academy.VariantDto
import ir.pattern.persianball.data.model.shoppingCart.CartItem
import ir.pattern.persianball.data.repository.DashboardRepository
import ir.pattern.persianball.databinding.FragmentMovieDetailBinding
import ir.pattern.persianball.manager.AccountManager
import ir.pattern.persianball.presenter.feature.BaseFragment
import ir.pattern.persianball.presenter.feature.movie.dialog.CourseChooseViewModel
import ir.pattern.persianball.presenter.feature.movie.dialog.DetailChooseDialogFragment
import ir.pattern.persianball.presenter.feature.movie.dialog.recycler.CoachItemData
import ir.pattern.persianball.presenter.feature.movie.dialog.recycler.GiftProductItemData
import ir.pattern.persianball.presenter.feature.movie.dialog.recycler.LocationItemData
import ir.pattern.persianball.presenter.feature.movie.locationOrsupports.LocationOrSupportFragment
import ir.pattern.persianball.presenter.feature.movie.prerequisites.PreRequisitesFragment
import ir.pattern.persianball.presenter.feature.player.PlayerActivity
import ir.pattern.persianball.presenter.feature.player.PlayerRepository
import ir.pattern.persianball.utils.UiUtils.convertToPersianNumber
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class MovieDetailFragment : BaseFragment() {

    lateinit var binding: FragmentMovieDetailBinding
    private val viewModel: MovieDetailViewModel by viewModels()
    private val dialogViewModel: CourseChooseViewModel by viewModels()
    lateinit var movie: AcademyDto
    private var movieId: Int = -1
    private lateinit var args: MovieDetailFragmentArgs
    private val decimalForm =
        DecimalFormat("#,###", DecimalFormatSymbols.getInstance(Locale.US).apply {
            groupingSeparator = ','
        })

    @Inject
    lateinit var accountManager: AccountManager

    @Inject
    lateinit var playerRepository: PlayerRepository

    @Inject
    lateinit var dashboardRepository: DashboardRepository

    override fun onAttach(context: Context) {
        super.onAttach(context)
        args = MovieDetailFragmentArgs.fromBundle(requireArguments())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        movieId = args.id
    }

    override fun getChildView(inflater: LayoutInflater, container: ViewGroup?): View {
        binding = FragmentMovieDetailBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.getAcademyById(movieId)
        }
        binding.backBtn.setOnClickListener {
            requireActivity().onBackPressed()
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.academyDto.collect {
                when (it) {
                    is Resource.Success -> {
                        movie = it.data
                        viewModel.setAppropriateMap(movie)
                        binding.productChooseName.text = "انتخاب محصول هدیه"
                        if (movie.category?.nameFarsi == "دوره ها") {
                            val firstSupport = viewModel.supportMap.keys.toList()[0]
                            binding.supportName.text = "انتخاب پشتیبان"
                            //viewModel.setSelectedCoach(firstSupport)
                        } else {
                            binding.supportName.text = "انتخاب زمان و مکان"
                            val firstLocation = viewModel.locationMap.keys.toList()[0]
                            //viewModel.setSelectedLocation(firstLocation)
                        }
                        val adapter = ViewPagerAdapter(childFragmentManager, lifecycle, movie)
                        binding.viewpager.adapter = adapter
                        binding.viewpager.setCurrentItem(2, false)
                        TabLayoutMediator(binding.tabLayout, binding.viewpager) { tab, position ->
                            when (position) {
                                0 -> tab.text = resources.getString(R.string.pre_requisites)
                                1 -> tab.text = resources.getString(R.string.description)
                            }
                        }.attach()
                        initView()
                        loading(show = false)
                    }

                    is Resource.Failure -> {
                        loading(show = true)
                    }

                    else -> {
                        loading(show = true)
                    }
                }
            }
        }

        binding.addBtn.setOnClickListener {
            binding.addBtn.isEnabled = false
            viewLifecycleOwner.lifecycleScope.launch {
                if (!accountManager.isLogin) {
                    Toast.makeText(
                        requireActivity(),
                        "برای اضافه کردن به سبد خرید ابتدا لاگین کنید.",
                        Toast.LENGTH_LONG
                    ).show()
                    return@launch
                }
                if (movie.category?.nameFarsi == "دوره ها") {
                    viewModel.getSelectedVariant(movie)?.also {
                        viewModel.addCartItem(
                            CartItem(
                                course = it.id,
                                quantity = 1
                            )
                        )
                    } ?: Toast.makeText(
                        requireContext(),
                        "ابتدا پشتیبان و محصول هدیه خود را انتخاب کنید.",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    viewModel.getSelectedLocation(movie)?.also {
                        viewModel.addCartItem(
                            CartItem(
                                course = it.id,
                                quantity = 1
                            )
                        )
                    } ?: Toast.makeText(
                        requireContext(),
                        "ابتدا زمان و مکان و محصول هدیه خود را انتخاب کنید.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.addingCartFlow.collect {
                when (it) {
                    "در حال اضافه کردن به سبد خرید." -> {
                        binding.addBtn.isEnabled = false
                    }

                    else -> {
                        lifecycleScope.launch {
                            delay(4000)
                            binding.addBtn.isEnabled = true
                        }
                    }
                }
                Toast.makeText(
                    requireContext(),
                    it,
                    Toast.LENGTH_SHORT
                ).show()

            }
        }

        binding.videos.setOnClickListener {
            val directions =
                MovieDetailFragmentDirections.actionMovieDetailFragmentToSectionListFragment(movieId)
            findNavController().navigate(directions)
        }

        binding.watch.setOnClickListener {
            if (!movie.video.isNullOrEmpty()) {
                playerRepository.videoUrl = null
                playerRepository.videoUrl = movie.video
                val intent = Intent(requireActivity(), PlayerActivity::class.java)
                intent.putExtra("HAVE_URL", false)
                intent.putExtra("URL", "")
                startActivity(intent)
            } else {
                Toast.makeText(
                    requireActivity(),
                    "پیش نمایش وجود ندارد.",
                    Toast.LENGTH_LONG
                ).show()
            }
        }

        baseBinding.tryAgainBtn.setOnClickListener {
            showTryAgainView(false)
            viewLifecycleOwner.lifecycleScope.launch {
                viewModel.getAcademyById(movieId)
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.selectedCoach.collect {
                if (viewModel.supportMap.isNotEmpty()) {
                    val firstProduct = viewModel.supportMap[it]
                    firstProduct?.get(0)?.let { it1 ->
                        //viewModel.setSelectedGift(it1)
                        viewModel.selectedGifts = it1
                    }
                    Glide.with(requireContext())
                        .load("https://api.persianball.ir/${it?.avatar}")
                        .into(binding.supportImage)
                    binding.supportName.text = it?.fullName
                    Glide.with(requireContext())
                        .load("https://api.persianball.ir/${firstProduct?.get(0)?.image}")
                        .into(binding.productImage)
                    firstProduct?.get(0)?.also { f ->
                        binding.productChooseName.text = f.productName
                    }

                    it?.also {
                        val variant = viewModel.getSelectedVariant(movie)
                        val s = variant?.discountPercentage
                        if (s != null) {
                            binding.realPrice.isVisible = true
                            binding.realPrice.text =
                                resources.getString(
                                    R.string.product_price,
                                    decimalForm.format(variant.price)
                                )
                            binding.discountedPrice.text = resources.getString(
                                R.string.product_price,
                                (decimalForm.format(variant.price.minus((variant.price * s / 100))))
                            )
                        } else {
                            binding.discountedPrice.text =
                                resources.getString(
                                    R.string.product_price,
                                    decimalForm.format(variant?.price)
                                )
                            binding.realPrice.isVisible = false
                        }
                    }
                }
            }

        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.selectedLocation.collect {
                if (viewModel.locationMap.isNotEmpty()) {
                    val firstProduct = viewModel.locationMap[it]
                    viewModel.selectedGifts = firstProduct?.get(0)
                    Glide.with(requireContext())
                        .load("https://api.persianball.ir/${it?.image}")
                        .into(binding.supportImage)
                    binding.supportName.text = "${it?.location} \n ${it?.time}"
                    Glide.with(requireContext())
                        .load("https://api.persianball.ir/${firstProduct?.get(0)?.image}")
                        .into(binding.productImage)
                    firstProduct?.get(0)?.also {f ->
                        binding.productChooseName.text = f.productName
                    }


                    val s = it?.discountPercentage
                    if (s != null) {
                        binding.realPrice.isVisible = true
                        binding.realPrice.text =
                            resources.getString(
                                R.string.product_price,
                                decimalForm.format(it?.price)
                            )
                        binding.discountedPrice.text = resources.getString(
                            R.string.product_price,
                            (decimalForm.format(it.price.minus((it.price * s / 100))))
                        )
                    } else {
                        if (it?.price != null) {
                            binding.discountedPrice.text =
                                resources.getString(
                                    R.string.product_price,
                                    decimalForm.format(it.price)
                                )
                            binding.realPrice.isVisible = false
                        }
                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.selectedGift.collect {
                Glide.with(requireContext())
                    .load("https://api.persianball.ir/${it?.image}")
                    .into(binding.productImage)
                binding.productChooseName.text = it?.productName
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.selectedGift.collect {
                Glide.with(requireContext())
                    .load("https://api.persianball.ir/${it?.image}")
                    .into(binding.productImage)
                binding.productChooseName.text = it?.productName
            }
        }
    }

    private fun showPage(show: Boolean) {
        binding.constraintLayout.isVisible = show
        binding.materialCardView.isVisible = show
        binding.viewpager.isVisible = show
//        binding.cardView7.isVisible = show
    }

    private fun initView() {
        var show = true
        playerRepository.soldVariant = null
        movie.variants.map {v->
            dashboardRepository.userCourse?.results?.map {
                if (v?.id == it.course_variant_id && movie.id == it.courseId){
                    playerRepository.soldVariant = v
                    show = false
                    binding.cardView7.isVisible = false
                }
            }
        }
        if (show){
            binding.cardView7.isVisible = true
        }
        binding.headerTitle.text = movie.courseTitle
        Glide.with(requireContext()).load("https://api.persianball.ir/${movie.image}")
            .into(binding.poster)
        if (movie.courseDuration > 0) {
            binding.videoTime.text = convertTimeToString(movie.courseDuration)
        } else {
            binding.videoTime.isVisible = false
        }
        binding.videos.visibility =
            if (movie.category?.nameFarsi != "کلاس ها") View.VISIBLE else View.INVISIBLE
        if (movie.category?.nameFarsi == "کلاس ها") {
            binding.videoCount.text =
                resources.getString(R.string.section_count, movie.section_count.toString())
        } else {
            binding.videoCount.text =
                resources.getString(R.string.week_count, movie.weekCount.toString())
        }
        movie.coursePrice?.also {
            binding.realPrice.text =
                resources.getString(R.string.product_price, decimalForm.format(it))
            val s = movie.discountPercentage
            if (s != null) {
                binding.discountedPrice.text = resources.getString(
                    R.string.product_price,
                    (decimalForm.format(it.minus((it * s / 100))))
                )
            }
        }

        binding.chooseSupportLayout.setOnClickListener {
            if (movie.category?.nameFarsi == "دوره ها") {
                DetailChooseDialogFragment.newInstance(
                    "انتخاب پشتیبان",
                    viewModel.supportMap.keys.toList().map { RecyclerItem(CoachItemData(it)) })
                    .show(childFragmentManager, "course_choose_dialog")
            } else {
                DetailChooseDialogFragment.newInstance(
                    "انتخاب زمان و مکان",
                    viewModel.locationMap.keys.toList().map { RecyclerItem(LocationItemData(it)) })
                    .show(childFragmentManager, "course_choose_dialog")
            }
        }

        binding.chooseProductLayout.setOnClickListener {
            val list = if (movie.category?.nameFarsi == "دوره ها") {
                viewModel.supportMap[viewModel.selectedCoach.value]?.also { list ->
                    list.map { it?.let { RecyclerItem(GiftProductItemData(it)) } }
                }
            } else {
                viewModel.locationMap[viewModel.selectedLocation.value]?.also { list ->
                    list.map { it?.let { RecyclerItem(GiftProductItemData(it)) } }
                }
            }
            if (list != null && list.isNotEmpty()) {
                DetailChooseDialogFragment.newInstance(
                    "انتخاب محصول",
                    list.map { RecyclerItem(GiftProductItemData(it!!)) }
                ).show(childFragmentManager, "course_choose_dialog")
            }
        }
    }

    private fun loading(show: Boolean) {
        if (show) {
            binding.frameLayout.visibility = View.VISIBLE
            binding.loading.isIndeterminate = true
            binding.loading.visibility = View.VISIBLE
        } else {
            binding.frameLayout.visibility = View.GONE
            binding.loading.isIndeterminate = false
            binding.loading.visibility = View.GONE

            binding.constraintLayout.isVisible = true
            binding.materialCardView.isVisible = true
            binding.cardViewViewPager.isVisible = true
//            binding.cardView7.isVisible = true
        }
    }

    class ViewPagerAdapter(
        fragmentManager: FragmentManager,
        lifecycle: Lifecycle,
        val movie: AcademyDto
    ) :
        FragmentStateAdapter(fragmentManager, lifecycle) {

        override fun getItemCount(): Int {
            return 2
        }

        override fun createFragment(position: Int): Fragment {
            return when (position) {
                0 -> PreRequisitesFragment.newInstance(movie.detail.preRequirement)
                1 -> LocationOrSupportFragment.newInstance(movie.courseDescription)
                else -> LocationOrSupportFragment.newInstance(movie.courseDescription)
            }
        }
    }

    private fun convertTimeToString(minute: Int): String {
        val hours = minute / 60
        val stringBuilder = StringBuilder()
        val formatter = Formatter(stringBuilder, Locale.getDefault())
        stringBuilder.setLength(0)
        return if (hours > 0) {
            convertToPersianNumber(
                formatter.format("%d:%02d:%02d", hours, minute % 60, 0).toString()
            )
        } else {
            convertToPersianNumber(formatter.format("%02d:%02d", minute % 60, 0).toString())
        }
    }
}