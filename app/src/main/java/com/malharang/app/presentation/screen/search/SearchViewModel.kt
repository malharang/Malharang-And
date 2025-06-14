package com.malharang.app.presentation.screen.search

import androidx.lifecycle.ViewModel
import com.malharang.app.domain.usecase.DummyUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val useCase: DummyUseCase
) : ViewModel() {
    val

}