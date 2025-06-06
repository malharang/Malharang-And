package com.malharang.app.di

import com.malharang.app.data.repositoryimpl.DummyRepositoryImpl
import com.malharang.app.data.repositoryimpl.PlaceTypeRepositoryImpl
import com.malharang.app.domain.repository.DummyRepository
import com.malharang.app.domain.repository.PlaceTypeRepository
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
}
