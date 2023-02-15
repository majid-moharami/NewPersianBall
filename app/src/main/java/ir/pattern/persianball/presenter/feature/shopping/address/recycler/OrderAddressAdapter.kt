package ir.pattern.persianball.presenter.feature.shopping.address.recycler

import android.view.View
import android.view.ViewGroup
import ir.pattern.persianball.presenter.adapter.BasePagingAdapter
import ir.pattern.persianball.presenter.adapter.BaseViewHolder
import ir.pattern.persianball.presenter.adapter.BaseViewHolder.OnClickListener

class OrderAddressAdapter : BasePagingAdapter() {
    lateinit var onClickListener: OnClickListener<OrderAddressViewHolder, OrderAddressData>
    override fun getViewHolder(parent: ViewGroup, viewType: Int, view: View): BaseViewHolder<*>? {
        return when (viewType) {
            OrderAddressData.VIEW_TYPE -> OrderAddressViewHolder(view, onClickListener)
            else -> null
        }
    }
}