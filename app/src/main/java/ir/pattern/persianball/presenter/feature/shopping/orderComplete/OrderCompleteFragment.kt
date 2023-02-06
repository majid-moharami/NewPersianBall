package ir.pattern.persianball.presenter.feature.shopping.orderComplete

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import ir.pattern.persianball.R
import ir.pattern.persianball.databinding.FragmentOrderCompleteBinding

class OrderCompleteFragment : Fragment() {

    lateinit var binding: FragmentOrderCompleteBinding

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
}