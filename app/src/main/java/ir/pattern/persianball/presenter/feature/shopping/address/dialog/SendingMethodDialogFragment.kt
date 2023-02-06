package ir.pattern.persianball.presenter.feature.shopping.address.dialog

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
import ir.pattern.persianball.R
import ir.pattern.persianball.databinding.SendingMethodDialogBinding
import ir.pattern.persianball.presenter.feature.shopping.address.SendingViewModel

class SendingMethodDialogFragment : DialogFragment() {
    lateinit var binding: SendingMethodDialogBinding
    private val dialogViewModel: SendingViewModel by viewModels({ requireParentFragment() })

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
            if (b){
                binding.peick.background= resources.getDrawable(R.drawable.backgraound_sending_method_choosed)
                binding.post.background= null
                binding.free.background= null
            }
        }

        binding.post.setOnCheckedChangeListener { compoundButton, b ->
            if (b){
                binding.post.background= resources.getDrawable(R.drawable.backgraound_sending_method_choosed)
                binding.peick.background= null
                binding.free.background= null
            }
        }

        binding.free.setOnCheckedChangeListener { compoundButton, b ->
            if (b){
                binding.free.background= resources.getDrawable(R.drawable.backgraound_sending_method_choosed)
                binding.peick.background= null
                binding.post.background= null
            }
        }
        return binding.root
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

    private fun checkedColor() {
        if (binding.peick.isChecked) {
            binding.peick.background =
                resources.getDrawable(R.drawable.backgraound_sending_method_choosed)
            //binding.peickCard.cardBackgroundColor = resources.getColor(R.color.send_method_card_color)
        }
        binding.post.background =
            resources.getDrawable(R.drawable.backgraound_sending_method_choosed)
        binding.free.background =
            resources.getDrawable(R.drawable.backgraound_sending_method_choosed)
    }

    companion object {
        fun newInstance(): SendingMethodDialogFragment {
            return SendingMethodDialogFragment()
        }
    }
}