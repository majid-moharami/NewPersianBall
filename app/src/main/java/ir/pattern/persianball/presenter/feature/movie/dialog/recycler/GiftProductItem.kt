package ir.pattern.persianball.presenter.feature.movie.dialog.recycler

import android.annotation.SuppressLint
import android.view.View
import androidx.databinding.ViewDataBinding
import com.bumptech.glide.Glide
import com.google.firebase.firestore.util.Assert
import ir.pattern.persianball.R
import ir.pattern.persianball.data.model.academy.GiftProductDto
import ir.pattern.persianball.data.model.base.Equatable
import ir.pattern.persianball.data.model.base.PersianBallRecyclerData
import ir.pattern.persianball.databinding.HolderChooseCoachBinding
import ir.pattern.persianball.databinding.HolderChooseGiftBinding
import ir.pattern.persianball.presenter.adapter.BaseViewHolder
import java.util.*

class GiftProductItemData(val giftProductDto: GiftProductDto) : PersianBallRecyclerData,
    Equatable {

    companion object {
        const val VIEW_TYPE = R.layout.holder_choose_gift
    }

    override val viewType: Int = VIEW_TYPE

    override fun equals(other: Any?): Boolean = true

    override fun getUniqueId(): String = UUID.randomUUID().toString()

    override fun hashCode(): Int {
        return giftProductDto.hashCode()
    }
}

class GiftProductItemViewHolder(
    itemView: View,
    private val onGiftItemListener: BaseViewHolder.OnClickListener<GiftProductItemViewHolder, GiftProductItemData>?,
) : BaseViewHolder<GiftProductItemData>(itemView) {

    lateinit var binding: HolderChooseGiftBinding

    @SuppressLint("RestrictedApi")
    override fun setViewDataBinding(viewDataBinding: ViewDataBinding?) {
        super.setViewDataBinding(viewDataBinding)
        when (viewDataBinding) {
            is HolderChooseGiftBinding -> binding = viewDataBinding
            else -> Assert.fail("binding is incompatible")
        }
    }

    override fun onBindView(data: GiftProductItemData) {
        Glide.with(itemView).load("https://api.persianball.ir/${data.giftProductDto.image}").into(binding.image)
        binding.name.text = data.giftProductDto.productName
        setOnClickListener(binding.clickableLayout, onGiftItemListener, this, data)
    }

}