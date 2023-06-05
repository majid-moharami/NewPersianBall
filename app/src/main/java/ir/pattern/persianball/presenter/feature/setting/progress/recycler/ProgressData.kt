package ir.pattern.persianball.presenter.feature.setting.progress.recycler

import android.annotation.SuppressLint
import android.view.View
import androidx.databinding.ViewDataBinding
import calendar.CivilDate
import calendar.DateConverter
import com.google.firebase.firestore.util.Assert
import ir.pattern.persianball.R
import ir.pattern.persianball.data.model.base.Equatable
import ir.pattern.persianball.data.model.base.PersianBallRecyclerData
import ir.pattern.persianball.data.model.dashboard.OrderDto
import ir.pattern.persianball.data.model.profile.Address
import ir.pattern.persianball.data.model.profile.AddressDto
import ir.pattern.persianball.databinding.HolderOrderOrderBinding
import ir.pattern.persianball.presenter.adapter.BaseViewHolder
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.text.SimpleDateFormat
import java.util.*

class ProgressData(val orderDto: OrderDto, val address: List<Address?>?) : PersianBallRecyclerData,
    Equatable {

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

    val decimalForm =
        DecimalFormat("#,###", DecimalFormatSymbols.getInstance(Locale.US).apply {
            groupingSeparator = ','
        })

    override fun onBindView(data: ProgressData) {
        data.address?.map {
            if (it?.id == data.orderDto.address.toLong()) {
                binding.address.text = itemView.resources.getString(
                    R.string.address_order,
                    it.address
                )
            }
        }
        data.orderDto.also {
            binding.date.text = itemView.resources.getString(
                R.string.date_order,
                getPersianDate(it.createdAt)
            )
            binding.status.text = when (it.orderStatus) {
                "pending" -> {
                    binding.status.setTextColor(itemView.resources.getColor(R.color.yellow))
                    "در حال پردازش"
                }
                "done" -> {
                    binding.status.setTextColor(itemView.resources.getColor(R.color.green))
                    "ارسال شد"
                }
                else -> {
                    binding.status.setTextColor(itemView.resources.getColor(R.color.red))
                    "لغو شد"
                }
            }
            binding.tracking.text = when (it.deliveryMethod) {
                "post" -> "روش ارسال: پست"
                "peyk" -> "روش ارسال: پیک"
                else -> "روش ارسال: رایگان"
            }
            binding.price.text = itemView.resources.getString(
                R.string.price_order,
                decimalForm.format(it.price.price)
            )
        }
    }

    fun getPersianDate(date: String?): String {
        if (date.isNullOrEmpty()) {
            return ""
        } else {
            val format = SimpleDateFormat("yyyy-MM-dd")
            val date: Date = format.parse(date)
            val c = Calendar.getInstance()
            c.time = date
            val persianDate = DateConverter.civilToPersian(CivilDate(c))
            return "${persianDate.year}/${persianDate.month}/${persianDate.dayOfMonth}"
        }
    }

}