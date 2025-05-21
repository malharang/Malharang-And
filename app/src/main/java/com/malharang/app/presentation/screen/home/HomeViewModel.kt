package com.malharang.app.presentation.screen.home

import androidx.lifecycle.ViewModel
import com.malharang.app.domain.usecase.DummyUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val dummyUseCase: DummyUseCase
) : ViewModel()
