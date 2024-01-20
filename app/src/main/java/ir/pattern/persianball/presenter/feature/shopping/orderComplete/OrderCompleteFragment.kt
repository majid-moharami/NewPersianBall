package ir.pattern.persianball.presenter.feature.shopping.orderComplete

import android.content.ClipData
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.text.ClipboardManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import ir.pattern.persianball.R
import ir.pattern.persianball.databinding.FragmentOrderCompleteBinding
import kotlinx.coroutines.launch

@AndroidEntryPoint
class OrderCompleteFragment : Fragment() {

    lateinit var binding: FragmentOrderCompleteBinding
    private val viewModel: OrderCompleteViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(activity),
            R.layout.fragment_order_complete,
            container,
            false
        )

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.intent?.extras?.getString("TransactionNumber").takeIf { !it.isNullOrEmpty() }?.let {
            binding.code.text = it.substring(1)
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.payment.collect {
                binding.code.text = it?.trackingCode.toString()
            }
        }

        binding.copy.setOnClickListener {
            copyToClipboard("tracking_code", binding.code.text.toString())
            Toast.makeText(requireContext(), "با موفقیت کپی شد.", Toast.LENGTH_SHORT).show()
        }
    }

    fun copyToClipboard(label: String?, textToClip: String?) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
            val clipboard = context
                ?.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            clipboard.text = textToClip
        } else {
            val clipboard =
                context
                    ?.getSystemService(Context.CLIPBOARD_SERVICE) as android.content.ClipboardManager?
            val clip = ClipData.newPlainText(label, textToClip)
            clipboard!!.setPrimaryClip(clip)
        }
    }
}