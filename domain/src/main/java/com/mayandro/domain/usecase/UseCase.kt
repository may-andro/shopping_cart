package com.mayandro.domain.usecase

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

abstract class UseCase<in P, O: Any> {
    abstract suspend fun run(param: P): Flow<O>

    open operator fun invoke(
        scope: CoroutineScope,
        param: P,
        onResult: ((O) -> Unit) = {},
        onFailure: ((Throwable) -> Unit) = {}
    ) {
        scope.launch(Dispatchers.Main) {
            run(param)
                .flowOn(Dispatchers.IO)
                .catch { e ->
                    onFailure(e)
                }
                .collect {
                    onResult(it)
                }
        }
    }
}