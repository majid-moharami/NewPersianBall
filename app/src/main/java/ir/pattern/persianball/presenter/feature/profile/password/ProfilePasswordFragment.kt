package ir.pattern.persianball.presenter.feature.profile.password

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import ir.pattern.persianball.R
import ir.pattern.persianball.databinding.FragmentAddressBinding
import ir.pattern.persianball.databinding.FragmentProfileBinding
import ir.pattern.persianball.databinding.FragmentProfilePasswordBinding
import ir.pattern.persianball.presenter.adapter.BasePagingAdapter
import ir.pattern.persianball.presenter.feature.profile.address.AddressDataAdapter
import ir.pattern.persianball.presenter.feature.profile.address.AddressViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class ProfilePasswordFragment : Fragment() {

    lateinit var binding: FragmentProfilePasswordBinding
    private val viewModel: PasswordViewModel by viewModels()
    var pagingAdapter: BasePagingAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(activity),
            R.layout.fragment_profile_password,
            container,
            false
        )

        pagingAdapter = PasswordDataAdapter().also {
            binding.recyclerView.adapter = it
        }
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL ,false)
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.recyclerItems.collectLatest {
                it?.let { recyclerData ->
                    pagingAdapter?.submitData(recyclerData)
                }
            }
        }

        return binding.root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ProfilePasswordFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance() =
            ProfilePasswordFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}