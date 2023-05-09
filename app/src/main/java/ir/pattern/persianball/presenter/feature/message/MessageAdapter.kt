package ir.pattern.persianball.presenter.feature.message

import android.view.View
import android.view.ViewGroup
import ir.pattern.persianball.presenter.adapter.BasePagingAdapter
import ir.pattern.persianball.presenter.adapter.BaseViewHolder
import ir.pattern.persianball.presenter.feature.message.recycler.MessageData
import ir.pattern.persianball.presenter.feature.message.recycler.MessageDataViewHolder

class MessageAdapter : BasePagingAdapter() {

    override fun getViewHolder(parent: ViewGroup, viewType: Int, view: View): BaseViewHolder<*>? {
        return when (viewType) {
            MessageData.VIEW_TYPE -> MessageDataViewHolder(view)
            else -> null
        }
    }
}