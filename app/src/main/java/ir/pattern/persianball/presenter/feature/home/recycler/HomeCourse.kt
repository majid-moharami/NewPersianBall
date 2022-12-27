package ir.pattern.persianball.presenter.feature.home.recycler

import android.annotation.SuppressLint
import android.view.View
import android.widget.LinearLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.ViewDataBinding
import com.bumptech.glide.Glide
import com.google.firebase.firestore.util.Assert
import ir.pattern.persianball.R
import ir.pattern.persianball.data.model.academy.AcademyDto
import ir.pattern.persianball.data.model.base.Equatable
import ir.pattern.persianball.data.model.base.PersianBallRecyclerData
import ir.pattern.persianball.data.model.home.Course
import ir.pattern.persianball.databinding.HolderHomeSliderBinding
import ir.pattern.persianball.databinding.HomeNewCourseViewholderBinding
import ir.pattern.persianball.presenter.adapter.BaseViewHolder

class HomeCourseData(var academy: AcademyDto) : PersianBallRecyclerData, Equatable {
    companion object {
        const val VIEW_TYPE = R.layout.home_new_course_viewholder
    }

    override val viewType: Int = VIEW_TYPE

    override fun equals(other: Any?): Boolean = true

    override fun hashCode(): Int {
        return javaClass.hashCode()
    }
}

class HomeCourseViewHolder(
    itemView: View,
    private val onCourseClickListener: OnClickListener<HomeCourseViewHolder, HomeCourseData>?
) : BaseViewHolder<HomeCourseData>(itemView) {
    private lateinit var binding: HomeNewCourseViewholderBinding

    @SuppressLint("RestrictedApi")
    override fun setViewDataBinding(viewDataBinding: ViewDataBinding?) {
        super.setViewDataBinding(viewDataBinding)
        when (viewDataBinding) {
            is HomeNewCourseViewholderBinding -> binding = viewDataBinding
            else -> Assert.fail("binding is incompatible")
        }
    }

    override fun onBindView(data: HomeCourseData) {
        data.academy.also {
            Glide.with(itemView).load(it.image).into(binding.image)
            binding.title.text = it.courseTitle
            binding.description.text = it.courseDescription
        }
        setOnClickListener(binding.clickableLayout, onCourseClickListener, this, data)
    }
}