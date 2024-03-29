package ir.pattern.persianball.data.repository.remote.datasource

import ir.pattern.persianball.data.model.*
import ir.pattern.persianball.data.model.academy.Academy
import ir.pattern.persianball.data.model.home.*
import ir.pattern.persianball.data.repository.remote.api.HomeService
import ir.pattern.persianball.data.repository.remote.api.Request
import ir.pattern.persianball.data.model.ErrorTranslator
import ir.pattern.persianball.data.model.academy.AcademyDto
import ir.pattern.persianball.data.repository.remote.api.HomeVersionTwoService
import javax.inject.Inject

class HomeRemoteDataSource
@Inject constructor(
    private val homeService: HomeService,
    private val homeVersionTwoService: HomeVersionTwoService,
    private val errorTranslator: ErrorTranslator
) {

    suspend fun getCourses() : Resource<Academy>{
        return Request.getResponse(request = {homeVersionTwoService.getCourse(100)}, errorTranslator)
    }

    suspend fun getAcademy() : Resource<Academy>{
        return Request.getResponse(request = {homeVersionTwoService.getAcademy(100)}, errorTranslator)
    }

    suspend fun getCourseDetail(id: Int): Resource<AcademyDto>{
        return Request.getResponse(request = {homeVersionTwoService.getCourseDetail(id)}, errorTranslator)
    }

    suspend fun getProductDetail(id: Int): Resource<Product>{
        return Request.getResponse(request = {homeVersionTwoService.getProductDetail(id)}, errorTranslator)
    }

    suspend fun getProducts() : Resource<Products> {
        return Request.getResponse(request = {homeVersionTwoService.getProduct(100)}, errorTranslator)
    }

    suspend fun getGalley(): Resource<SliderList> {
        return Request.getResponse(request = {homeService.getGallery(50)}, errorTranslator)
    }

    suspend fun verifyUser(verifyUser: VerifyUser): Resource<TokenResultDto?>{
        return Request.getResponse(request = {homeService.verifyUser(verifyUser)}, errorTranslator)
    }

    suspend fun registerUser(signUpRequest: SignUp): Resource<Any?>{
        return Request.getResponse(request = {homeService.register(signUpRequest)}, errorTranslator)
    }

    suspend fun retryCode(retryCode: RetryCode): Resource<Any?>{
        return Request.getResponse(request = {homeService.retryCode(retryCode)}, errorTranslator)
    }

    suspend fun forgetPassword(forgetPassword: ForgetPassword): Resource<Any?>{
        return Request.getResponse(request = {homeService.forgetPassword(forgetPassword)}, errorTranslator)
    }

    suspend fun changePassword(changePassword: ChangePassword): Resource<Any?>{
        return Request.getResponse(request = {homeService.changePassword(changePassword)}, errorTranslator)
    }

}