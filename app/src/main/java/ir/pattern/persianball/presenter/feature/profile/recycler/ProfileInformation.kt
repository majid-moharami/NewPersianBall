package ir.pattern.persianball.presenter.feature.profile.recycler

import android.annotation.SuppressLint
import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.firestore.util.Assert
import ir.pattern.persianball.R
import ir.pattern.persianball.data.model.base.Equatable
import ir.pattern.persianball.data.model.base.PersianBallRecyclerData
import ir.pattern.persianball.databinding.HolderProfileInformationsBinding
import ir.pattern.persianball.presenter.adapter.BaseViewHolder
import ir.pattern.persianball.presenter.feature.profile.address.AddressFragment
import ir.pattern.persianball.presenter.feature.profile.personalInformation.PersonalInformationFragment
import ir.pattern.persianball.presenter.feature.profile.password.ProfilePasswordFragment

class ProfileInformationData() : PersianBallRecyclerData, Equatable {

    companion object {
        const val VIEW_TYPE = R.layout.holder_profile_informations
    }

    override val viewType: Int = VIEW_TYPE

    override fun equals(other: Any?): Boolean = true

    override fun hashCode(): Int {
        return viewType
    }
}

class ProfileInformationViewHolder(
    itemView: View,
    private val fm: FragmentManager,
    val lifecycle: Lifecycle
) : BaseViewHolder<ProfileInformationData>(itemView) {

    private lateinit var binding: HolderProfileInformationsBinding

    @SuppressLint("RestrictedApi")
    override fun setViewDataBinding(viewDataBinding: ViewDataBinding?) {
        super.setViewDataBinding(viewDataBinding)
        when (viewDataBinding) {
            is HolderProfileInformationsBinding -> binding = viewDataBinding
            else -> Assert.fail("binding is incompatible")
        }
    }

    override fun onBindView(data: ProfileInformationData?) {
        val adapter = ViewPagerAdapter(
            fm,
            lifecycle
        )
        binding.viewpager.adapter = adapter
        binding.viewpager.setCurrentItem(2, false)
        TabLayoutMediator(binding.tabLayout, binding.viewpager) { tab, position ->
            when (position) {
                0 -> tab.text = itemView.resources.getString(R.string.change_password)
                1 -> tab.text = itemView.resources.getString(R.string.address_phone)
                2 -> tab.text = itemView.resources.getString(R.string.personal_info)
            }
        }.attach()
    }

    class ViewPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
        FragmentStateAdapter(fragmentManager, lifecycle) {

        override fun getItemCount(): Int {
            return 3
        }

        override fun createFragment(position: Int): Fragment {
            return when (position) {
                0 -> ProfilePasswordFragment.newInstance()
                1 -> AddressFragment.newInstance()
                3 -> PersonalInformationFragment.newInstance()
                else -> PersonalInformationFragment.newInstance()
            }
        }
    }
}