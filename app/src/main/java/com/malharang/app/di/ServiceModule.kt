package com.malharang.app.di

import com.malharang.app.data.remote.service.ScenarioService
import com.malharang.app.data.remote.service.SpeechService
import com.malharang.app.data.remote.service.TranslateService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ServiceModule {
    @Provides
    @Singleton
    fun providesTranslateService(
        @javax.inject.Named("Translate") retrofit: Retrofit
    ): TranslateService =
        retrofit.create(TranslateService::class.java)

    @Provides
    @Singleton
    fun providesSpeechService(
        @javax.inject.Named("Speech") retrofit: Retrofit
    ): SpeechService =
        retrofit.create(SpeechService::class.java)

    @Provides
    @Singleton
    fun providesScenarioService(retrofit: Retrofit): ScenarioService =
        retrofit.create(ScenarioService::class.java)
}
