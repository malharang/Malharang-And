package com.malharang.app.di

import com.malharang.app.data.remote.service.DummyService
import com.malharang.app.data.remote.service.TranslateService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
object ServiceModule {
    @Provides
    @Singleton
    fun providesDummyService(retrofit: Retrofit): DummyService =
        retrofit.create(DummyService::class.java)

    @Provides
    @Singleton
    fun providesTranslateService(
        @javax.inject.Named("Translate") retrofit: Retrofit
    ): TranslateService =
        retrofit.create(TranslateService::class.java)
}
