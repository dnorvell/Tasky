package com.norvellium.tasky.di

import android.content.Context
import com.norvellium.tasky.auth.data.local.TokenPreferencesImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object TestAppModule {


    @Provides
    fun provideTokenPreferences(@ApplicationContext context: Context) = TokenPreferencesImpl(context)

}