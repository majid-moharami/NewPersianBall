package ir.pattern.persianball.presenter.feature.shopping.address

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.PagingData
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import ir.pattern.persianball.R
import ir.pattern.persianball.data.model.RecyclerItem
import ir.pattern.persianball.data.model.Resource
import ir.pattern.persianball.data.model.base.RecyclerData
import ir.pattern.persianball.databinding.FragmentAddressSubmitBinding
import ir.pattern.persianball.presenter.adapter.BasePagingAdapter
import ir.pattern.persianball.presenter.adapter.BaseViewHolder
import ir.pattern.persianball.presenter.feature.shopping.address.add.AddAddressActivity
import ir.pattern.persianball.presenter.feature.shopping.address.dialog.SendingMethodDialogFragment
import ir.pattern.persianball.presenter.feature.shopping.address.dialog.SendingViewModel
import ir.pattern.persianball.presenter.feature.shopping.address.recycler.OrderAddressAdapter
import ir.pattern.persianball.presenter.feature.shopping.address.recycler.OrderAddressData
import ir.pattern.persianball.presenter.feature.shopping.recycler.ShoppingCartAdapter
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AddressSubmitFragment : Fragment() {

    lateinit var binding: FragmentAddressSubmitBinding
    private val viewModel: OrderAddressViewModel by activityViewModels()
    private val dialogViewModel: SendingViewModel by viewModels()
    var pagingAdapter: BasePagingAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onResume() {
        super.onResume()
//        if (viewModel.addressAdded) {
//            viewLifecycleOwner.lifecycleScope.launch {
//                viewModel.getAddress()
//                viewModel.setAddressAdded()
//            }
//        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(activity),
            R.layout.fragment_address_submit,
            container,
            false
        )
        binding.addNewAddress.setOnClickListener {
            startActivity(Intent(activity, AddAddressActivity::class.java))
        }

        binding.sendMethod.setOnClickListener {
            val dialog = SendingMethodDialogFragment.newInstance()
            dialog.show(childFragmentManager, "edit_info_dialog")
        }

        binding.address.setOnClickListener {
            if (viewModel.lastSelectedAddressId == null) {
                Toast.makeText(activity, "آدرس موردنظر را انتخاب کنید.", Toast.LENGTH_LONG).show()
            } else {
                val direction =
                    AddressSubmitFragmentDirections.actionAddressSubmitFragmentToPaymentFragment(
                        dialogViewModel.deliveryMethod.type,
                        viewModel.lastSelectedAddressId!!
                    )
                findNavController().navigate(direction)
            }
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.addressList.collect {
                when (it) {
                    is Resource.Success -> {
                        if (it.data.addresses.isNotEmpty()) {
                            binding.recyclerView.visibility = View.VISIBLE
                            binding.sendMethod.visibility = View.VISIBLE
                            binding.empty.visibility = View.GONE
                            binding.textView5.visibility = View.GONE
                        } else {
                            binding.recyclerView.visibility = View.GONE
                            binding.empty.visibility = View.VISIBLE
                            binding.textView5.visibility = View.VISIBLE
                            binding.sendMethod.visibility = View.GONE
                        }
                    }
                    is Resource.Failure -> {

                    }
                    else -> {
                        Toast.makeText(activity, "LOADING", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.addressAdded.collectLatest {
                if (it) {
                    viewModel.getAddress()
                    viewModel.setAddressAdded()
                }
            }
        }

        binding.recyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        pagingAdapter = OrderAddressAdapter().also {
            binding.recyclerView.adapter = it

            it.onClickListener =
                BaseViewHolder.OnClickListener { view, viewHolder, recyclerData ->
                    viewModel.updateSelectedAddress(recyclerData.addressDto.id)
                }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.recyclerItems.collectLatest {
                it?.let { recyclerData ->
                    pagingAdapter?.submitData(recyclerData)
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.recyclerItems.value?.let {
                binding.recyclerView.visibility = View.VISIBLE
                binding.sendMethod.visibility = View.VISIBLE
                binding.empty.visibility = View.GONE
                binding.textView5.visibility = View.GONE
            } ?: run {
                binding.recyclerView.visibility = View.GONE
                binding.empty.visibility = View.VISIBLE
                binding.textView5.visibility = View.VISIBLE
                binding.sendMethod.visibility = View.GONE
            }
        }
    }
}