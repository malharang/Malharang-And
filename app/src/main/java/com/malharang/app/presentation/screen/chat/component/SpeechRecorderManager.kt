package com.malharang.app.presentation.screen.chat.component

import android.content.Context
import android.media.MediaPlayer
import android.media.MediaRecorder
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.ResponseBody
import timber.log.Timber
import java.io.File
import javax.inject.Inject

class SpeechRecorderManager @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private var recorder: MediaRecorder? = null
    private var outputFile: File? = null

    private var mediaPlayer: MediaPlayer? = null

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

    fun playTTSStream(responseBody: ResponseBody, onComplete: () -> Unit) {
        try {
            val tempFile = File.createTempFile("tts_output", ".mp3", context.cacheDir)
            tempFile.outputStream().use { outputStream ->
                responseBody.byteStream().use { inputStream ->
                    inputStream.copyTo(outputStream)
                }
            }

            mediaPlayer = MediaPlayer().apply {
                setDataSource(tempFile.absolutePath)
                prepare()
                start()
                setOnCompletionListener {
                    onComplete()
                    it.release()
                    tempFile.delete()
                }
            }

        } catch (e: Exception) {
            Timber.tag("TTS_TEST").e(e, "TTS 스트림 재생 실패")
        }
    }

    fun stopTTS(onComplete: () -> Unit = {}) {
        mediaPlayer?.let {
            if (it.isPlaying) {
                it.stop()
                it.release()
                mediaPlayer = null
                onComplete()
            }
        }
    }
}
