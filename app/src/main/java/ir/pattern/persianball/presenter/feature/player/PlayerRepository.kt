package ir.pattern.persianball.presenter.feature.player

import ir.pattern.persianball.data.model.academy.VariantDto
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PlayerRepository @Inject constructor() {
    var soldVariant : VariantDto? = null
    var videoUrl: String? = null
}