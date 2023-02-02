package ir.pattern.persianball.presenter.feature.shopping.recycler

import android.annotation.SuppressLint
import android.view.View
import androidx.databinding.ViewDataBinding
import com.bumptech.glide.Glide
import com.google.firebase.firestore.util.Assert
import ir.pattern.persianball.R
import ir.pattern.persianball.data.model.base.Equatable
import ir.pattern.persianball.data.model.base.PersianBallRecyclerData
import ir.pattern.persianball.data.model.shoppingCart.ShoppingCartItemDto
import ir.pattern.persianball.databinding.HolderShopCartItemBinding
import ir.pattern.persianball.presenter.adapter.BaseViewHolder
import ir.pattern.persianball.presenter.feature.store.recycler.StoreData
import ir.pattern.persianball.presenter.feature.store.recycler.StoreViewHolder

class ShoppingCartData(val shoppingCartItemDto: ShoppingCartItemDto) : PersianBallRecyclerData, Equatable {

    companion object {
        const val VIEW_TYPE = R.layout.holder_shop_cart_item
    }

    override val viewType: Int = VIEW_TYPE

    override fun equals(other: Any?): Boolean = true

    override fun hashCode(): Int = javaClass.hashCode()
}

class ShopCartViewHolder(
    itemView: View,
    private val onDeleteCartItemClickListener: OnClickListener<ShopCartViewHolder, ShoppingCartData>?,
    private val onAddCartItemClickListener: OnClickListener<ShopCartViewHolder, ShoppingCartData>?,
    private val onMinusCartItemClickListener: OnClickListener<ShopCartViewHolder, ShoppingCartData>?,
): BaseViewHolder<ShoppingCartData>(itemView){

    lateinit var binding: HolderShopCartItemBinding

    @SuppressLint("RestrictedApi")
    override fun setViewDataBinding(viewDataBinding: ViewDataBinding?) {
        super.setViewDataBinding(viewDataBinding)
        when (viewDataBinding) {
            is HolderShopCartItemBinding -> binding = viewDataBinding
            else -> Assert.fail("binding is incompatible")
        }
    }

    override fun onBindView(data: ShoppingCartData?) {
        data?.shoppingCartItemDto?.also {
            if (it.product != null){
                binding.countLayout.visibility = View.VISIBLE
                binding.time.visibility = View.GONE
                binding.language.visibility = View.GONE
                Glide.with(itemView).load("https://api.persianball.ir/${it.product?.image}").into(binding.image)
                binding.price.text = itemView.resources.getString(R.string.product_price, it.product?.price)
                binding.count.text = it.product?.count.toString()
                binding.title.text = it.product?.productName
            }else{
                binding.countLayout.visibility = View.GONE
                binding.time.visibility = View.VISIBLE
                binding.language.visibility = View.VISIBLE
                Glide.with(itemView).load("https://api.persianball.ir/${it.course?.image}").into(binding.image)
                binding.title.text = it.course?.courseName
                binding.price.text = itemView.resources.getString(R.string.product_price, it.course?.price)
                binding.time.text = it.course?.time
                binding.language.text = it.course?.location
            }
        }
        setOnClickListener(binding.trash, onDeleteCartItemClickListener, this, data)
        setOnClickListener(binding.add, onAddCartItemClickListener, this, data)
        setOnClickListener(binding.minus, onMinusCartItemClickListener, this, data)
    }
}