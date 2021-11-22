package com.example.utility.state

sealed class Result<out T : Any> {
    sealed class State : Result<Nothing>() {
        override fun toString(): String = this::class.java.simpleName
    }

    object Empty : State()
    object Progress : State()
    object Complete : State()
    data class Failure(val error: Throwable) : State()
    data class Success<T : Any>(val value: T) : Result<T>()
}
