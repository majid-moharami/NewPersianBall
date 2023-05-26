package ir.pattern.persianball.presenter.feature.academy.recycler

import android.annotation.SuppressLint
import android.view.View
import androidx.databinding.ViewDataBinding
import com.bumptech.glide.Glide
import com.google.firebase.firestore.util.Assert
import ir.pattern.persianball.R
import ir.pattern.persianball.data.model.academy.AcademyDto
import ir.pattern.persianball.data.model.base.Equatable
import ir.pattern.persianball.data.model.base.PersianBallRecyclerData
import ir.pattern.persianball.databinding.HolderAcademyItemBinding
import ir.pattern.persianball.presenter.adapter.BaseViewHolder
import ir.pattern.persianball.utils.UiUtils
import java.util.*

class AcademyCourseData(val academy: AcademyDto) : PersianBallRecyclerData, Equatable {
    companion object {
        const val VIEW_TYPE = R.layout.holder_academy_item
    }

    override val viewType: Int = VIEW_TYPE

    override fun equals(other: Any?): Boolean = true

    override fun getUniqueId(): String = UUID.randomUUID().toString()

    override fun hashCode(): Int {
        return javaClass.hashCode()
    }
}

class AcademyCourseViewHolder(
    itemView: View,
    private val onCourseClickListener: OnClickListener<AcademyCourseViewHolder, AcademyCourseData>?
) : BaseViewHolder<AcademyCourseData>(itemView) {

    private lateinit var binding: HolderAcademyItemBinding

    @SuppressLint("RestrictedApi")
    override fun setViewDataBinding(viewDataBinding: ViewDataBinding?) {
        super.setViewDataBinding(viewDataBinding)
        when (viewDataBinding) {
            is HolderAcademyItemBinding -> binding = viewDataBinding
            else -> Assert.fail("binding is incompatible")
        }
    }

    @SuppressLint("StringFormatMatches")
    override fun onBindView(data: AcademyCourseData?) {
        data?.academy?.also {
            binding.title.text = it.courseTitle
            binding.duration.text =
                itemView.resources.getString(R.string.course_week, it.weekCount, it.section_count)
            binding.hardness.text =
                itemView.resources.getString(R.string.difficulty, it.courseDifficulty)
            binding.time.text = convertTimeToString(it.courseDuration)
            Glide.with(itemView.context).load("https://api.persianball.ir/${it.image}")
                .into(binding.shapeableImageView)
        }
        setOnClickListener(binding.clickableLayout, onCourseClickListener, this, data)
    }

    private fun convertTimeToString(timeMs: Int): String {
        val seconds = timeMs % 60
        val minutes = (timeMs / 60) % 60
        val hours = timeMs / 3600
        val stringBuilder = StringBuilder()
        val formatter = Formatter(stringBuilder, Locale.getDefault())
        stringBuilder.setLength(0)
        return if (hours > 0) {
            UiUtils.convertToPersianNumber(
                formatter.format("%d:%02d:%02d", hours, minutes, seconds).toString()
            )
        } else {
            UiUtils.convertToPersianNumber(
                formatter.format("%02d:%02d", minutes, seconds).toString()
            )
        }
    }
}