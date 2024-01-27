package ir.pattern.persianball.data.model.store

import ir.pattern.persianball.data.model.academy.AcademyDto
import ir.pattern.persianball.data.model.academy.AcademyHomeDto
import ir.pattern.persianball.data.model.home.Product

data class StoreDto(
    val isAcademy: Boolean,
    var academyDto: AcademyHomeDto? = null,
    var product: Product? = null
)
