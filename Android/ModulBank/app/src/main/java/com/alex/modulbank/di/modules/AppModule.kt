package com.alex.modulbank.di.modules

import com.alex.modulbank.api.IAuthService
import com.alex.modulbank.api.IBankOperationsService
import com.alex.modulbank.api.IUserService
import com.alex.modulbank.models.PrefsManager
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
class AppModule {

    private val API_URL = "https://198.168.0.108/"

    @Provides
    @Singleton
    @Named("auth")
    fun provideRetrofitAuth():Retrofit{
        return Retrofit.Builder()
            .baseUrl(API_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideAuthService(@Named("auth") retrofit:Retrofit): IAuthService {
        return retrofit.create(IAuthService::class.java)
    }

    @Provides
    @Singleton
    fun provideUserService(@Named("withHeaders")retrofit:Retrofit): IUserService {
        return retrofit.create(IUserService::class.java)
    }

    @Provides
    @Singleton
    fun provideBankOperationsService(@Named("withHeaders")retrofit:Retrofit): IBankOperationsService {
        return retrofit.create(IBankOperationsService::class.java)
    }

    @Provides
    @Singleton
    @Named("withHeaders")
    fun provideRetrofitUser(httpClient: OkHttpClient.Builder):Retrofit{
        return Retrofit.Builder()
            .baseUrl(API_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient.build())
            .build()
    }

    @Provides
    @Singleton
    fun provideHttpClient(prefs: PrefsManager):OkHttpClient.Builder{

        //TODO: (un)comment
        val token = prefs.getAuthData()
        val httpClient = OkHttpClient.Builder()

        httpClient.addInterceptor { chain ->
            val request: Request =
                chain.request()
                    .newBuilder()
                    .addHeader("ID", token!!.userId)
                    .addHeader("Authorization", "Bearer "+token.token)
                    .build()
            chain.proceed(request)
        }

        return httpClient
    }

}


