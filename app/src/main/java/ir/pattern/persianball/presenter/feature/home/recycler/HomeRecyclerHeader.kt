package ir.pattern.persianball.presenter.feature.home.recycler

import android.annotation.SuppressLint
import android.view.View
import androidx.databinding.ViewDataBinding
import com.google.firebase.firestore.util.Assert
import ir.pattern.persianball.R
import ir.pattern.persianball.data.model.base.Equatable
import ir.pattern.persianball.data.model.base.PersianBallRecyclerData
import ir.pattern.persianball.databinding.HolderHomeRecyclerTitleBinding
import ir.pattern.persianball.databinding.HomeNewCourseViewholderBinding
import ir.pattern.persianball.presenter.adapter.BaseViewHolder

class HomeRecyclerHeaderData(val title: String) : PersianBallRecyclerData, Equatable {
    companion object {
        const val VIEW_TYPE = R.layout.holder_home_recycler_title
    }

    override val viewType: Int = VIEW_TYPE

    override fun equals(other: Any?): Boolean {
        TODO("Not yet implemented")
    }

    override fun hashCode(): Int {
        return javaClass.hashCode()
    }
}

class HomeRecyclerHeaderViewHolder(itemView: View) :
    BaseViewHolder<HomeRecyclerHeaderData>(itemView) {
    private lateinit var binding: HolderHomeRecyclerTitleBinding

    @SuppressLint("RestrictedApi")
    override fun setViewDataBinding(viewDataBinding: ViewDataBinding?) {
        super.setViewDataBinding(viewDataBinding)
        when (viewDataBinding) {
            is HolderHomeRecyclerTitleBinding -> binding = viewDataBinding
            else -> Assert.fail("binding is incompatible")
        }
    }

    override fun onBindView(data: HomeRecyclerHeaderData) {
        binding.headerTitle.text = data.title
    }

}