package ir.pattern.persianball.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ir.pattern.persianball.data.repository.remote.api.AppApi
import ir.pattern.persianball.data.repository.remote.okhttp.AuthenticationRefreshToken
import okhttp3.Dispatcher
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
//
//    @Singleton
//    @Provides
//    fun provideAppApi(okHttpClient: OkHttpClient): AppApi {
//        return Retrofit.Builder()
//            .client(okHttpClient)
//            .baseUrl("")
//            .addConverterFactory(GsonConverterFactory.create())
//            .build()
//            .create(AppApi::class.java)
//    }
//
//    @Singleton
//    @Provides
//    fun provideUserApi(okHttpClient: OkHttpClient): AppApi {
//        return Retrofit.Builder()
//            .client(okHttpClient)
//            .baseUrl("")
//            .addConverterFactory(GsonConverterFactory.create())
//            .build()
//            .create(AppApi::class.java)
//    }
//
//    @Provides
//    fun provideOkHttpClient(authRefreshToken: AuthenticationRefreshToken): OkHttpClient =
//        OkHttpClient.Builder()
//            .addInterceptor(authRefreshToken)
//            .readTimeout(5, TimeUnit.SECONDS)
//            .connectTimeout(5, TimeUnit.SECONDS)
//            .callTimeout(5, TimeUnit.SECONDS)
//            .retryOnConnectionFailure(true)
//            .dispatcher(Dispatcher().apply { maxRequests = 1 })
//            .build()
}