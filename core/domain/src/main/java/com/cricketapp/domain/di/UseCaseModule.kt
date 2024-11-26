package com.cricketapp.domain.di

import com.cricketapp.domain.usecase.GetMatchesUseCase
import com.cricketapp.domain.usecase.GetCurrentMatchesUseCaseImpl
import com.cricketapp.domain.usecase.GetMatchDetailsUseCase
import com.cricketapp.domain.usecase.GetMatchDetailsUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
interface UseCaseModule {
    @Binds
    fun bindGetCurrentMatchesUseCase(
        currentMatchesUseCaseImpl: GetCurrentMatchesUseCaseImpl
    ): GetMatchesUseCase

    @Binds
    fun bindGetMatchDetailsUseCase(
        matchInfoUseCaseImpl: GetMatchDetailsUseCaseImpl
    ): GetMatchDetailsUseCase
}