package ir.pattern.persianball.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import ir.pattern.persianball.data.model.Resource
import ir.pattern.persianball.data.model.academy.Academy
import ir.pattern.persianball.data.model.home.*
import ir.pattern.persianball.data.model.paging.PagingSourceSinglePage
import ir.pattern.persianball.data.remote.datasource.HomeRemoteDataSource
import ir.pattern.persianball.utils.singlePagingConfig
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

    lateinit var products : Products

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
}