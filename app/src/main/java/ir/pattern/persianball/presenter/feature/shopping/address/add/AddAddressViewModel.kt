package ir.pattern.persianball.presenter.feature.shopping.address.add

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.pattern.persianball.data.model.Resource
import ir.pattern.persianball.data.model.address.OrderAddress
import ir.pattern.persianball.data.repository.ShoppingCartRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddAddressViewModel
@Inject constructor(
    private val shoppingCartRepository: ShoppingCartRepository
) : ViewModel() {

    suspend fun addAddress(address: OrderAddress): Flow<Resource<Any?>> {
        return shoppingCartRepository.addAddress(address)
    }

    suspend fun updateAddress(id: Long, address: OrderAddress) : Flow<Resource<Any?>> {
        return shoppingCartRepository.updateAddress(id, address)
    }

    fun addressAdded(a: Boolean) {
        viewModelScope.launch {
            shoppingCartRepository.setAddressAdded(a)
        }
    }

}