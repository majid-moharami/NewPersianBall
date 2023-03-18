package ir.pattern.persianball.presenter.feature.store.recycler

import android.annotation.SuppressLint
import android.view.View
import androidx.databinding.ViewDataBinding
import com.google.firebase.firestore.util.Assert
import ir.pattern.persianball.R
import ir.pattern.persianball.data.model.base.Equatable
import ir.pattern.persianball.data.model.base.PersianBallRecyclerData
import ir.pattern.persianball.databinding.HolderStoreSearchBinding
import ir.pattern.persianball.presenter.adapter.BaseViewHolder

class SearchData(): PersianBallRecyclerData, Equatable {
    companion object {
        const val VIEW_TYPE = R.layout.holder_store_search
    }

    override val viewType: Int = VIEW_TYPE

    override fun equals(other: Any?): Boolean = true

    override fun hashCode(): Int = javaClass.hashCode()
}

class SearchViewHolder(
    itemView: View,
    private val onSearchClickListener: OnClickListener<SearchViewHolder, SearchData>?
) : BaseViewHolder<SearchData>(itemView) {

    lateinit var binding: HolderStoreSearchBinding

    @SuppressLint("RestrictedApi")
    override fun setViewDataBinding(viewDataBinding: ViewDataBinding?) {
        super.setViewDataBinding(viewDataBinding)
        when (viewDataBinding) {
            is HolderStoreSearchBinding -> binding = viewDataBinding
            else -> Assert.fail("binding is incompatible")
        }
    }
    override fun onBindView(data: SearchData?) {
        setOnClickListener(binding.search, onSearchClickListener, this, data)
    }

}