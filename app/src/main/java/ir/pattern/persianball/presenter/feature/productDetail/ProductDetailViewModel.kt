package ir.pattern.persianball.presenter.feature.productDetail

import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.pattern.persianball.data.model.RecyclerItem
import ir.pattern.persianball.data.model.Resource
import ir.pattern.persianball.data.model.academy.AcademyDto
import ir.pattern.persianball.data.model.home.Product
import ir.pattern.persianball.data.model.home.VariantsDto
import ir.pattern.persianball.data.model.shoppingCart.CartItem
import ir.pattern.persianball.data.repository.HomeRepository
import ir.pattern.persianball.data.repository.ShoppingCartRepository
import ir.pattern.persianball.presenter.adapter.BaseViewModel
import ir.pattern.persianball.presenter.feature.productDetail.recycler.ImageData
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject

@HiltViewModel
class ProductDetailViewModel
@Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val shoppingCartRepository: ShoppingCartRepository,
    private val homeRepository: HomeRepository
) : BaseViewModel() {

    lateinit var currentColorMap: Map<String, String>
    private val _productDto = MutableSharedFlow<Resource<Product>>()
    val academyDto = _productDto.asSharedFlow()

    private val _addingCartFlow = MutableSharedFlow<String>()
    val addingCartFlow = _addingCartFlow.asSharedFlow()
    var both: Boolean? = null
    var onlyColor: Boolean? = null
    var onlySize: Boolean? = null
    var anyOne: Boolean? = null

    suspend fun getProductById(id: Int) {
        _productDto.emit(Resource.Loading())
        homeRepository.getProductDetail(id).collect {
            when (it) {
                is Resource.Success -> {
                    _productDto.emit(Resource.Success(it.data))
                }

                is Resource.Failure -> {
                    _productDto.emit(Resource.Failure(it.error))
                }

                else -> {}
            }
        }
    }

    fun getColorOFSize(size: String, product: Product): Map<String, String>? {
        val colorMap = mutableMapOf<String, String>()
        product.variants?.also {
            for (i in product.variants) {
                if (i?.size == size && i.colorRgb.isNotBlank() && i.isActive) {
                    colorMap[i.color] = i.colorRgb
                }
            }
        }
        return if (colorMap.isEmpty()) {
            null
        } else {
            currentColorMap = colorMap
            colorMap
        }
    }

    fun getColors(product: Product): Map<String, String>? {
        val colorMap = mutableMapOf<String, String>()
        product.variants?.also {
            for (i in product.variants) {
                i?.also {
                    if (i.colorRgb.isNotBlank() && i.isActive) {
                        colorMap[i.color] = i.colorRgb
                    }
                }
            }
        }
        return if (colorMap.isEmpty()) {
            null
        } else {
            currentColorMap = colorMap
            colorMap
        }
    }

    fun getSizeList(product: Product): Set<String>? {
        val sizeList = mutableSetOf<String>()
        product.variants?.also {
            for (i in product.variants) {
                if (i != null && i.isActive) {
                    sizeList.add(i.size)
                }
            }
        }
        return if (sizeList.isEmpty()) null else sizeList
    }


    fun getSelectedVariantId(product: Product, currentSizeIndex: Int, currentColorIndex: Int): Int {
        if (both == true) {
            val size = getSizeList(product)!!.toList()[currentSizeIndex]
            val color = getColorOFSize(size, product)!!.keys.toList()[currentColorIndex]
            var id = 0
            product.variants?.map {
                it?.also {
                    if (it.size == size && it.color == color) {
                        id = it.id
                    }
                }
            }
            return id
        } else if (onlySize == true) {
            val size = getSizeList(product)!!.toList()[currentSizeIndex]
            var id = 0
            product.variants?.map {
                it?.also {
                    if (it.size == size) {
                        id = it.id
                    }
                }
            }
            return id
        } else if (onlyColor == true) {
            val color = getColors(product)!!.keys.toList()[currentColorIndex]
            var id = 0
            product.variants?.map {
                it?.also {
                    if (it.color == color) {
                        id = it.id
                    }
                }
            }
            return id
        } else {
            return product.variants?.get(0)?.id ?: -1
        }
    }

    fun getSelectedVariantImage(
        product: Product,
        currentSizeIndex: Int,
        currentColorIndex: Int
    ): String? {
        if (both == true) {
            val size = getSizeList(product)!!.toList()[currentSizeIndex]
            val color = getColorOFSize(size, product)!!.keys.toList()[currentColorIndex]
            var img: String? = null
            product.variants?.map {
                it?.also {
                    if (it.size == size && it.color == color) {
                        img = it.image
                    }
                }
            }
            return img
        } else if (onlySize == true) {
            val size = getSizeList(product)!!.toList()[currentSizeIndex]
            var img: String? = null
            product.variants?.map {
                it?.also {
                    if (it.size == size) {
                        img = it.image
                    }
                }
            }
            return img
        } else if (onlyColor == true) {
            val color = getColors(product)!!.keys.toList()[currentColorIndex]
            var img: String? = null
            product.variants?.map {
                it?.also {
                    if (it.color == color) {
                        img = it.image
                    }
                }
            }
            return img
        } else {
            return product.variants?.get(0)?.image
        }
    }

    fun getSelectedVariant(
        product: Product,
        currentSizeIndex: Int,
        currentColorIndex: Int
    ): VariantsDto? {
        var variantsDto: VariantsDto? = null
        product.variants?.map {
            it?.also { v ->
                if (v.id == getSelectedVariantId(product, currentSizeIndex, currentColorIndex)) {
                    variantsDto = v
                }
            }
        }
        return variantsDto
    }

    fun getImageList(product: Product): List<RecyclerItem> {
        val list = mutableListOf<RecyclerItem>()
        product.also {
            if (it.video != null) {
                list.add(RecyclerItem(ImageData(it.videoThumbnail ?: "", true, it.video)))
            }
            product.images?.map { img ->
                var isExist = false
                list.map { image ->
                    if ((image.data as ImageData).imageUrl == img) {
                        isExist = true
                    }
                }
                if (!isExist) {
                    list.add(RecyclerItem(ImageData(img)))
                }
            }
            product.variants?.map { varient ->
                varient?.image?.also { img ->
                    var isExist = false
                    list.map { image ->
                        if ((image.data as ImageData).imageUrl == img) {
                            isExist = true
                        }
                    }
                    if (!isExist) {
                        list.add(RecyclerItem(ImageData(img)))
                    }
                }
            }
        }
        return list
    }


    suspend fun addCartItem(item: CartItem) {
        _addingCartFlow.emit("در حال اضافه کردن به سبد خرید.")
        shoppingCartRepository.addCartItem(item).collect {
            when (it) {
                is Resource.Success -> {
                    _addingCartFlow.emit("محصول مورد نظر با موفقیت به سبد خرید اضافه شد.")
                    getShoppingCart()
                }

                is Resource.Failure -> {
                    _addingCartFlow.emit("خطا در انجام عملیات.")
                }

                else -> {
                }
            }
        }
    }

    fun isProductInBasket(id: Int?) : Boolean{
        if (id == -1) return false
        shoppingCartRepository.basketList.map {
            if (it.product?.id  == id) return true
        }

        return false
    }

    suspend fun getShoppingCarts() {
        shoppingCartRepository.getShoppingCart().collect {
            when (it) {
                is Resource.Success -> {
                    if (!it.data.result.isEmpty()) {
                        shoppingCartRepository._shopBadge.emit(it.data.result[0].items.size)
                        shoppingCartRepository.basketList = it.data.result[0].items
                    } else {
                        shoppingCartRepository._shopBadge.emit(0)
                    }
                }

                is Resource.Failure -> {
                    shoppingCartRepository._shopBadge.emit(0)
                }

                else -> {
                    shoppingCartRepository._shopBadge.emit(0)
                }
            }
        }
    }

    suspend fun getShoppingCart() {
        shoppingCartRepository.getShoppingCart().collect {
            when (it) {
                is Resource.Success -> {
                    if (!it.data.result.isEmpty()) {
                        shoppingCartRepository.basketList = it.data.result[0].items
                    }
                }
                is Resource.Failure -> {

                }
                else -> {

                }
            }
        }
    }
}