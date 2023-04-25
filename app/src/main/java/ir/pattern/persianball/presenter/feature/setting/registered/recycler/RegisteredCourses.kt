package ir.pattern.persianball.presenter.feature.setting.registered.recycler

import android.annotation.SuppressLint
import android.view.View
import androidx.databinding.ViewDataBinding
import com.bumptech.glide.Glide
import com.google.firebase.firestore.util.Assert
import ir.pattern.persianball.R
import ir.pattern.persianball.data.model.base.Equatable
import ir.pattern.persianball.data.model.base.PersianBallRecyclerData
import ir.pattern.persianball.data.model.dashboard.DashboardDto
import ir.pattern.persianball.databinding.HolderRegisteredCourseBinding
import ir.pattern.persianball.presenter.adapter.BaseViewHolder
import java.util.*

class RegisteredCoursesData(val dashboardDto: DashboardDto) : PersianBallRecyclerData, Equatable {

    companion object {
        const val VIEW_TYPE = R.layout.holder_registered_course
    }

    override val viewType: Int = VIEW_TYPE

    override fun equals(other: Any?): Boolean = true

    override fun getUniqueId(): String = UUID.randomUUID().toString()

    override fun hashCode(): Int {
        return javaClass.hashCode()
    }
}

class RegisteredCoursesViewHolder(
    itemView: View
) : BaseViewHolder<RegisteredCoursesData>(itemView){

    private lateinit var binding: HolderRegisteredCourseBinding

    @SuppressLint("RestrictedApi")
    override fun setViewDataBinding(viewDataBinding: ViewDataBinding?) {
        super.setViewDataBinding(viewDataBinding)
        when (viewDataBinding) {
            is HolderRegisteredCourseBinding -> binding = viewDataBinding
            else -> Assert.fail("binding is incompatible")
        }
    }
    override fun onBindView(data: RegisteredCoursesData?) {
       data?.dashboardDto?.also {
           binding.title.text = it.courseTitle
           Glide.with(itemView).load("http://api.persianball.ir/media/course/cordless-screwdriver.jpg").into(binding.shapeableImageView)
           binding.teacherText.text = "مدرس: ${it.coach}"
           binding.progressView.progress = it.completionPercent.toFloat()
           binding.duration.text = " ${it.weekCount}هفته"
           binding.progressText.text = "شما ${it.completionPercent} درصد از این دوره را گذرانده‌اید"
           binding.progressView.labelText = "%${it.completionPercent}"
           binding.rateText.text = "امتیاز: 33/100"
       }
    }

}