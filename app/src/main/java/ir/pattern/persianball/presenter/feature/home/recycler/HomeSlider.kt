package ir.pattern.persianball.presenter.feature.home.recycler

import android.annotation.SuppressLint
import android.view.View
import androidx.databinding.ViewDataBinding
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.google.firebase.firestore.util.Assert
import ir.pattern.persianball.R
import ir.pattern.persianball.data.model.base.Equatable
import ir.pattern.persianball.data.model.base.PersianBallRecyclerData
import ir.pattern.persianball.data.model.home.Slide
import ir.pattern.persianball.databinding.HolderHomeSliderBinding
import ir.pattern.persianball.presenter.adapter.BaseViewHolder

class HomeSliderData constructor(var slide: Slide) : PersianBallRecyclerData, Equatable{
    companion object {
        const val VIEW_TYPE = R.layout.holder_home_slider
    }
    override val viewType: Int = VIEW_TYPE
    override fun equals(other: Any?): Boolean {
        TODO("Not yet implemented")
    }

    override fun hashCode(): Int {
        return viewType
    }

}


class HomeSliderViewHolder(itemView: View) : BaseViewHolder<HomeSliderData>(itemView){

    private lateinit var binding: HolderHomeSliderBinding

    @SuppressLint("RestrictedApi")
    override fun setViewDataBinding(viewDataBinding: ViewDataBinding?) {
        super.setViewDataBinding(viewDataBinding)
        when (viewDataBinding) {
            is HolderHomeSliderBinding -> binding = viewDataBinding
            else -> Assert.fail("binding is incompatible")
        }
    }


    override fun onBindView(data: HomeSliderData?) {
        val imageList = mutableListOf<SlideModel>()
        imageList.add(SlideModel(R.drawable.slider, ScaleTypes.FIT))
        imageList.add(SlideModel(R.drawable.slider, ScaleTypes.FIT))
        imageList.add(SlideModel(R.drawable.slider, ScaleTypes.FIT))
        imageList.add(SlideModel(R.drawable.slider, ScaleTypes.FIT))
        imageList.add(SlideModel(R.drawable.slider, ScaleTypes.FIT))
        binding.imageSlider.setImageList(imageList)
    }
}

