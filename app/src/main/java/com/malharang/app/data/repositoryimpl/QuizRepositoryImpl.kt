package com.malharang.app.data.repositoryimpl

import com.malharang.app.data.mapper.todomain.toDomainList
import com.malharang.app.data.remote.datasource.QuizRemoteDataSource
import com.malharang.app.domain.model.MessageData
import com.malharang.app.domain.model.QuizData
import com.malharang.app.domain.repository.QuizRepository
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.inject.Inject

class QuizRepositoryImpl @Inject constructor(
    private val quizRemoteDataSource: QuizRemoteDataSource
) : QuizRepository {
    override suspend fun postQuiz(
        quizType: String,
        messages: List<MessageData>
    ): Result<List<QuizData>> = runCatching {
        quizRemoteDataSource.postQuiz(quizType, messages).toDomainList()
    }.recover { exception ->
        when (exception) {
            is SocketTimeoutException -> {
                throw Exception("퀴즈 생성에 시간이 오래 걸리고 있습니다. 잠시 후 다시 시도해주세요.")
            }
            is UnknownHostException -> {
                throw Exception("네트워크 연결을 확인해주세요.")
            }
            else -> {
                throw Exception("퀴즈 생성 중 오류가 발생했습니다: ${exception.localizedMessage}")
            }
        }
    }
}
