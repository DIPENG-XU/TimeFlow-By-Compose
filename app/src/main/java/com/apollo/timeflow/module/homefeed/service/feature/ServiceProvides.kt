package com.apollo.timeflow.module.homefeed.service.feature

import com.apollo.timeflow.module.homefeed.service.featureImpl.ThemeService
import com.apollo.timeflow.module.homefeed.service.featureImpl.TimeDataService
import com.apollo.timeflow.module.homefeed.service.featureImpl.TimeFormatRecordDataStoreService
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
    fun providesIThemeService(impl: ThemeService): IThemeService

    @Singleton
    @Binds
    fun providesITimeDataService(impl: TimeDataService): ITimeDataService

    @Singleton
    @Binds
    fun providesITimeFormatRecordDataStoreService(impl: TimeFormatRecordDataStoreService): ITimeFormatRecordDataStoreService
}