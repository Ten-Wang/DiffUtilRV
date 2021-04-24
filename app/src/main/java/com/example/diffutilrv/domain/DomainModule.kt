package com.example.diffutilrv.domain

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface DomainModule {
    @Binds
    fun bindGetEmployeeUseCase(getEmployeeUseCaseImpl: GetEmployeeUseCaseImpl): GetEmployeeUseCase
}