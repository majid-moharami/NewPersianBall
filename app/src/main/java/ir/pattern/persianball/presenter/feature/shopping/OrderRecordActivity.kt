package ir.pattern.persianball.presenter.feature.shopping

import android.R.attr
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import dagger.hilt.android.AndroidEntryPoint
import ir.pattern.persianball.R
import ir.pattern.persianball.databinding.ActivityOrderRecordBinding
import ir.pattern.persianball.presenter.feature.shopping.payment.PaymentFragment


@AndroidEntryPoint
class OrderRecordActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private lateinit var binding: ActivityOrderRecordBinding

    companion object {
        const val PAYMENT_REQUEST_CODE = 3000
        var isSuccess = false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOrderRecordBinding.inflate(layoutInflater)
        setContentView(binding.root)
        navController = findNavController(R.id.order_nav_host_fragment)
        setupNavigation()
        if (!intent.extras?.getString("TransactionNumber").isNullOrEmpty()){
            navController.navigate(R.id.orderCompleteFragment)
            binding.stepper.go(2,false)
        }
    }

    private fun setupNavigation() {
        //setupActionBarWithNavController(navController)
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.addressSubmitFragment -> {
                    binding.stepper.done(false)
                    binding.stepper.go(0,true)
                }
                R.id.paymentFragment -> {
                    binding.stepper.done(false)
                    binding.stepper.go(1,true)
                }
                R.id.orderCompleteFragment -> {
                    binding.stepper.done(false)
                    binding.stepper.go(2,true)
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PAYMENT_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                data?.also {
                    val payment = it.getStringExtra("isPaymentSuccess")
                    if (payment == "true") {
                        isSuccess = true
                        navController.navigate(R.id.orderCompleteFragment)
                        Toast.makeText(this, "پرداخت با موفقیت انجام شد.", Toast.LENGTH_LONG).show()
                    } else {
                        Toast.makeText(this, "پرداخت موفقیت آمیز نبود.", Toast.LENGTH_LONG).show()
                        finish()
                    }
                }
            }
        }
    }
}