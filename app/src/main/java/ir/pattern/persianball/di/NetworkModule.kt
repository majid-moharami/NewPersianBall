package ir.pattern.persianball.di

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ir.pattern.persianball.data.repository.remote.okhttp.AuthenticationRefreshToken
import ir.pattern.persianball.data.repository.remote.api.*
import ir.pattern.persianball.manager.TokenAuthenticator
import ir.pattern.persianball.manager.TokenInterceptor
import okhttp3.Dispatcher
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    fun provideOkHttpClient(
        @ApplicationContext context: Context,
        tokenInterceptor: TokenInterceptor,
        tokenAuthenticator: TokenAuthenticator
    ): OkHttpClient {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY

        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY

        val chuckerInterceptor = ChuckerInterceptor.Builder(context)
            .collector(ChuckerCollector(context,showNotification = true))
            .maxContentLength(250000L)
            .redactHeaders(emptySet())
            .alwaysReadResponseBody(false)
            .build()

        return OkHttpClient.Builder()
            .readTimeout(10, TimeUnit.SECONDS)
            .connectTimeout(10, TimeUnit.SECONDS)
            .callTimeout(10, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
            .dispatcher(Dispatcher().apply { maxRequests = 1 })
            .addInterceptor(chuckerInterceptor)
            .addInterceptor(logging)
            .authenticator(tokenAuthenticator)
            .addInterceptor(tokenInterceptor)
            .build()
    }

    private fun provideOkHttp(@ApplicationContext context: Context) : OkHttpClient {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY

        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY

        val chuckerInterceptor = ChuckerInterceptor.Builder(context)
            .collector(ChuckerCollector(context,showNotification = true))
            .maxContentLength(250000L)
            .redactHeaders(emptySet())
            .alwaysReadResponseBody(false)
            .build()

        return OkHttpClient.Builder()
            .readTimeout(10, TimeUnit.SECONDS)
            .connectTimeout(10, TimeUnit.SECONDS)
            .callTimeout(10, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
            .dispatcher(Dispatcher().apply { maxRequests = 1 })
            .addInterceptor(chuckerInterceptor)
            .addInterceptor(logging)
            .build()
    }


    @Singleton
    @Provides
    fun provideLoginBuilder(): LoginRetrofitBuilder = LoginRetrofitBuilder()

    @Singleton
    @Provides
    fun provideUsersBuilder(): UserRetrofitBuilder = UserRetrofitBuilder()

    @Singleton
    @Provides
    fun provideTwoVersionBuilder(): VersionTwoRetrofitBuilder = VersionTwoRetrofitBuilder()

    @Singleton
    @Provides
    fun provideUserApi(
        userRetrofitBuilder: UserRetrofitBuilder,
        okHttpClient: OkHttpClient
    ): UserService {
        return userRetrofitBuilder.retrofitBuilder
            .client(okHttpClient)
            .build()
            .create(UserService::class.java)
    }

    @Singleton
    @Provides
    fun provideShoppingCartApi(
        userRetrofitBuilder: UserRetrofitBuilder,
        okHttpClient: OkHttpClient
    ): ShoppingCartService {
        return userRetrofitBuilder.retrofitBuilder
            .client(okHttpClient)
            .build()
            .create(ShoppingCartService::class.java)
    }

    @Singleton
    @Provides
    fun provideDashboardApi(
        userRetrofitBuilder: UserRetrofitBuilder,
        okHttpClient: OkHttpClient
    ): DashboardService {
        return userRetrofitBuilder.retrofitBuilder
            .client(okHttpClient)
            .build()
            .create(DashboardService::class.java)
    }

    @Singleton
    @Provides
    fun provideLoginApi(
        loginRetrofitBuilder: LoginRetrofitBuilder,
        @ApplicationContext context: Context,
        okHttpClient: OkHttpClient
    ): LoginService {
        //val okHttpClient = provideOkHttp(context)
        return loginRetrofitBuilder.retrofitBuilder
            .client(okHttpClient)
            .build()
            .create(LoginService::class.java)
    }

    @Singleton
    @Provides
    fun provideHomeApi(
        userRetrofitBuilder: UserRetrofitBuilder,
        @ApplicationContext context: Context
    ) : HomeService {
        val okHttpClient = provideOkHttp(context)
        return userRetrofitBuilder.retrofitBuilder
            .client(okHttpClient)
            .build()
            .create(HomeService::class.java)
    }

    @Singleton
    @Provides
    fun provideHomeVersionTwo(
        versionTwoRetrofitBuilder: VersionTwoRetrofitBuilder,
        @ApplicationContext context: Context
    ) : HomeVersionTwoService {
        val okHttpClient = provideOkHttp(context)
        return versionTwoRetrofitBuilder.retrofitBuilder
            .client(okHttpClient)
            .build()
            .create(HomeVersionTwoService::class.java)
    }
}