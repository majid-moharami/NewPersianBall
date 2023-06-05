package ir.pattern.persianball.presenter.feature.setting.progress.recycler

import android.annotation.SuppressLint
import android.view.View
import androidx.databinding.ViewDataBinding
import com.google.firebase.firestore.util.Assert
import ir.pattern.persianball.R
import ir.pattern.persianball.data.model.base.Equatable
import ir.pattern.persianball.data.model.base.PersianBallRecyclerData
import ir.pattern.persianball.data.model.dashboard.OrderDto
import ir.pattern.persianball.databinding.HolderOrderOrderBinding
import ir.pattern.persianball.presenter.adapter.BaseViewHolder
import java.util.*

class ProgressData(val orderDto: OrderDto) : PersianBallRecyclerData, Equatable {

    companion object {
        const val VIEW_TYPE = R.layout.holder_order_order
    }

    override val viewType: Int = VIEW_TYPE

    override fun equals(other: Any?): Boolean = true

    override fun getUniqueId(): String = UUID.randomUUID().toString()

    override fun hashCode(): Int {
        return javaClass.hashCode()
    }
}

class ProgressViewHolder(
    itemView: View
) : BaseViewHolder<ProgressData>(itemView) {
    private lateinit var binding: HolderOrderOrderBinding

    @SuppressLint("RestrictedApi")
    override fun setViewDataBinding(viewDataBinding: ViewDataBinding?) {
        super.setViewDataBinding(viewDataBinding)
        when (viewDataBinding) {
            is HolderOrderOrderBinding -> binding = viewDataBinding
            else -> Assert.fail("binding is incompatible")
        }
    }

    override fun onBindView(data: ProgressData) {
        data.orderDto.also {
            binding.date.text = it.createdAt
            binding.address.text =  "${it.address}آدرس: "
            binding.status.text = it.orderStatus
            binding.status.setTextColor(itemView.resources.getColor(R.color.green))
            binding.tracking.text = it.deliveryMethod
            binding.price.text = (it.price.price + it.price.shippingPrice + it.price.nat).toString()
        }
    }

}