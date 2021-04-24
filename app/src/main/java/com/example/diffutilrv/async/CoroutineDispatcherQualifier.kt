package com.example.diffutilrv.async

import javax.inject.Qualifier

@MustBeDocumented
@Retention(AnnotationRetention.RUNTIME)
annotation class CoroutineDispatcherQualifier {
    @Qualifier
    @MustBeDocumented
    @Retention(AnnotationRetention.RUNTIME)
    annotation class Io
}
