package ir.pattern.persianball.data.repository

import ir.pattern.persianball.data.model.ChangePassword
import ir.pattern.persianball.data.model.ForgetPassword
import ir.pattern.persianball.data.model.Resource
import ir.pattern.persianball.data.model.RetryCode
import ir.pattern.persianball.data.model.SignUp
import ir.pattern.persianball.data.model.TokenResultDto
import ir.pattern.persianball.data.model.VerifyUser
import ir.pattern.persianball.data.model.academy.Academy
import ir.pattern.persianball.data.model.academy.AcademyDto
import ir.pattern.persianball.data.model.home.Product
import ir.pattern.persianball.data.model.home.Products
import ir.pattern.persianball.data.model.home.Slider
import ir.pattern.persianball.data.model.home.SliderList
import ir.pattern.persianball.data.repository.remote.datasource.HomeRemoteDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HomeRepository
@Inject constructor(
    private val homeRemoteDataSource: HomeRemoteDataSource
) {
//    suspend fun getCourses() : Flow<PagingData<Courses>> = Pager(
//        config = PagingConfig.singlePagingConfig(),
//        pagingSourceFactory = {
//            PagingSourceSinglePage(
//                networkCall = {homeRemoteDataSource.getCourses()}
//            )
//        }
//    ).flow

    lateinit var products: Products
    var courses: AcademyDto? = null
    lateinit var sliderList: List<Slider>

    suspend fun getCourses(): Flow<Resource<Academy>> {
        return flow {
            val result = homeRemoteDataSource.getCourses()
            emit(result)
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getProducts(): Flow<Resource<Products>> {
        return flow {
            val result = homeRemoteDataSource.getProducts()
            emit(result)
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getGallery(): Flow<Resource<SliderList>> {
        return flow {
            val result = homeRemoteDataSource.getGalley()
            emit(result)
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getAcademy(): Flow<Resource<Academy>> {
        return flow {
            val result = homeRemoteDataSource.getAcademy()
            emit(result)
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getCourseDetail(id: Int): Flow<Resource<AcademyDto>>{
        return flow {
            val result = homeRemoteDataSource.getCourseDetail(id)
            emit(result)
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getProductDetail(id: Int): Flow<Resource<Product>>{
        return flow {
            val result = homeRemoteDataSource.getProductDetail(id)
            emit(result)
        }.flowOn(Dispatchers.IO)
    }

    suspend fun verifyUser(verifyUser: VerifyUser): Flow<Resource<TokenResultDto?>> {
        return flow {
            val result = homeRemoteDataSource.verifyUser(verifyUser)
            emit(result)
        }.flowOn(Dispatchers.IO)
    }

    suspend fun registerUser(signUpRequest: SignUp): Flow<Resource<Any?>> {
        return flow {
            val result = homeRemoteDataSource.registerUser(signUpRequest)
            emit(result)
        }.flowOn(Dispatchers.IO)
    }

    suspend fun retryCode(retryCode: RetryCode): Flow<Resource<Any?>> {
        return flow {
            val result = homeRemoteDataSource.retryCode(retryCode)
            emit(result)
        }.flowOn(Dispatchers.IO)
    }

    suspend fun forgetPassword(forgetPassword: ForgetPassword): Flow<Resource<Any?>> {
        return flow {
            val result = homeRemoteDataSource.forgetPassword(forgetPassword)
            emit(result)
        }.flowOn(Dispatchers.IO)
    }

    suspend fun changePassword(changePassword: ChangePassword): Flow<Resource<Any?>> {
        return flow {
            val result = homeRemoteDataSource.changePassword(changePassword)
            emit(result)
        }.flowOn(Dispatchers.IO)
    }

//    fun getCourseById(id: Int): AcademyDto? {
//        if (courses != null) {
//            for (i in courses!!.result) {
//                if (i.id == id) return i
//            }
//            return null
//        }else{
//            return null
//        }
//    }
}