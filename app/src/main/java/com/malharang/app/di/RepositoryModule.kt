package com.malharang.app.di

import com.malharang.app.data.repositoryimpl.DummyRepositoryImpl
import com.malharang.app.data.repositoryimpl.PlaceTypeRepositoryImpl
import com.malharang.app.domain.repository.DummyRepository
import com.malharang.app.domain.repository.PlaceTypeRepository
import com.malharang.app.data.repositoryimpl.TranslateRepositoryImpl
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
    abstract fun bindDummyRepository(dummyRepositoryImpl: DummyRepositoryImpl): DummyRepository

    @Binds
    @Singleton
    abstract fun bindPlaceTypeRepository(placeTypeRepositoryImpl: PlaceTypeRepositoryImpl): PlaceTypeRepository

    @Binds
    @Singleton
    abstract fun bindTranslateRepository(translateRepositoryImpl: TranslateRepositoryImpl): TranslateRepository
}
