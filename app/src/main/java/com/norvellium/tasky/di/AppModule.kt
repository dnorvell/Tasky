package com.norvellium.tasky.di

import android.app.Application
import android.content.Context
import com.norvellium.tasky.preferences.TokenPreferences
import com.norvellium.tasky.preferences.TokenPreferencesImpl
import com.norvellium.tasky.repository.AuthRepository
import com.norvellium.tasky.repository.AuthRepositoryImpl
import com.norvellium.tasky.web.NetworkResponseAdapterFactory
import com.norvellium.tasky.web.TaskyApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideTaskyApi(): TaskyApi {

        val authToken = "" // TODO

        val client = OkHttpClient.Builder().addInterceptor { chain ->
            val request: Request =
                chain.request().newBuilder()
                    .addHeader("x-api-key", TaskyApi.API_KEY)
                    .addHeader("Authorization", "Bearer $authToken")
                    .build()
            chain.proceed(request)
        }.build()

        return Retrofit.Builder()
            .baseUrl(TaskyApi.BASE_URL)
            .client(client)
            .addCallAdapterFactory(NetworkResponseAdapterFactory())
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create(TaskyApi::class.java)
    }

    @Provides
    @Singleton
    fun provideAuthRepository(api: TaskyApi): AuthRepository {
        return AuthRepositoryImpl(api)
    }

    @Provides
    @Singleton
    fun provideTokenPreferences(@ApplicationContext context: Context): TokenPreferences {
        return TokenPreferencesImpl(context)
    }

}