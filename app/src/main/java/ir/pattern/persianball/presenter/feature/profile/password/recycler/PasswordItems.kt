package ir.pattern.persianball.presenter.feature.profile.password.recycler

import android.annotation.SuppressLint
import android.view.View
import androidx.databinding.ViewDataBinding
import com.google.firebase.firestore.util.Assert
import ir.pattern.persianball.R
import ir.pattern.persianball.data.model.base.Equatable
import ir.pattern.persianball.data.model.base.PersianBallRecyclerData
import ir.pattern.persianball.data.model.profile.ItemInfoDto
import ir.pattern.persianball.databinding.HolderChangePasswordProfileBinding
import ir.pattern.persianball.databinding.PersonalInformationViewholderBinding
import ir.pattern.persianball.presenter.adapter.BaseViewHolder

class PasswordItemsData(val itemInfoDto: ItemInfoDto): PersianBallRecyclerData, Equatable{

    companion object{
        const val VIEW_TYPE = R.layout.holder_change_password_profile
    }

    override val viewType: Int = VIEW_TYPE

    override fun equals(other: Any?): Boolean = true

    override fun hashCode(): Int {
        return itemInfoDto.hashCode()
    }
}

class PasswordItemsViewHolder(itemView: View): BaseViewHolder<PasswordItemsData>(itemView){

    private lateinit var binding: HolderChangePasswordProfileBinding

    @SuppressLint("RestrictedApi")
    override fun setViewDataBinding(viewDataBinding: ViewDataBinding?) {
        super.setViewDataBinding(viewDataBinding)
        when (viewDataBinding) {
            is HolderChangePasswordProfileBinding -> binding = viewDataBinding
            else -> Assert.fail("binding is incompatible")
        }
    }

    override fun onBindView(data: PasswordItemsData?) {
        binding.titleTxt.text = data?.itemInfoDto?.title
    }

}