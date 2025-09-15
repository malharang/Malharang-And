package com.malharang.app.di

import com.malharang.app.data.repositoryimpl.ChatRepositoryImpl
import com.malharang.app.data.repositoryimpl.ConversationRepositoryImpl
import com.malharang.app.data.repositoryimpl.ExportSentenceRepositoryImpl
import com.malharang.app.data.repositoryimpl.MessageRepositoryImpl
import com.malharang.app.data.repositoryimpl.PlaceTypeRepositoryImpl
import com.malharang.app.data.repositoryimpl.QuizRepositoryImpl
import com.malharang.app.data.repositoryimpl.ScenarioRepositoryImpl
import com.malharang.app.data.repositoryimpl.SpeechRepositoryImpl
import com.malharang.app.data.repositoryimpl.TranslateRepositoryImpl
import com.malharang.app.domain.repository.ChatRepository
import com.malharang.app.domain.repository.ConversationRepository
import com.malharang.app.domain.repository.ExportSentenceRepository
import com.malharang.app.domain.repository.MessageRepository
import com.malharang.app.domain.repository.PlaceTypeRepository
import com.malharang.app.domain.repository.QuizRepository
import com.malharang.app.domain.repository.ScenarioRepository
import com.malharang.app.domain.repository.SpeechRepository
import com.malharang.app.domain.repository.TranslateRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindPlaceTypeRepository(placeTypeRepositoryImpl: PlaceTypeRepositoryImpl): PlaceTypeRepository

    @Binds
    @Singleton
    abstract fun bindTranslateRepository(translateRepositoryImpl: TranslateRepositoryImpl): TranslateRepository

    @Binds
    @Singleton
    abstract fun bindSpeechRepository(speechRepositoryImpl: SpeechRepositoryImpl): SpeechRepository

    @Binds
    @Singleton
    abstract fun bindScenarioRepository(scenarioRepositoryImpl: ScenarioRepositoryImpl): ScenarioRepository

    @Binds
    @Singleton
    abstract fun bindChatRepository(chatRepositoryImpl: ChatRepositoryImpl): ChatRepository

    @Binds
    @Singleton
    abstract fun bindConversationRepository(
        conversationRepositoryImpl: ConversationRepositoryImpl
    ): ConversationRepository

    @Binds
    @Singleton
    abstract fun bindMessageRepository(
        messageRepositoryImpl: MessageRepositoryImpl
    ): MessageRepository

    @Binds
    @Singleton
    abstract fun bindExportSentenceRepository(
        exportSentenceRepositoryImpl: ExportSentenceRepositoryImpl
    ): ExportSentenceRepository

    @Binds
    @Singleton
    abstract fun bindQuizRepository(
        quizRepositoryImpl: QuizRepositoryImpl
    ): QuizRepository
}
