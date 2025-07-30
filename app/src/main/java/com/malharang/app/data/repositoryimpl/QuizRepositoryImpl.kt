package com.malharang.app.data.repositoryimpl

import com.malharang.app.data.mapper.todomain.toDomainList
import com.malharang.app.data.remote.datasource.QuizRemoteDataSource
import com.malharang.app.domain.model.MessageData
import com.malharang.app.domain.model.QuizData
import com.malharang.app.domain.repository.QuizRepository
import javax.inject.Inject

class QuizRepositoryImpl @Inject constructor(
    private val quizRemoteDataSource: QuizRemoteDataSource
) : QuizRepository {
    override suspend fun postQuiz(
        quizType: String,
        messages: List<MessageData>
    ): Result<List<QuizData>> = runCatching {
        quizRemoteDataSource.postQuiz(quizType, messages).toDomainList()
    }
}