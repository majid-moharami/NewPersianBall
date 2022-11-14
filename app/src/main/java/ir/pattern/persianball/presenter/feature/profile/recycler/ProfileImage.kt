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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class ProfileImageData constructor(var avatar: StateFlow<String?>) : PersianBallRecyclerData,
    Equatable {

    companion object {
        const val VIEW_TYPE = R.layout.holder_profile_image
    }

    override val viewType: Int = VIEW_TYPE

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ProfileImageData

        if (avatar != other.avatar) return false

        return true
    }

    override fun hashCode(): Int {
        return avatar.hashCode() ?: 0
    }
}

class ProfileImageViewHolder(itemView: View, val uploadImager: () -> Unit) :
    BaseViewHolder<ProfileImageData>(itemView) {
    private lateinit var binding: HolderProfileImageBinding
    private var job: Job? = null

    @SuppressLint("RestrictedApi")
    override fun setViewDataBinding(viewDataBinding: ViewDataBinding?) {
        super.setViewDataBinding(viewDataBinding)
        when (viewDataBinding) {
            is HolderProfileImageBinding -> binding = viewDataBinding
            else -> Assert.fail("binding is incompatible")
        }
    }

    override fun onBindView(data: ProfileImageData?) {
        binding.backProfileImage.setImageDrawable(itemView.resources.getDrawable(R.drawable.desktop))
    }

    override fun onAttach(data: ProfileImageData?) {
        super.onAttach(data)
        job = Job()
        CoroutineScope(Dispatchers.Main.immediate + job!!).launch {
            data?.avatar?.collectLatest {
                if (it.isNullOrEmpty()) {
                    binding.profileImage.setOnClickListener {
                        uploadImager.invoke()
                    }
                } else {
                    binding.profileImage.setOnClickListener(null)
                }
            }
        }
    }

    override fun onDetach(data: ProfileImageData?) {
        super.onDetach(data)
        job?.cancel()
    }
}