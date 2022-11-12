package ir.pattern.persianball.data.remote.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class UserRetrofitBuilder {
    val retrofitBuilder: Retrofit.Builder = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())

    companion object {
        private const val BASE_URL = "https://api.persianball.ir/v1/"
    }
}