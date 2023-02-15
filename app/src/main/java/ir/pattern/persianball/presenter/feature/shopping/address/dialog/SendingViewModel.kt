package ir.pattern.persianball.presenter.feature.shopping.address.dialog

import android.os.Bundle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.pattern.persianball.data.model.shoppingCart.DeliveryType
import ir.pattern.persianball.data.repository.ShoppingCartRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject

@HiltViewModel
class SendingViewModel @Inject constructor(
    private val shoppingCartRepository: ShoppingCartRepository
) : ViewModel()  {
    private val _dialogResult = MutableSharedFlow<String>()
    val dialogResult = _dialogResult.asSharedFlow()
    var deliveryMethod : DeliveryType = DeliveryType.POST

    fun getTotalPrice() = shoppingCartRepository.totalPrice
}