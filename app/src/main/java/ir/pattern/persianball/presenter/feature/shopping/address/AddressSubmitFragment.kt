package ir.pattern.persianball.presenter.feature.shopping.address

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import ir.pattern.persianball.R
import ir.pattern.persianball.data.model.profile.InfoType
import ir.pattern.persianball.databinding.FragmentAddressSubmitBinding
import ir.pattern.persianball.databinding.FragmentProductDetailBinding
import ir.pattern.persianball.presenter.feature.profile.EditInfoDialogFragment
import ir.pattern.persianball.presenter.feature.profile.EditInfoViewModel
import ir.pattern.persianball.presenter.feature.shopping.address.add.AddAddressActivity
import ir.pattern.persianball.presenter.feature.shopping.address.dialog.SendingMethodDialogFragment

class AddressSubmitFragment : Fragment() {

    lateinit var binding: FragmentAddressSubmitBinding
    private val dialogViewModel: SendingViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
            val direction = AddressSubmitFragmentDirections.actionAddressSubmitFragmentToPaymentFragment()
            findNavController().navigate(direction)
        }
        return binding.root
    }
}