package com.malharang.app.di

import com.malharang.app.data.remote.datasource.ChatRemoteDataSource
import com.malharang.app.data.remote.datasource.EvaluationRemoteDataSource
import com.malharang.app.data.remote.datasource.QuizRemoteDataSource
import com.malharang.app.data.remote.datasource.ScenarioRemoteDataSource
import com.malharang.app.data.remote.datasource.SpeechRemoteDataSource
import com.malharang.app.data.remote.datasource.TranslateRemoteDataSource
import com.malharang.app.data.remote.datasourceimpl.ChatRemoteDataSourceImpl
import com.malharang.app.data.remote.datasourceimpl.EvaluationRemoteDataSourceImpl
import com.malharang.app.data.remote.datasourceimpl.QuizRemoteDataSourceImpl
import com.malharang.app.data.remote.datasourceimpl.ScenarioRemoteDataSourceImpl
import com.malharang.app.data.remote.datasourceimpl.SpeechRemoteDataSourceImpl
import com.malharang.app.data.remote.datasourceimpl.TranslateRemoteDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {
    @Binds
    @Singleton
    abstract fun bindsTranslateDataSource(translateRemoteDataSourceImpl: TranslateRemoteDataSourceImpl): TranslateRemoteDataSource

    @Binds
    @Singleton
    abstract fun bindsSpeechDataSource(speechRemoteDataSourceImpl: SpeechRemoteDataSourceImpl): SpeechRemoteDataSource

    @Binds
    @Singleton
    abstract fun bindsScenarioDataSource(scenarioRemoteDataSourceImpl: ScenarioRemoteDataSourceImpl): ScenarioRemoteDataSource

    @Binds
    @Singleton
    abstract fun bindsChatDataSource(chatRemoteDataSourceImpl: ChatRemoteDataSourceImpl): ChatRemoteDataSource

    @Binds
    @Singleton
    abstract fun bindsEvaluationDataSource(evaluationRemoteDataSourceImpl: EvaluationRemoteDataSourceImpl): EvaluationRemoteDataSource

    @Binds
    @Singleton
    abstract fun bindsQuizDataSource(quizRemoteDataSourceImpl: QuizRemoteDataSourceImpl): QuizRemoteDataSource
}
