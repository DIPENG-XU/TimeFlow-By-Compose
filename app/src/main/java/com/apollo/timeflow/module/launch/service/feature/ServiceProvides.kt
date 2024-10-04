package com.apollo.timeflow.module.launch.service.feature

import com.apollo.timeflow.module.launch.service.featureImpl.SplashService
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
interface ServiceProvides {
    @Singleton
    @Binds
    fun providesISplashService(impl: SplashService): ISplashService
}