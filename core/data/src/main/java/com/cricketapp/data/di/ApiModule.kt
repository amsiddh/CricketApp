package com.cricketapp.data.di

import com.cricketapp.data.remote.api.MatchesApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    @Singleton
    @Provides
    fun provideMatchesApi(retrofit: Retrofit): MatchesApi = retrofit.create(MatchesApi::class.java)
}
