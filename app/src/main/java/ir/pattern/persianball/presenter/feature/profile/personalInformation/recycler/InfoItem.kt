package ir.pattern.persianball.presenter.feature.profile.personalInformation.recycler

import android.annotation.SuppressLint
import android.view.View
import androidx.databinding.ViewDataBinding
import com.google.firebase.firestore.util.Assert
import ir.pattern.persianball.R
import ir.pattern.persianball.data.model.base.Equatable
import ir.pattern.persianball.data.model.base.PersianBallRecyclerData
import ir.pattern.persianball.data.model.profile.Address
import ir.pattern.persianball.data.model.profile.AddressDto
import ir.pattern.persianball.data.model.profile.InfoType
import ir.pattern.persianball.data.model.profile.ItemInfoDto
import ir.pattern.persianball.databinding.PersonalInformationViewholderBinding
import ir.pattern.persianball.presenter.adapter.BaseViewHolder
import java.util.*

class InfoItemData(val content: String, val infoType: InfoType) : PersianBallRecyclerData,
    Equatable {

    companion object {
        const val VIEW_TYPE = R.layout.personal_information_viewholder
    }

    override val viewType: Int = VIEW_TYPE

    override fun equals(other: Any?): Boolean = true

    override fun getUniqueId(): String = UUID.randomUUID().toString()

    override fun hashCode(): Int {
        return javaClass.hashCode()
    }
}

class InfoItemViewHolder(
    itemView: View,
    private val onEditClickListener: OnClickListener<InfoItemViewHolder, InfoItemData>
) : BaseViewHolder<InfoItemData>(itemView) {

    private lateinit var binding: PersonalInformationViewholderBinding

    @SuppressLint("RestrictedApi")
    override fun setViewDataBinding(viewDataBinding: ViewDataBinding?) {
        super.setViewDataBinding(viewDataBinding)
        when (viewDataBinding) {
            is PersonalInformationViewholderBinding -> binding = viewDataBinding
            else -> Assert.fail("binding is incompatible")
        }
    }

    override fun onBindView(data: InfoItemData) {
        binding.titleTxt.text = data.infoType.type
        binding.contentTxt.text = data.content
        when (data.infoType) {
            InfoType.NAME, InfoType.FAMILY_NAME, InfoType.LATIN_NAME, InfoType.LATIN_FAMILY_NAME -> {
                binding.titleTxt.setCompoundDrawablesWithIntrinsicBounds(
                    0,
                    0,
                    R.drawable.ic_profile_name,
                    0
                )
            }
            InfoType.BIRTHDAY -> {
                binding.titleTxt.setCompoundDrawablesWithIntrinsicBounds(
                    0,
                    0,
                    R.drawable.ic_profile_birthday,
                    0
                )
            }
            InfoType.NATION -> binding.titleTxt.setCompoundDrawablesWithIntrinsicBounds(
                0,
                0,
                R.drawable.ic_profile_nationality,
                0
            )
            InfoType.NATIONAL_CODE -> binding.titleTxt.setCompoundDrawablesWithIntrinsicBounds(
                0,
                0,
                R.drawable.ic_proflile_national_code,
                0
            )
            else -> {}
        }
        setOnClickListener(binding.persianBallImageButton, onEditClickListener, this, data)
    }

}