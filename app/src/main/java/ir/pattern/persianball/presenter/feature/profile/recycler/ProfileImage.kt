package ir.pattern.persianball.presenter.feature.profile.recycler

import android.annotation.SuppressLint
import android.view.View
import androidx.databinding.ViewDataBinding
import com.google.firebase.firestore.util.Assert
import ir.pattern.persianball.R
import ir.pattern.persianball.data.model.base.Equatable
import ir.pattern.persianball.data.model.base.PersianBallRecyclerData
import ir.pattern.persianball.databinding.HolderProfileImageBinding
import ir.pattern.persianball.presenter.adapter.BaseViewHolder

class ProfileImageData constructor() : PersianBallRecyclerData, Equatable{

    companion object{
        const val VIEW_TYPE = R.layout.holder_profile_image
    }
    override val viewType: Int = VIEW_TYPE

    override fun equals(other: Any?): Boolean = true

    override fun hashCode(): Int {
        return javaClass.hashCode()
    }
}

class ProfileImageViewHolder(itemView: View) : BaseViewHolder<ProfileImageData>(itemView){
    private lateinit var binding : HolderProfileImageBinding

    @SuppressLint("RestrictedApi")
    override fun setViewDataBinding(viewDataBinding: ViewDataBinding?) {
        super.setViewDataBinding(viewDataBinding)
        when(viewDataBinding){
            is HolderProfileImageBinding -> binding = viewDataBinding
            else -> Assert.fail("binding is incompatible")
        }
    }
    override fun onBindView(data: ProfileImageData?) {
        binding.backProfileImage.setImageDrawable(itemView.resources.getDrawable(R.drawable.desktop))
        binding.profileImage.setImageDrawable(itemView.resources.getDrawable(R.drawable.tom))
    }
}