package com.jj.core.domain.result

@Deprecated("Old implementation")
sealed class UseCaseResult<out T> {

    private var hasBeenHandled = false

    class Success<out T>(private val data: T) : UseCaseResult<T>() {
        val dataValue: T
            get() = data
    }

    class Loading<out T>(private val data: T? = null) : UseCaseResult<T>() {
        val dataValue: T?
            get() = data
    }

    class Error<out T>(private val data: T, val exception: Exception) : UseCaseResult<T>() {
        val dataValue: T
            get() = data
    }

    fun getValue() = when (this) {
        is Success -> dataValue
        is Error -> dataValue
        is Loading -> dataValue
    }

    fun onSuccess(block: Success<T>.() -> Unit): UseCaseResult<T> {
        if (this is Success && hasBeenHandled.not()) {
            block()
            hasBeenHandled = true
        }
        return this
    }

    fun onError(block: Error<T>.() -> Unit): UseCaseResult<T> {
        if (this is Error && hasBeenHandled.not()) {
            block()
            hasBeenHandled = true
        }
        return this
    }
}