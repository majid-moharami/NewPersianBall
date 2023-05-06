package ir.pattern.persianball.presenter.feature.shopping.orderComplete

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.pattern.persianball.data.model.Resource
import ir.pattern.persianball.data.model.shoppingCart.Discount
import ir.pattern.persianball.data.model.shoppingCart.DiscountDto
import ir.pattern.persianball.data.model.shoppingCart.PaymentCompleteDto
import ir.pattern.persianball.data.repository.ShoppingCartRepository
import ir.pattern.persianball.presenter.adapter.BaseViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OrderCompleteViewModel
@Inject constructor(
    private val shoppingCartRepository: ShoppingCartRepository
) : BaseViewModel() {

    private val _payment = MutableSharedFlow<PaymentCompleteDto?>()
    val payment = _payment.asSharedFlow()

    init {
        viewModelScope.launch {
            getPayment()
        }
    }

    suspend fun getPayment() {
        shoppingCartRepository.getPayments().collect {
            when (it) {
                is Resource.Success -> {
                    it.data?.also { payments ->
                       _payment.emit(payments.results.last())
                    }
                }
                is Resource.Failure -> {
                    it.error.code
                }
                else -> {}
            }
        }
    }


}