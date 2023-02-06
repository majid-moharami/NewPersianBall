package ir.pattern.persianball.presenter.feature.shopping.payment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import ir.pattern.persianball.R
import ir.pattern.persianball.databinding.FragmentPaymentBinding

class PaymentFragment : Fragment() {

    lateinit var binding: FragmentPaymentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(activity),
            R.layout.fragment_payment,
            container,
            false
        )
        binding.paymentBtn.setOnClickListener {
            val direction = PaymentFragmentDirections.actionPaymentFragmentToOrderCompleteFragment()
            findNavController().navigate(direction)
        }
        return inflater.inflate(R.layout.fragment_payment, container, false)
    }

}