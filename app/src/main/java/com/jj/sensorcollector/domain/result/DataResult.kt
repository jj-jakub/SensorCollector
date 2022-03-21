package com.jj.sensorcollector.domain.result

import com.jj.sensorcollector.domain.result.DataResult.Error
import com.jj.sensorcollector.domain.result.DataResult.Success

sealed class DataResult<out T> {

    private var hasBeenHandled = false

    class Success<out T>(private val data: T?) : DataResult<T>() {
        val dataValue: T?
            get() = data
    }

    class Loading<out T>(private val data: T? = null) : DataResult<T>() {
        val dataValue: T?
            get() = data
    }

    class Error(val exception: Exception) : DataResult<Nothing>()

    fun getValue(): T? = if (this is Success) dataValue else null

    fun forceGetValue(): T = when (this) {
        is Success -> dataValue!!
        is Loading -> dataValue!!
        is Error -> throw exception
    }

    fun onSuccess(block: Success<T>.() -> Unit): DataResult<T> {
        if (this is Success && hasBeenHandled.not()) {
            block()
            hasBeenHandled = true
        }
        return this
    }

    fun onError(block: Error.() -> Unit): DataResult<T> {
        if (this is Error && hasBeenHandled.not()) {
            block()
            hasBeenHandled = true
        }
        return this
    }
}

suspend fun <T> getDataResult(block: suspend () -> T?): DataResult<T> {
    return try {
        Success(block.invoke())
    } catch (e: Exception) {
        e.printStackTrace()
        Error(e)
    }
}