package ir.pattern.persianball.presenter.feature.shopping.address.dialog

import android.annotation.SuppressLint
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.PagingData
import ir.pattern.persianball.R
import ir.pattern.persianball.data.model.RecyclerItem
import ir.pattern.persianball.data.model.Resource
import ir.pattern.persianball.data.model.base.RecyclerData
import ir.pattern.persianball.data.model.shoppingCart.DeliveryType
import ir.pattern.persianball.databinding.SendingMethodDialogBinding
import ir.pattern.persianball.presenter.feature.shopping.address.recycler.OrderAddressData
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch

class SendingMethodDialogFragment : DialogFragment() {
    lateinit var binding: SendingMethodDialogBinding
    private val dialogViewModel: SendingViewModel by viewModels({ requireParentFragment() })

    @SuppressLint("UseGetLayoutInflater")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(requireActivity()),
            R.layout.sending_method_dialog,
            container,
            false
        )
        binding.peick.setOnCheckedChangeListener { compoundButton, b ->
            if (b) chooseItem(DeliveryType.PEYK)
        }

        binding.post.setOnCheckedChangeListener { compoundButton, b ->
            if (b) chooseItem(DeliveryType.POST)
        }

        binding.free.setOnCheckedChangeListener { compoundButton, b ->
            if (b) chooseItem(DeliveryType.FREE)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (dialogViewModel.getTotalPrice() >= 700000){
            binding.free.isChecked = true
        }else{
            binding.free.isEnabled = false
        }
        when(dialogViewModel.deliveryMethod){
            DeliveryType.POST -> {
                binding.post.isChecked = true
            }
            DeliveryType.PEYK -> {
                binding.peick.isChecked = true
            }
            else -> {
                binding.free.isChecked = true
            }
        }
    }


    @SuppressLint("UseCompatLoadingForDrawables")
    private fun chooseItem(item: DeliveryType) {
        when (item) {
            DeliveryType.POST -> {
                dialogViewModel.deliveryMethod = DeliveryType.POST
                binding.post.background =
                    resources.getDrawable(R.drawable.backgraound_sending_method_choosed)
                binding.peick.background = null
                binding.free.background = null
            }
            DeliveryType.PEYK -> {
                dialogViewModel.deliveryMethod = DeliveryType.PEYK
                binding.peick.background =
                    resources.getDrawable(R.drawable.backgraound_sending_method_choosed)
                binding.post.background = null
                binding.free.background = null
            }
            else -> {
                dialogViewModel.deliveryMethod = DeliveryType.FREE
                binding.free.background =
                    resources.getDrawable(R.drawable.backgraound_sending_method_choosed)
                binding.peick.background = null
                binding.post.background = null
            }
        }
    }

    override fun onStart() {
        super.onStart()
        val dialog: Dialog? = dialog
        dialog?.let {
            it.setCancelable(true)
            it.window?.also { window ->
                with(window) {
                    setLayout(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                    )
                    setGravity(Gravity.CENTER)
                    setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                }
            }
        }
    }

    companion object {
        fun newInstance(): SendingMethodDialogFragment {
            return SendingMethodDialogFragment()
        }
    }
}