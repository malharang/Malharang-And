package com.malharang.app.presentation.model

enum class MicState(val trigger: String?) {
    Idle(null),
    StartRecording("startRecord"),
    StartProcessing("startProcess"),
    EndRecording("endRecord")
}