package com.example.diffutilrv.async

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

@Module
@InstallIn(SingletonComponent::class)
object CoroutineDispatcherModule {
    @Provides
    @CoroutineDispatcherQualifier.Io
    fun provideIoDispatcher(): CoroutineDispatcher = Dispatchers.IO
}