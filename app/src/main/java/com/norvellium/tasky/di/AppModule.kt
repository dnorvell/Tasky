package com.norvellium.tasky.di

import android.content.Context
import com.norvellium.tasky.BuildConfig
import com.norvellium.tasky.core.web.ApiKeyInterceptor
import com.norvellium.tasky.preferences.TokenPreferences
import com.norvellium.tasky.preferences.TokenPreferencesImpl
import com.norvellium.tasky.repository.AuthRepository
import com.norvellium.tasky.repository.AuthRepositoryImpl
import com.norvellium.tasky.core.web.NetworkResponseAdapterFactory
import com.norvellium.tasky.core.web.TaskyApi
import com.norvellium.tasky.core.web.TokenInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideTokenInterceptor(tokenPreferences: TokenPreferences): TokenInterceptor {
        return TokenInterceptor(tokenPreferences)
    }

    @Provides
    @Singleton
    fun provideApiKeyInterceptor(): ApiKeyInterceptor {
        return ApiKeyInterceptor(BuildConfig.API_KEY)
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(
        tokenInterceptor: TokenInterceptor,
        apiKeyInterceptor: ApiKeyInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY})
            .addInterceptor(apiKeyInterceptor)
            .addInterceptor(tokenInterceptor)
            .build()
    }

    @Provides
    @Singleton
    fun provideTaskyApi(client: OkHttpClient): TaskyApi {
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