package ir.pattern.persianball.presenter.feature.movie.dialog.recycler

import android.annotation.SuppressLint
import android.view.View
import androidx.databinding.ViewDataBinding
import com.bumptech.glide.Glide
import com.google.firebase.firestore.util.Assert
import ir.pattern.persianball.R
import ir.pattern.persianball.data.model.academy.CoachDto
import ir.pattern.persianball.data.model.base.Equatable
import ir.pattern.persianball.data.model.base.PersianBallRecyclerData
import ir.pattern.persianball.databinding.HolderChooseCoachBinding
import ir.pattern.persianball.databinding.HolderLocationBinding
import ir.pattern.persianball.presenter.adapter.BaseViewHolder
import java.util.UUID

class CoachItemData(val coachDto: CoachDto) : PersianBallRecyclerData,
    Equatable {

    companion object {
        const val VIEW_TYPE = R.layout.holder_choose_coach
    }

    override val viewType: Int = VIEW_TYPE

    override fun equals(other: Any?): Boolean = true

    override fun getUniqueId(): String = UUID.randomUUID().toString()

    override fun hashCode(): Int {
        return coachDto.hashCode()
    }
}

class CoachItemViewHolder(
    itemView: View,
    private val onCoachItemListener: BaseViewHolder.OnClickListener<CoachItemViewHolder, CoachItemData>?,
) : BaseViewHolder<CoachItemData>(itemView) {

    lateinit var binding: HolderChooseCoachBinding

    @SuppressLint("RestrictedApi")
    override fun setViewDataBinding(viewDataBinding: ViewDataBinding?) {
        super.setViewDataBinding(viewDataBinding)
        when (viewDataBinding) {
            is HolderChooseCoachBinding -> binding = viewDataBinding
            else -> Assert.fail("binding is incompatible")
        }
    }

    override fun onBindView(data: CoachItemData) {
        Glide.with(itemView).load("https://api.persianball.ir/${data.coachDto.avatar}").into(binding.image)
        binding.name.text = data.coachDto.fullName
        setOnClickListener(binding.clickableLayout, onCoachItemListener, this, data)
    }

}