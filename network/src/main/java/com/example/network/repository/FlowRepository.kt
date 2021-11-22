package com.example.network.repository

import com.example.utility.state.Result
import kotlinx.coroutines.flow.*

interface FlowRepository<TInput : Any, TOutput : Any> {
    fun flow(input: TInput): Flow<Result<TOutput>> = flowInternal(input)
        .map { Result.Success(it) as Result<TOutput> }
        .onStart { emit(Result.Progress) }
        .catch { emit(Result.Failure(it)) }
        .onCompletion { emit(Result.Complete) }

    fun flowInternal(input: TInput): Flow<TOutput>
}

fun <T : Any, R : Any> Flow<Result<T>>.mapResult(mapper: (T) -> R): Flow<Result<R>> = map {
    when (it) {
        is Result.Success<T> -> Result.Success(mapper(it.value))
        else -> it as Result<R>
    }
}
