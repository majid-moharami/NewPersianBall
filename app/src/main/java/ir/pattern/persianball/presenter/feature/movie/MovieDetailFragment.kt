package ir.pattern.persianball.presenter.feature.movie

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import ir.pattern.persianball.R
import ir.pattern.persianball.data.model.academy.Academy
import ir.pattern.persianball.data.model.academy.AcademyDto
import ir.pattern.persianball.data.model.academy.MovieDetailDto
import ir.pattern.persianball.databinding.FragmentAcademyBinding
import ir.pattern.persianball.databinding.FragmentMovieDetailBinding

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
        initView()
        return binding.root
    }

    @SuppressLint("StringFormatMatches")
    private fun initView(){
        binding.viewpager.setCurrentItem(2, false)
        binding.headerTitle.text = movie.courseTitle
        Glide.with(this).load("https://api.persianball.ir/${movie.image}").into(binding.poster)
        binding.videoTime.text = resources.getString(R.string.course_duration, movie.courseDuration)
        binding.videoCount.text = resources.getString(R.string.video_count, movie.section_count)
    }

}