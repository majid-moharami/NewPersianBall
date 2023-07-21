package ir.pattern.persianball.presenter.feature.academy.recycler

import android.R.attr.banner
import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.navigation.findNavController
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.interfaces.ItemClickListener
import com.denzcoskun.imageslider.models.SlideModel
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

    override fun onBindView(data: AcademySliderData) {
        val imageList = mutableListOf<SlideModel>()
        data.slider.map {
            if (it.category == 2) {
                imageList.add(
                    SlideModel(
                        "https://api.persianball.ir/${it.image}",
                        ScaleTypes.CENTER_CROP
                    )
                )
            }
        }
        binding.imageSlider.setImageList(imageList)
        binding.imageSlider.setItemClickListener(object : ItemClickListener {
            override fun onItemSelected(position: Int) {
                for (i in 0 until data.slider.size - 1) {
                    if (i == position) {
                        when (data.slider[i].sliderType) {
                            "video" -> {
                                val intent = Intent(itemView.context, PlayerActivity::class.java)
                                intent.putExtra("HAVE_URL", true)
                                intent.putExtra("URL", data.slider[i].url)
                                itemView.context.startActivity(intent)
                            }

                            "course" -> {
                                data.slider[i].let {
                                    it.uniqueId?.also { id ->
                                        itemView.findNavController().navigate(
                                            HomeFragmentDirections.actionHomeFragmentToMovieDetailFragment(
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

                            "url" -> {
                                data.slider[i].let {
                                    val url = it.url
                                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                                    itemView.context.startActivity(intent)
                                }
                            }
                        }
                    }
                }

            }
        })
    }
}