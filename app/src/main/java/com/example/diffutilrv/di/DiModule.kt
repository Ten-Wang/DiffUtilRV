package com.example.diffutilrv.di

import com.example.diffutilrv.api.ApiService
import com.example.diffutilrv.api.DummyApiService
import com.example.diffutilrv.repository.EmployeeRepository
import com.example.diffutilrv.usecase.GetEmployeeListUseCase
import com.example.diffutilrv.viewmodel.EmployeeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module

class DiModule {

    private val networkModule = module {
        single<ApiService> { DummyApiService() }
    }

    private val repositoryModule = module {
        factory { EmployeeRepository(api = get()) }
    }

    private val useCaseModule = module {
        factory { GetEmployeeListUseCase(employeeRepository = get()) }
    }

    private val viewModelModule = module {
        viewModel { EmployeeViewModel(getEmployeeListUseCase = get()) }
    }

    fun init() {
        startKoin {
            modules(
                listOf(
                    networkModule,
                    repositoryModule,
                    useCaseModule,
                    viewModelModule
                )
            )
        }
    }
}