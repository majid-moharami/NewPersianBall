package ir.pattern.persianball.presenter.feature.home.recycler

import android.annotation.SuppressLint
import android.content.Intent
import android.view.View
import androidx.core.content.ContextCompat.startActivity
import androidx.databinding.ViewDataBinding
import androidx.navigation.findNavController
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.interfaces.ItemClickListener
import com.denzcoskun.imageslider.models.SlideModel
import com.google.firebase.firestore.util.Assert
import ir.pattern.persianball.R
import ir.pattern.persianball.data.model.base.Equatable
import ir.pattern.persianball.data.model.base.PersianBallRecyclerData
import ir.pattern.persianball.data.model.home.Slider
import ir.pattern.persianball.data.model.home.SliderList
import ir.pattern.persianball.databinding.HolderHomeSliderBinding
import ir.pattern.persianball.presenter.adapter.BaseViewHolder
import ir.pattern.persianball.presenter.feature.home.HomeFragmentDirections
import ir.pattern.persianball.presenter.feature.player.PlayerActivity
import java.util.*

class HomeSliderData constructor(var slider: SliderList) : PersianBallRecyclerData, Equatable {
    companion object {
        const val VIEW_TYPE = R.layout.holder_home_slider
    }

    override val viewType: Int = VIEW_TYPE
    override fun equals(other: Any?): Boolean {
        TODO("Not yet implemented")
    }

    override fun getUniqueId(): String = UUID.randomUUID().toString()

    override fun hashCode(): Int {
        return viewType
    }

}


class HomeSliderViewHolder(itemView: View) : BaseViewHolder<HomeSliderData>(itemView) {

    private lateinit var binding: HolderHomeSliderBinding

    @SuppressLint("RestrictedApi")
    override fun setViewDataBinding(viewDataBinding: ViewDataBinding?) {
        super.setViewDataBinding(viewDataBinding)
        when (viewDataBinding) {
            is HolderHomeSliderBinding -> binding = viewDataBinding
            else -> Assert.fail("binding is incompatible")
        }
    }

    override fun onBindView(data: HomeSliderData) {
        val imageList = mutableListOf<SlideModel>()
        data.slider.slider.map {
            imageList.add(
                SlideModel(
                    "https://api.persianball.ir/${it.image}",
                    ScaleTypes.CENTER_CROP
                )
            )
        }
        binding.imageSlider.setImageList(imageList)
        binding.imageSlider.setItemClickListener(object : ItemClickListener {
            override fun onItemSelected(position: Int) {
                for (i in 0 until data.slider.slider.size - 1) {
                    if (i == position) {
                        when (data.slider.slider[i].sliderType) {
                            "video" -> {
                                val intent = Intent(itemView.context, PlayerActivity::class.java)
                                intent.putExtra("HAVE_URL", true)
                                intent.putExtra("URL", data.slider.slider[i].url)
                                itemView.context.startActivity(intent)
                            }

                            "course" -> {
                                data.slider.slider[i].let {
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
                                data.slider.slider[i].let {
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
        })
    }
}

