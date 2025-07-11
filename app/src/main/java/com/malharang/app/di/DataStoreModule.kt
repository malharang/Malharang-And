package com.malharang.app.di

import android.content.Context
import com.malharang.app.data.local.datastore.ConversationDataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {

    @Provides
    @Singleton
    fun provideConversationDataStore(
        @ApplicationContext context: Context
    ): ConversationDataStore = ConversationDataStore(context)
}
