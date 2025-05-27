package com.malharang.app.presentation.screen.mission

import androidx.lifecycle.ViewModel
import com.malharang.app.domain.usecase.DummyUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MissionViewModel @Inject constructor(
    private val dummyUseCase: DummyUseCase
) : ViewModel()
