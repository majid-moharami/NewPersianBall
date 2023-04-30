package ir.pattern.persianball.presenter.feature.shopping.address.recycler

import android.annotation.SuppressLint
import android.view.View
import androidx.databinding.ViewDataBinding
import com.google.firebase.firestore.util.Assert
import ir.pattern.persianball.R
import ir.pattern.persianball.data.model.address.AddressDto
import ir.pattern.persianball.data.model.base.Equatable
import ir.pattern.persianball.data.model.base.PersianBallRecyclerData
import ir.pattern.persianball.data.model.shoppingCart.ShoppingCartItemDto
import ir.pattern.persianball.databinding.HolderAddressSubmitBinding
import ir.pattern.persianball.databinding.HolderShopCartItemBinding
import ir.pattern.persianball.presenter.adapter.BaseViewHolder
import ir.pattern.persianball.presenter.feature.shopping.recycler.ShopCartViewHolder
import ir.pattern.persianball.presenter.feature.shopping.recycler.ShoppingCartData
import java.util.*

class OrderAddressData(val addressDto: AddressDto, var isDefault: Boolean) :
    PersianBallRecyclerData, Equatable {
    companion object {
        const val VIEW_TYPE = R.layout.holder_address_submit
    }

    override val viewType: Int = VIEW_TYPE

    override fun equals(other: Any?): Boolean = false

    override fun getUniqueId(): String = UUID.randomUUID().toString()

    override fun hashCode(): Int = javaClass.hashCode()
}

class OrderAddressViewHolder(
    itemView: View,
    private val onClickListener: OnClickListener<OrderAddressViewHolder, OrderAddressData>?
) : BaseViewHolder<OrderAddressData>(itemView) {

    lateinit var binding: HolderAddressSubmitBinding

    @SuppressLint("RestrictedApi")
    override fun setViewDataBinding(viewDataBinding: ViewDataBinding?) {
        super.setViewDataBinding(viewDataBinding)
        when (viewDataBinding) {
            is HolderAddressSubmitBinding -> binding = viewDataBinding
            else -> Assert.fail("binding is incompatible")
        }
    }

    override fun onBindView(data: OrderAddressData?) {
        data?.isDefault.also {
            if (it == true) {
                binding.defaultTextLayout.visibility = View.VISIBLE
                binding.baseLayout.background =
                    itemView.resources.getDrawable(R.drawable.selected_address_back)
                binding.cardLayout.background =
                    itemView.resources.getDrawable(R.drawable.background_address_default)
            } else if (it == false){
                binding.defaultTextLayout.visibility = View.INVISIBLE
                binding.baseLayout.background =
                    itemView.resources.getDrawable(R.drawable.unselected_address_back)
                binding.cardLayout.background =
                    itemView.resources.getDrawable(R.drawable.backgraound_address_other)
            }
        }
        data?.addressDto?.also {
            binding.addressName.text = it.addressName
            binding.name.text = it.userFullName
            binding.number.text = it.receiverMobilePhone
            binding.address.text = "${it.city} ${it.province} ${it.address} \n" + itemView.resources.getString(R.string.postal_code, it.postalCode)
        }
        setOnClickListener(binding.baseLayout, onClickListener, this, data)
    }
}