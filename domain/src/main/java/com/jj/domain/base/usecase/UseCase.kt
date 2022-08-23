package com.jj.domain.base.usecase

interface UseCase<Input, Output> {
    suspend operator fun invoke(param: Input): Output
}

suspend operator fun <T> UseCase<Unit, T>.invoke() = invoke(Unit)
