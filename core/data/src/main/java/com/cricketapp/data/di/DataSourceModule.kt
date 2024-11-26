package com.cricketapp.data.di

import com.cricketapp.data.remote.datasource.MatchesRemoteDataSource
import com.cricketapp.data.remote.datasource.MatchesRemoteDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface DataSourceModule {
    @Binds
    fun bindMatchesRemoteDataSource(
        matchesRemoteDataSourceImpl: MatchesRemoteDataSourceImpl
    ): MatchesRemoteDataSource
}