package com.malharang.app.di

import com.malharang.app.data.remote.datasource.DummyRemoteDataSource
import com.malharang.app.data.remote.datasource.SpeechRemoteDataSource
import com.malharang.app.data.remote.datasourceimpl.DummyRemoteDataSourceImpl
import com.malharang.app.data.remote.datasource.TranslateRemoteDataSource
import com.malharang.app.data.remote.datasourceimpl.TranslateRemoteDataSourceImpl
import com.malharang.app.data.remote.datasourceimpl.SpeechRemoteDataSourceImpl
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
    abstract fun bindsDummyDataSource(dummyRemoteDataSourceImpl: DummyRemoteDataSourceImpl): DummyRemoteDataSource

    @Binds
    @Singleton
    abstract fun bindsTranslateDataSource(translateRemoteDataSourceImpl: TranslateRemoteDataSourceImpl): TranslateRemoteDataSource

    @Binds
    @Singleton
    abstract fun bindsSpeechDataSource(speechRemoteDataSourceImpl: SpeechRemoteDataSourceImpl): SpeechRemoteDataSource
}
