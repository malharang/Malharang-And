package com.malharang.app.di

import com.malharang.app.domain.repository.DummyRepository
import com.malharang.app.domain.repository.PlaceTypeRepository
import com.malharang.app.domain.repository.SpeechRepository
import com.malharang.app.domain.usecase.DummyUseCase
import com.malharang.app.domain.usecase.PlaceTypeUseCase
import com.malharang.app.domain.repository.TranslateRepository
import com.malharang.app.domain.usecase.STTUseCase
import com.malharang.app.domain.usecase.TTSUseCase
import com.malharang.app.domain.usecase.TranslateUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {
    @Provides
    @Singleton
    fun provideDummyUseCase(
        dummyRepository: DummyRepository
    ): DummyUseCase = DummyUseCase(dummyRepository)

    @Provides
    @Singleton
    fun providePlaceTypeUseCase(
        placeTypeRepository: PlaceTypeRepository
    ): PlaceTypeUseCase = PlaceTypeUseCase(placeTypeRepository)

    @Provides
    @Singleton
    fun provideTranslateUseCase(
        translateRepository: TranslateRepository
    ): TranslateUseCase = TranslateUseCase(translateRepository)

    @Provides
    @Singleton
    fun provideSTTUseCase(
        speechRepository: SpeechRepository
    ): STTUseCase = STTUseCase(speechRepository)

    @Provides
    @Singleton
    fun provideTTSUseCase(
        speechRepository: SpeechRepository
    ): TTSUseCase = TTSUseCase(speechRepository)
}
