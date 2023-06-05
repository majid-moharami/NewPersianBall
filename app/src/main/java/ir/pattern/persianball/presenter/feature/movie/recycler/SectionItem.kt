package ir.pattern.persianball.presenter.feature.movie.recycler

import android.annotation.SuppressLint
import android.view.View
import androidx.core.view.isVisible
import androidx.databinding.ViewDataBinding
import com.bumptech.glide.Glide
import com.google.firebase.firestore.util.Assert
import ir.pattern.persianball.R
import ir.pattern.persianball.data.model.academy.SectionDto
import ir.pattern.persianball.data.model.base.Equatable
import ir.pattern.persianball.data.model.base.PersianBallRecyclerData
import ir.pattern.persianball.databinding.HolderCourseSectionItemBinding
import ir.pattern.persianball.databinding.HolderCourseWeekItemBinding
import ir.pattern.persianball.presenter.adapter.BaseViewHolder
import java.util.*

class SectionItemData(val headerId: String, var section: SectionDto) : PersianBallRecyclerData, Equatable {
    companion object {
        const val VIEW_TYPE = R.layout.holder_course_section_item
    }

    override val viewType: Int = VIEW_TYPE

    override fun equals(other: Any?): Boolean = true

    override fun getUniqueId(): String = UUID.randomUUID().toString()

    override fun hashCode(): Int {
        var result = section.hashCode()
        result = 31 * result + viewType
        return result
    }
}

class SectionItemViewHolder(
    itemView: View,
    private val onMovieClickListener: BaseViewHolder.OnClickListener<SectionItemViewHolder, SectionItemData>?,
    private val onSendClickListener: BaseViewHolder.OnClickListener<SectionItemViewHolder, SectionItemData>?
) : BaseViewHolder<SectionItemData>(itemView) {
    private lateinit var binding: HolderCourseSectionItemBinding

    @SuppressLint("RestrictedApi")
    override fun setViewDataBinding(viewDataBinding: ViewDataBinding?) {
        super.setViewDataBinding(viewDataBinding)
        when (viewDataBinding) {
            is HolderCourseSectionItemBinding -> binding = viewDataBinding
            else -> Assert.fail("binding is incompatible")
        }
    }


    override fun onBindView(data: SectionItemData) {
        Glide.with(itemView.context).load(data.section.videoThumbnail).into(binding.sectionPoster)
        binding.lock.isVisible = data.section.isLocked
        binding.title.text = data.section.title
        setOnClickListener(binding.posterLayout, onMovieClickListener, this, data)
    }
}