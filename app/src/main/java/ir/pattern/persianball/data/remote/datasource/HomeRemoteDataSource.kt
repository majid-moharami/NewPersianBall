package ir.pattern.persianball.data.remote.datasource

import ir.pattern.persianball.data.model.Resource
import ir.pattern.persianball.data.model.home.Courses
import ir.pattern.persianball.data.model.home.Gallery
import ir.pattern.persianball.data.model.home.Products
import ir.pattern.persianball.data.remote.api.HomeService
import ir.pattern.persianball.data.remote.api.Request
import ir.pattern.persianball.data.remote.api.UserService
import javax.inject.Inject

class HomeRemoteDataSource
@Inject constructor(
    private val homeService: HomeService
) {

    suspend fun getCourses() : Resource<Courses>{
        return Request.getResponse(request = {homeService.getCourse()})
    }

    suspend fun getProducts() : Resource<Products> {
        return Request.getResponse(request = {homeService.getProduct()})
    }

    suspend fun getGalley(): Resource<Gallery> {
        return Request.getResponse(request = {homeService.getGallery()})
    }

}