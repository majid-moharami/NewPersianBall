package ir.pattern.persianball.presenter.feature.movie.recycler

import android.annotation.SuppressLint
import android.view.View
import androidx.databinding.ViewDataBinding
import com.google.firebase.firestore.util.Assert
import ir.pattern.persianball.R
import ir.pattern.persianball.data.model.academy.SectionsDto
import ir.pattern.persianball.data.model.base.Equatable
import ir.pattern.persianball.data.model.base.PersianBallRecyclerData
import ir.pattern.persianball.databinding.HolderCourseWeekItemBinding
import ir.pattern.persianball.presenter.adapter.BaseViewHolder
import java.util.UUID

class SectionHeaderData(var isOpen: Boolean, var sections: SectionsDto?) : PersianBallRecyclerData,
    Equatable {

    val id : String = UUID.randomUUID().toString()

    companion object {
        const val VIEW_TYPE = R.layout.holder_course_week_item
    }

    override val viewType: Int = VIEW_TYPE

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as SectionHeaderData
        if (isOpen != other.isOpen) return false
        return true
    }

    override fun getUniqueId(): String = id

    override fun hashCode(): Int {
        var result = sections.hashCode()
        result = 31 * result + viewType
        return result
    }
}

class SectionHeaderViewHolder(
    itemView: View,
    private val onSectionHeaderClickListener: OnClickListener<SectionHeaderViewHolder, SectionHeaderData>?
) : BaseViewHolder<SectionHeaderData>(itemView) {

    lateinit var binding: HolderCourseWeekItemBinding

    @SuppressLint("RestrictedApi")
    override fun setViewDataBinding(viewDataBinding: ViewDataBinding?) {
        super.setViewDataBinding(viewDataBinding)
        when (viewDataBinding) {
            is HolderCourseWeekItemBinding -> binding = viewDataBinding
            else -> Assert.fail("binding is incompatible")
        }
    }

    override fun onBindView(data: SectionHeaderData) {
        binding.week.text = itemView.resources.getString(R.string.week, data.sections?.weekNumber)
        binding.sectionCount.text = itemView.resources.getString(
            R.string.section_count,
            data.sections?.sections?.size.toString()
        )
        binding.arrow.setImageDrawable(itemView.resources.getDrawable(if (data.isOpen) R.drawable.ic_up else R.drawable.ic_down))
        setOnClickListener(binding.clickableLayout, onSectionHeaderClickListener, this, data)
    }
}


