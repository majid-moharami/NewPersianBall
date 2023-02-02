package ir.pattern.persianball.presenter.feature.shopping.recycler

import android.view.View
import android.view.ViewGroup
import ir.pattern.persianball.presenter.adapter.BasePagingAdapter
import ir.pattern.persianball.presenter.adapter.BaseViewHolder
import ir.pattern.persianball.presenter.feature.store.recycler.StoreData
import ir.pattern.persianball.presenter.feature.store.recycler.StoreViewHolder

class ShoppingCartAdapter : BasePagingAdapter() {
    lateinit var onDeleteCartItemClickListener: BaseViewHolder.OnClickListener<ShopCartViewHolder, ShoppingCartData>
    lateinit var onAddCartItemClickListener: BaseViewHolder.OnClickListener<ShopCartViewHolder, ShoppingCartData>
    lateinit var onMinusCartItemClickListener: BaseViewHolder.OnClickListener<ShopCartViewHolder, ShoppingCartData>
    override fun getViewHolder(parent: ViewGroup, viewType: Int, view: View): BaseViewHolder<*>? {
        return when (viewType) {
            ShoppingCartData.VIEW_TYPE -> ShopCartViewHolder(view, onDeleteCartItemClickListener, onAddCartItemClickListener, onMinusCartItemClickListener)
            else -> null
        }
    }
}