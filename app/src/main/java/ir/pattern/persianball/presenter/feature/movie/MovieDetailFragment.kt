package ir.pattern.persianball.presenter.feature.movie

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import ir.pattern.persianball.R
import ir.pattern.persianball.data.model.academy.Academy
import ir.pattern.persianball.data.model.academy.AcademyDto
import ir.pattern.persianball.data.model.academy.MovieDetailDto
import ir.pattern.persianball.databinding.FragmentAcademyBinding
import ir.pattern.persianball.databinding.FragmentMovieDetailBinding
import ir.pattern.persianball.presenter.feature.movie.courses.LessonsFragment
import ir.pattern.persianball.presenter.feature.movie.locationOrsupports.LocationOrSupportFragment
import ir.pattern.persianball.presenter.feature.movie.prerequisites.PreRequisitesFragment
import ir.pattern.persianball.presenter.feature.setting.DashboardFragment
import ir.pattern.persianball.presenter.feature.setting.progress.ProgressFragment
import ir.pattern.persianball.presenter.feature.setting.registered.RegisteredCoursesFragment

@AndroidEntryPoint
class MovieDetailFragment : Fragment() {

    lateinit var binding: FragmentMovieDetailBinding
    lateinit var movie: AcademyDto
    private lateinit var args : MovieDetailFragmentArgs

    override fun onAttach(context: Context) {
        super.onAttach(context)
        args = MovieDetailFragmentArgs.fromBundle(requireArguments())
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        movie = args.academy
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(activity),
            R.layout.fragment_movie_detail,
            container,
            false
        )
        val adapter = ViewPagerAdapter(childFragmentManager, lifecycle)
        binding.viewpager.adapter = adapter
        binding.viewpager.setCurrentItem(2, false)
        TabLayoutMediator(binding.tabLayout, binding.viewpager) { tab, position ->
            when (position) {
                0 -> tab.text = resources.getString(R.string.pre_requisites)
                1 -> tab.text = resources.getString(R.string.description)
            }
        }.attach()
        initView()
        return binding.root
    }

    @SuppressLint("StringFormatMatches")
    private fun initView(){
        binding.headerTitle.text = movie.courseTitle
        Glide.with(this).load("https://api.persianball.ir/${movie.image}").into(binding.poster)
        binding.videoTime.text = resources.getString(R.string.course_duration, movie.courseDuration)
        binding.videoCount.text = resources.getString(R.string.video_count, movie.section_count)
    }

    class ViewPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
        FragmentStateAdapter(fragmentManager, lifecycle) {

        override fun getItemCount(): Int {
            return 2
        }

        override fun createFragment(position: Int): Fragment {
            return when (position) {
                0 -> PreRequisitesFragment.newInstance()
                1 -> LocationOrSupportFragment.newInstance()
                else -> LocationOrSupportFragment.newInstance()
            }
        }
    }

}