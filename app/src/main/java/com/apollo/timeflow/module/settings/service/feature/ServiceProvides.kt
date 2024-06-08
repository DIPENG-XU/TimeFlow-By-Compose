package com.apollo.timeflow.module.settings.service.feature

import com.apollo.timeflow.module.settings.service.featureImpl.DateFormatService
import com.apollo.timeflow.module.settings.service.featureImpl.SettingsService
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
    fun providesIThemeService(impl: DateFormatService): IDateFormatService

    @Singleton
    @Binds
    fun providesITimeDataService(impl: SettingsService): ISettingsService
}