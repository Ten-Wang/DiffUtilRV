package com.example.diffutilrv.hilt;

import com.example.diffutilrv.repository.EmployeeRepository;
import com.example.diffutilrv.repository.EmployeeRepositoryImpl;

import dagger.Binds;
import dagger.Module;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ApplicationComponent;

@Module
@InstallIn(ApplicationComponent.class)
public abstract class RepositoryModule {

    @Binds
    public abstract EmployeeRepository bindEmployeeRepository(
            EmployeeRepositoryImpl employeeRepositoryImpl);
}
