package ir.pattern.persianball.presenter.feature.message.recycler

import android.annotation.SuppressLint
import android.view.View
import androidx.core.view.isVisible
import androidx.databinding.ViewDataBinding
import com.google.firebase.firestore.util.Assert
import ir.pattern.persianball.R
import ir.pattern.persianball.data.model.MessageDto
import ir.pattern.persianball.data.model.base.Equatable
import ir.pattern.persianball.data.model.base.PersianBallRecyclerData
import ir.pattern.persianball.databinding.HolderNotificationMessageBinding
import ir.pattern.persianball.presenter.adapter.BaseViewHolder

class MessageData(val messageDto: MessageDto?, val isNew: Boolean) : PersianBallRecyclerData,
    Equatable {

    companion object{
        const val VIEW_TYPE = R.layout.holder_notification_message
    }

    override val viewType: Int = VIEW_TYPE

    override fun equals(other: Any?): Boolean = true

    override fun getUniqueId(): String = "sdf"

    override fun hashCode(): Int {
        var result = messageDto?.hashCode() ?: 0
        result = 31 * result + isNew.hashCode()
        return result
    }
}

class MessageDataViewHolder(
    itemView: View
) : BaseViewHolder<MessageData>(itemView) {
    private lateinit var binding: HolderNotificationMessageBinding

    @SuppressLint("RestrictedApi")
    override fun setViewDataBinding(viewDataBinding: ViewDataBinding?) {
        super.setViewDataBinding(viewDataBinding)
        when (viewDataBinding) {
            is HolderNotificationMessageBinding -> binding = viewDataBinding
            else -> Assert.fail("binding is incompatible")
        }
    }

    override fun onBindView(data: MessageData) {
        binding.newBadge.isVisible = data.isNew
        binding.title.text = data.messageDto?.title
        binding.text.text = data.messageDto?.text
    }

}