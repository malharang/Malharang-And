package com.malharang.app.presentation.screen.chat.sideeffect

enum class MicState(val trigger: String?) {
    Idle(null),
    StartRecording("startRecord"),
    StartProcessing("startProcess"),
    EndRecording("endRecord")
}
