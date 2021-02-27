package com.mayandro.data.utils

import com.mayandro.utility.network.NetworkStatus
import kotlinx.coroutines.flow.*

inline fun <ResultType, RequestType> getDataAndCacheIt(
    crossinline query: () -> Flow<ResultType>,
    crossinline fetch: suspend () -> RequestType,
    crossinline saveFetchResult: suspend (RequestType) -> Unit,
    crossinline clearData: suspend () -> Unit,
    crossinline onFetchFailed: (Throwable) -> Unit = { Unit },
    crossinline shouldFetch: (ResultType) -> Boolean = { true },
    crossinline shouldClear: (RequestType, ResultType?) -> Boolean = { requestType: RequestType, resultType: ResultType? -> false }
) = flow<NetworkStatus<ResultType>> {

    emit(NetworkStatus.Loading(null))

    var getFromNetwork = false
    var dbData: ResultType? = null
    try {
        dbData = query().first()
        getFromNetwork = shouldFetch(dbData)
    } catch (throwable: Throwable) {
        getFromNetwork = true
    }
    val flow = if (getFromNetwork) {
        emit(NetworkStatus.Loading(dbData))
        try {
            val fetchData = fetch()

            if (shouldClear(fetchData, dbData)) {
                clearData()
            }
            saveFetchResult(fetchData)
            query().map {
                NetworkStatus.Success(it)
            }

        } catch (throwable: Throwable) {
            onFetchFailed(throwable)
            query().map { NetworkStatus.Error(throwable.message, it) }
        }

    } else {
        query().map { NetworkStatus.Success(it) }
    }

    emitAll(flow)
}