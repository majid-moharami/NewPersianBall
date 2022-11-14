package ir.pattern.persianball.presenter.feature.profile.recycler

import android.annotation.SuppressLint
import android.view.View
import androidx.databinding.ViewDataBinding
import com.google.firebase.firestore.util.Assert
import ir.pattern.persianball.R
import ir.pattern.persianball.data.model.base.Equatable
import ir.pattern.persianball.data.model.base.PersianBallRecyclerData
import ir.pattern.persianball.databinding.HolderProfileNameBinding
import ir.pattern.persianball.presenter.adapter.BaseViewHolder

class ProfileNameData(var firstName: String? = "", var lastName: String? = "") : PersianBallRecyclerData, Equatable{

    companion object{
        const val VIEW_TYPE = R.layout.holder_profile_name
    }
    override val viewType: Int = VIEW_TYPE
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ProfileNameData

        if (firstName != other.firstName) return false
        if (lastName != other.lastName) return false

        return true
    }

    override fun hashCode(): Int {
        var result = firstName?.hashCode() ?: 0
        result = 31 * result + (lastName?.hashCode() ?: 0)
        return result
    }
}

class ProfileNameViewHolder(itemView: View) : BaseViewHolder<ProfileNameData>(itemView){

    private lateinit var binding : HolderProfileNameBinding

    @SuppressLint("RestrictedApi")
    override fun setViewDataBinding(viewDataBinding: ViewDataBinding?) {
        super.setViewDataBinding(viewDataBinding)
        when(viewDataBinding){
            is HolderProfileNameBinding -> binding = viewDataBinding
            else -> Assert.fail("binding is incompatible")
        }
    }
    override fun onBindView(data: ProfileNameData) {
        binding.nameTxt.text = data.firstName + data.lastName
    }

}