package com.example.utility.coroutines

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

sealed class Data<out T : Any> {
    sealed class State : Data<Nothing>() {
        override fun toString(): String = this::class.java.simpleName
    }

    object Empty : State()
    object Progress : State()
    object Complete : State()
    data class Failure(val error: Throwable) : State()
    data class Success<T : Any>(val value: T) : Data<T>()

    fun isState() = this is State
    fun isNotState() = this !is State
    fun isSuccess() = this is Success
    fun isFailure() = this is Failure
}

fun <T : Any, R : Any> Flow<Data<T>>.mapResult(mapper: (T) -> R): Flow<Data<R>> = map {
    when (it) {
        is Data.Success<T> -> Data.Success(mapper(it.value))
        else -> it as Data.State
    }
}
