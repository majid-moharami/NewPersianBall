package ir.pattern.persianball.presenter.feature.store.recycler

import android.annotation.SuppressLint
import android.view.View
import androidx.databinding.ViewDataBinding
import com.google.firebase.firestore.util.Assert
import ir.pattern.persianball.R
import ir.pattern.persianball.data.model.base.Equatable
import ir.pattern.persianball.data.model.base.PersianBallRecyclerData
import ir.pattern.persianball.databinding.HolderStoreFilterBinding
import ir.pattern.persianball.presenter.adapter.BaseViewHolder

class FilterData(): PersianBallRecyclerData, Equatable {
    companion object {
        const val VIEW_TYPE = R.layout.holder_store_filter
    }

    override val viewType: Int = VIEW_TYPE

    override fun equals(other: Any?): Boolean = true

    override fun hashCode(): Int = javaClass.hashCode()
}

class FilterViewHolder(
    itemView: View,
    private val onCoursesClickListener: OnClickListener<FilterViewHolder, FilterData>?,
    private val onProductClickListener: OnClickListener<FilterViewHolder, FilterData>?,
    private val onClassesClickListener: OnClickListener<FilterViewHolder, FilterData>?
) : BaseViewHolder<FilterData>(itemView) {

    lateinit var binding: HolderStoreFilterBinding

    @SuppressLint("RestrictedApi")
    override fun setViewDataBinding(viewDataBinding: ViewDataBinding?) {
        super.setViewDataBinding(viewDataBinding)
        when (viewDataBinding) {
            is HolderStoreFilterBinding -> binding = viewDataBinding
            else -> Assert.fail("binding is incompatible")
        }
    }
    override fun onBindView(data: FilterData?) {
        setOnClickListener(binding.courses, onCoursesClickListener, this, data)
        setOnClickListener(binding.products, onProductClickListener, this, data)
        setOnClickListener(binding.classes, onClassesClickListener, this, data)
    }

}