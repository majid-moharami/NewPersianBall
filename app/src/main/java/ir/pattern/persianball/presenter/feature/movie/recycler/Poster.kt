package ir.pattern.persianball.presenter.feature.movie.recycler

import android.annotation.SuppressLint
import android.view.View
import androidx.core.view.isVisible
import androidx.databinding.ViewDataBinding
import com.bumptech.glide.Glide
import com.google.firebase.firestore.util.Assert
import ir.pattern.persianball.R
import ir.pattern.persianball.data.model.base.Equatable
import ir.pattern.persianball.data.model.base.PersianBallRecyclerData
import ir.pattern.persianball.databinding.HolderSectionPosterBinding
import ir.pattern.persianball.presenter.adapter.BaseViewHolder
import ir.pattern.persianball.utils.UiUtils
import java.util.*
import kotlin.math.floor

class PosterData(val image: String?, val count: Int?, val time: Int?, val categoty: String?) : PersianBallRecyclerData, Equatable {

    companion object {
        const val VIEW_TYPE = R.layout.holder_section_poster
    }

    override val viewType: Int = VIEW_TYPE

    override fun equals(other: Any?): Boolean = true

    override fun getUniqueId(): String = UUID.randomUUID().toString()
    override fun hashCode(): Int {
        var result = image?.hashCode() ?: 0
        result = 31 * result + (count?.hashCode() ?: 0)
        result = 31 * result + time.hashCode()
        result = 31 * result + viewType
        return result
    }

}

class PosterDataViewHolder(
    itemView: View
) : BaseViewHolder<PosterData>(itemView) {
    private lateinit var binding: HolderSectionPosterBinding

    @SuppressLint("RestrictedApi")
    override fun setViewDataBinding(viewDataBinding: ViewDataBinding?) {
        super.setViewDataBinding(viewDataBinding)
        when (viewDataBinding) {
            is HolderSectionPosterBinding -> binding = viewDataBinding
            else -> Assert.fail("binding is incompatible")
        }
    }

    override fun onBindView(data: PosterData?) {
        Glide.with(itemView.context).load("https://api.persianball.ir/${data?.image}").into(binding.poster)
        if (data?.time!! > 0) {
            binding.videoTime.text =convertTimeToString(data.time)
        } else {
            binding.videoTime.isVisible = false
        }
        if (data.categoty == "کلاس ها") {
            binding.videoCount.text = itemView.resources.getString(R.string.section_count,  data.count.toString())
        }else{
            binding.videoCount.text = itemView.resources.getString(R.string.week_count,  data.count.toString())
        }
    }

    private fun convertTimeToString(seconds: Int): String {
        val secondsLeft: Int = seconds % 3600 % 60
        val minutes = floor((seconds % 3600 / 60).toDouble()).toInt()
        val hours = floor((seconds / 3600).toDouble()).toInt()
        val stringBuilder = StringBuilder()
        val formatter = Formatter(stringBuilder, Locale.getDefault())
        stringBuilder.setLength(0)
        return if (hours > 0) {
            UiUtils.convertToPersianNumber(
                formatter.format("%d:%02d:%02d", hours, minutes, secondsLeft).toString()
            )
        } else {
            UiUtils.convertToPersianNumber(
                formatter.format("%02d:%02d", minutes, secondsLeft).toString()
            )
        }
    }

}