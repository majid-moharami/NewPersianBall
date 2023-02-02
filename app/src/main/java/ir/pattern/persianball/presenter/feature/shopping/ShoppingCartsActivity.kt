package ir.pattern.persianball.presenter.feature.shopping

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.findNavController
import dagger.hilt.android.AndroidEntryPoint
import ir.pattern.persianball.R
import ir.pattern.persianball.databinding.ActivityShoppingCartsBinding

@AndroidEntryPoint
class ShoppingCartsActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private lateinit var binding: ActivityShoppingCartsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShoppingCartsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        navController = findNavController(R.id.shopping_nav_host_fragment)
    }
}