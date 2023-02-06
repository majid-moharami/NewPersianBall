package ir.pattern.persianball.presenter.feature.shopping

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.findNavController
import ir.pattern.persianball.R
import ir.pattern.persianball.databinding.ActivityOrderRecordBinding
import ir.pattern.persianball.databinding.ActivityShoppingCartsBinding

class OrderRecordActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private lateinit var binding: ActivityOrderRecordBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOrderRecordBinding.inflate(layoutInflater)
        setContentView(binding.root)
        navController = findNavController(R.id.order_nav_host_fragment)
    }
}