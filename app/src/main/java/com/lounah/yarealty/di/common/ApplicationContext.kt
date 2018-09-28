package com.lounah.yarealty.di.common

import javax.inject.Qualifier

@Target(AnnotationTarget.VALUE_PARAMETER)
@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class ApplicationContext