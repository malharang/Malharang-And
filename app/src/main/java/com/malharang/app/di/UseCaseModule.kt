package com.malharang.app.di

import com.malharang.app.domain.repository.ChatRepository
import com.malharang.app.domain.repository.PlaceTypeRepository
import com.malharang.app.domain.repository.QuizRepository
import com.malharang.app.domain.repository.ScenarioRepository
import com.malharang.app.domain.repository.SpeechRepository
import com.malharang.app.domain.repository.TranslateRepository
import com.malharang.app.domain.usecase.ChatUseCase
import com.malharang.app.domain.usecase.PlaceTypeUseCase
import com.malharang.app.domain.usecase.QuizUseCase
import com.malharang.app.domain.usecase.STTUseCase
import com.malharang.app.domain.usecase.ScenarioUseCase
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

    @Provides
    @Singleton
    fun provideScenarioUseCase(
        scenarioRepository: ScenarioRepository
    ): ScenarioUseCase = ScenarioUseCase(scenarioRepository)

    @Provides
    @Singleton
    fun provideChatUseCase(
        chatRepository: ChatRepository
    ): ChatUseCase = ChatUseCase(chatRepository)

    @Provides
    @Singleton
    fun provideQuizUseCase(
        quizRepository: QuizRepository
    ): QuizUseCase = QuizUseCase(quizRepository)
}
