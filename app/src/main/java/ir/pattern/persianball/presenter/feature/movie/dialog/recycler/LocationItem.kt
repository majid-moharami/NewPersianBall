package ir.pattern.persianball.presenter.feature.movie.dialog.recycler

import android.annotation.SuppressLint
import android.view.View
import androidx.databinding.ViewDataBinding
import com.bumptech.glide.Glide
import com.google.firebase.firestore.util.Assert
import ir.pattern.persianball.R
import ir.pattern.persianball.data.model.academy.TimeAndLocationsDto
import ir.pattern.persianball.data.model.base.Equatable
import ir.pattern.persianball.data.model.base.PersianBallRecyclerData
import ir.pattern.persianball.databinding.HolderLocationBinding
import ir.pattern.persianball.presenter.adapter.BaseViewHolder
import java.util.*

class LocationItemData(val timeAndLocationsDto: TimeAndLocationsDto) : PersianBallRecyclerData,
    Equatable {

    companion object {
        const val VIEW_TYPE = R.layout.holder_location
    }

    override val viewType: Int = VIEW_TYPE

    override fun equals(other: Any?): Boolean = true

    override fun getUniqueId(): String = UUID.randomUUID().toString()

    override fun hashCode(): Int {
        return javaClass.hashCode()
    }
}

class LocationItemViewHolder(
    itemView: View,
    private val onLocationItemListener: BaseViewHolder.OnClickListener<LocationItemViewHolder, LocationItemData>?
) : BaseViewHolder<LocationItemData>(itemView) {

    lateinit var binding: HolderLocationBinding

    @SuppressLint("RestrictedApi")
    override fun setViewDataBinding(viewDataBinding: ViewDataBinding?) {
        super.setViewDataBinding(viewDataBinding)
        when (viewDataBinding) {
            is HolderLocationBinding -> binding = viewDataBinding
            else -> Assert.fail("binding is incompatible")
        }
    }

    override fun onBindView(data: LocationItemData) {
        Glide.with(itemView.context).load("https://api.persianball.ir/${data.timeAndLocationsDto.image}").into(binding.image)
        binding.location.text = data.timeAndLocationsDto.location
        binding.time.text = data.timeAndLocationsDto.time
        setOnClickListener(binding.clickableLayout, onLocationItemListener, this, data)
    }

}