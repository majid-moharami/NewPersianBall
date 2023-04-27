package ir.pattern.persianball.presenter.feature.academy.recycler

import android.R.attr.banner
import android.annotation.SuppressLint
import android.content.Intent
import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.navigation.findNavController
import com.example.moeidbannerlibrary.banner.BaseBannerAdapter
import com.google.firebase.firestore.util.Assert
import ir.pattern.persianball.R
import ir.pattern.persianball.data.model.base.Equatable
import ir.pattern.persianball.data.model.base.PersianBallRecyclerData
import ir.pattern.persianball.data.model.home.Slider
import ir.pattern.persianball.data.model.home.SliderList
import ir.pattern.persianball.databinding.HolderAcademySliderBinding
import ir.pattern.persianball.presenter.adapter.BaseViewHolder
import ir.pattern.persianball.presenter.feature.academy.AcademyFragmentDirections
import ir.pattern.persianball.presenter.feature.home.HomeFragmentDirections
import ir.pattern.persianball.presenter.feature.player.PlayerActivity
import java.util.*


class AcademySliderData constructor(var slider: List<Slider>) : PersianBallRecyclerData, Equatable {

    companion object {
        const val VIEW_TYPE = R.layout.holder_academy_slider
    }

    override val viewType: Int = VIEW_TYPE

    override fun equals(other: Any?): Boolean = true

    override fun getUniqueId(): String = UUID.randomUUID().toString()

    override fun hashCode(): Int {
        return slider.hashCode()
    }
}

class AcademySliderViewHolder(itemView: View) : BaseViewHolder<AcademySliderData>(itemView) {

    private lateinit var binding: HolderAcademySliderBinding

    @SuppressLint("RestrictedApi")
    override fun setViewDataBinding(viewDataBinding: ViewDataBinding?) {
        super.setViewDataBinding(viewDataBinding)
        when (viewDataBinding) {
            is HolderAcademySliderBinding -> binding = viewDataBinding
            else -> Assert.fail("binding is incompatible")
        }
    }

    override fun onBindView(data: AcademySliderData?) {
        val urls = data?.slider?.map {
            "https://api.persianball.ir/${it.image}"
        }
        val webBannerAdapter = BaseBannerAdapter(itemView.context, urls)
        webBannerAdapter.setOnBannerItemClickListener {
            data?.also { data ->
                for (i in 0 until data.slider.size - 1) {
                    if (i == it) {
                        when (data.slider[i].sliderType) {
                            "video" -> {
                                val intent = Intent(itemView.context, PlayerActivity::class.java)
                                intent.putExtra("HAVE_URL", true)
                                intent.putExtra("URL", data.slider[i].url)
                                itemView.context.startActivity(intent)
                            }

                            "course", "class" -> {
                                data.slider[i].let {
                                    it.uniqueId?.also { id ->
                                        itemView.findNavController().navigate(
                                            AcademyFragmentDirections.actionAcademyFragmentToMovieDetailFragment(
                                                id
                                            )
                                        )
                                    }
                                }
                            }

                            "product" -> {
                                data.slider[i].let {
                                    it.uniqueId?.also { id ->
                                        itemView.findNavController().navigate(
                                            HomeFragmentDirections.actionHomeFragmentToProductDetailFragment(
                                                id
                                            )
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        binding.Banner.setAdapter(webBannerAdapter)
    }
}