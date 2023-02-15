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
import ir.pattern.persianball.data.repository.ShoppingCartRepository
import ir.pattern.persianball.databinding.ActivityAddAddressBinding
import ir.pattern.persianball.presenter.feature.shopping.ShoppingCartViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class AddAddressActivity : AppCompatActivity() {
    lateinit var binding: ActivityAddAddressBinding
    private val viewModel: AddAddressViewModel by viewModels()
    @Inject
    lateinit var shoppingCartRepository: ShoppingCartRepository
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddAddressBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.addBtn.setOnClickListener {
            addAddress()
        }
    }

    private fun addAddress() {
        if (!binding.phoneNumber.text.isNullOrBlank() && !binding.province.text.isNullOrBlank() &&
            !binding.eparchy.text.isNullOrBlank() && !binding.address.text.isNullOrBlank() &&
            !binding.postalCode.text.isNullOrBlank()
        ) {
            binding.also {
                lifecycleScope.launch {
                    viewModel.addAddress(
                        OrderAddress(
                            it.addressName.text?.toString(),
                            it.postalCode.text.toString(),
                            "${it.province.text}, ${it.eparchy.text}, ${it.address.text}",
                            it.homePhone.text.toString(),
                            it.phoneNumber.text.toString()
                        )
                    ).collect {
                        when (it) {
                            is Resource.Success -> {
                                viewModel.addressAdded(true)
                                this@AddAddressActivity.finish()
                            }
                            is Resource.Failure -> {
                                Toast.makeText(
                                    this@AddAddressActivity,
                                    "لطفا جاهای خالی را پر کنید.",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                            else -> {}
                        }
                    }
                }
            }
        } else {
            Toast.makeText(this, "لطفا جاهای خالی را پر کنید.", Toast.LENGTH_LONG).show()
        }
    }
}