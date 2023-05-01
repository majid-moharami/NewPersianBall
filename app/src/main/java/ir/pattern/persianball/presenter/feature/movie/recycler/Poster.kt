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
import java.util.*

class PosterData(val image: String?, val count: Int?, val time: Int?) : PersianBallRecyclerData, Equatable {

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
            binding.videoTime.text =
                itemView.resources.getString(R.string.course_duration, data.time.toString())
        } else {
            binding.videoTime.isVisible = false
        }
        binding.videoCount.text = itemView.resources.getString(R.string.video_count, data.count)
    }

}