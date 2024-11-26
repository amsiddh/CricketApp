package com.cricketapp.data.di

import com.cricketapp.data.repository.MatchesRepositoryImpl
import com.cricketapp.domain.repository.MatchesRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    fun bindMatchesRepository(matchesRepositoryImpl: MatchesRepositoryImpl): MatchesRepository
}