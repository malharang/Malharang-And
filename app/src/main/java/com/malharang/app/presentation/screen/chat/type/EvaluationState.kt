package com.malharang.app.presentation.screen.chat.type

import androidx.annotation.DrawableRes
import com.malharang.app.R.drawable.ic_caution_14
import com.malharang.app.R.drawable.ic_check_22

enum class EvaluationState(
    @DrawableRes val icon: Int,
) {
    EMPTY(icon = 0),
    Loading(icon = 0),
    PASS(icon = ic_check_22),
    NOT_PASS(icon = ic_caution_14),
}