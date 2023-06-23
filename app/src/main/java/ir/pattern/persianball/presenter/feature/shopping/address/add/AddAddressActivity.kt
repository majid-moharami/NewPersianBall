package ir.pattern.persianball.presenter.feature.shopping.address.add

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import ir.pattern.persianball.data.model.Resource
import ir.pattern.persianball.data.model.address.OrderAddress
import ir.pattern.persianball.data.model.profile.Address
import ir.pattern.persianball.data.repository.ShoppingCartRepository
import ir.pattern.persianball.databinding.ActivityAddAddressBinding
import ir.pattern.persianball.presenter.feature.profile.address.AddressFragment
import ir.pattern.persianball.presenter.feature.shopping.ShoppingCartViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class AddAddressActivity : AppCompatActivity() {
    lateinit var binding: ActivityAddAddressBinding
    private val viewModel: AddAddressViewModel by viewModels()

    @Inject
    lateinit var shoppingCartRepository: ShoppingCartRepository
    var address: Address? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddAddressBinding.inflate(layoutInflater)
        setContentView(binding.root)
        address = try {
            intent.getSerializableExtra(AddressFragment.ADDRESS_OBJECT_EXTRA_CODE) as Address
        }catch (e:Exception){
            null
        }
        address?.also {
            setView(it)
        }
        binding.addBtn.setOnClickListener {
            binding.addBtn.isEnabled = false
            addAddress(address != null)
        }
        binding.backBtn.setOnClickListener {
            onBackPressed()
        }
    }

    private fun setView(address: Address) {
        binding.addressName.setText(address.addressName)
        binding.phoneNumber.setText(address.mobilePhone)
        binding.address.setText(address.address)
        binding.email.setText(address.email)
        binding.province.setText(address.city)
        binding.eparchy.setText(address.province)
        address.postalCode?.let { binding.postalCode.setText(it.toString()) }
    }

    private fun addAddress(isUpdate: Boolean) {
        if (!binding.phoneNumber.text.isNullOrBlank() && !binding.province.text.isNullOrBlank() &&
            !binding.eparchy.text.isNullOrBlank() && !binding.address.text.isNullOrBlank()
        ) {
            if (binding.postalCode.text.isNullOrEmpty() || binding.postalCode.text.length != 10) {
                Toast.makeText(
                    this@AddAddressActivity,
                    "لطفا کد پستی 10 رقمی را وارد کنید.",
                    Toast.LENGTH_LONG
                ).show()
                binding.addBtn.isEnabled = true
            } else {
                if (isUpdate) {
                    binding.also {
                        lifecycleScope.launch {
                            address?.id?.let { it1 ->
                                viewModel.updateAddress(
                                    it1,
                                    OrderAddress(
                                        it.addressName.text?.toString(),
                                        it.postalCode.text.toString(),
                                        "${it.address.text}",
                                        it.homePhone.text.toString(),
                                        it.phoneNumber.text.toString(),
                                        city = it.province.text.toString(),
                                        province = it.eparchy.text.toString()
                                    ).apply {
                                        if (!it.email.text.isNullOrEmpty()) {
                                            this.email = it.email.text.toString()
                                        }
                                        if (!it.homePhone.text.isNullOrEmpty()) {
                                            this.receiverHomePhone = it.homePhone.text.toString()
                                        }
                                    }
                                ).collect {
                                    when (it) {
                                        is Resource.Success -> {
                                            viewModel.addressAdded(true)
                                            this@AddAddressActivity.finish()
                                        }
                                        is Resource.Failure -> {
                                            Toast.makeText(
                                                this@AddAddressActivity,
                                                "لطفا موارد اجباری را پر کنید.",
                                                Toast.LENGTH_LONG
                                            ).show()
                                            binding.addBtn.isEnabled = true
                                        }
                                        else -> {}
                                    }
                                }
                            }
                        }
                    }
                } else {
                    binding.also {
                        lifecycleScope.launch {
                            viewModel.addAddress(
                                OrderAddress(
                                    it.addressName.text?.toString(),
                                    it.postalCode.text.toString(),
                                    "${it.address.text}",
                                    it.homePhone.text.toString(),
                                    it.phoneNumber.text.toString(),
                                    city = it.province.text.toString(),
                                    province = it.eparchy.text.toString()
                                ).apply {
                                    if (!it.email.text.isNullOrEmpty()) {
                                        this.email = it.email.text.toString()
                                    }
                                    if (!it.homePhone.text.isNullOrEmpty()) {
                                        this.receiverHomePhone = it.homePhone.text.toString()
                                    }
                                }
                            ).collect {
                                when (it) {
                                    is Resource.Success -> {
                                        viewModel.addressAdded(true)
                                        this@AddAddressActivity.finish()
                                    }
                                    is Resource.Failure -> {
                                        Toast.makeText(
                                            this@AddAddressActivity,
                                            "لطفا موارد اجباری را پر کنید.",
                                            Toast.LENGTH_LONG
                                        ).show()
                                        binding.addBtn.isEnabled = true
                                    }
                                    else -> {}
                                }
                            }
                        }
                    }
                }
            }
        } else {
            Toast.makeText(this, "لطفا موارد اجباری را پر کنید.", Toast.LENGTH_LONG).show()
            binding.addBtn.isEnabled = true
        }
    }
}