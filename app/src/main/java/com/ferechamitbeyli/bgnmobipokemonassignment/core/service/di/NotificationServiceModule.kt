package com.ferechamitbeyli.bgnmobipokemonassignment.core.service.di

import android.app.NotificationManager
import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ServiceComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ServiceScoped

@Module
@InstallIn(ServiceComponent::class)
class NotificationServiceModule {

    @[Provides ServiceScoped]
    fun provideNotificationManager(
        @ApplicationContext appContext: Context
    ): NotificationManager =
        appContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

}