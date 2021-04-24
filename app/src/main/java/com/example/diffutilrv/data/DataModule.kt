package com.example.diffutilrv.data

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {
    @Binds
    fun bindEmployeeDataSource(employeeDataSourceImpl: EmployeeDataSourceImpl): EmployeeDataSource
}
