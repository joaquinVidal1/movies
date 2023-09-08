package com.example.movies.domain.usecase.base

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import com.example.movies.data.Result

abstract class CoroutineUseCase<in P, R>(private val coroutineDispatcher: CoroutineDispatcher = Dispatchers.IO) {
    suspend operator fun invoke(params: P): Result<R> = try {
        withContext(coroutineDispatcher) {
            execute(params).let {
                Result.Success(it)
            }
        }
    } catch (throwable: Throwable) {
        Result.Error(throwable.message, throwable)
    }

    internal abstract suspend fun execute(params: P): R
}
