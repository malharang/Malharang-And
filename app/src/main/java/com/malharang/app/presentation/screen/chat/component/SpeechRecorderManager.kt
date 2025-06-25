package com.malharang.app.presentation.screen.chat.component

import android.content.Context
import android.media.MediaRecorder
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.File
import javax.inject.Inject

class SpeechRecorderManager @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private var recorder: MediaRecorder? = null
    private var outputFile: File? = null

    fun start(): String {
        outputFile = File(context.cacheDir, "recorded_audio.m4a")

        @Suppress("DEPRECATION")
        recorder = MediaRecorder().apply {
            setAudioSource(MediaRecorder.AudioSource.MIC)
            setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
            setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
            setAudioEncodingBitRate(128000)
            setAudioSamplingRate(44100)
            setOutputFile(outputFile!!.absolutePath)
            prepare()
            start()
        }

        return outputFile!!.absolutePath
    }

    fun stop(): String {
        recorder?.apply {
            stop()
            release()
        }
        recorder = null
        return outputFile!!.absolutePath
    }
}
