package com.mayandro.remote.utils

import com.mayandro.utility.CONNECT_EXCEPTION
import com.mayandro.utility.SOCKET_TIME_OUT_EXCEPTION
import com.mayandro.utility.UNKNOWN_HOST_EXCEPTION
import com.mayandro.utility.UNKNOWN_NETWORK_EXCEPTION
import com.mayandro.utility.network.NetworkStatus
import org.json.JSONObject
import retrofit2.HttpException
import retrofit2.Response
import java.net.ConnectException
import java.net.SocketException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

suspend fun <T : Any> safeApiCall(call: suspend () -> Response<T>): NetworkStatus<T> {
    try {
        val response = call.invoke()
        when(response.isSuccessful) {
            true -> {
                if (response.body() != null) {
                    return NetworkStatus.Success(response.body())
                }
                return NetworkStatus.Error(response.message())
            }
            false -> {
                val serverMessage = response.errorBody()?.let {
                    try {
                        val jsonObject = JSONObject(it.charStream().readText())
                        if(jsonObject.has("userMessage"))
                            jsonObject.getString("userMessage")
                        else
                            response.message()
                    } catch (e: java.lang.Exception){
                        response.message()
                    }
                }

                return NetworkStatus.Error(serverMessage)
            }
        }
    } catch (e: Exception) {
        return when (e) {
            is ConnectException -> {
                NetworkStatus.Error(CONNECT_EXCEPTION)
            }
            is UnknownHostException -> {
                NetworkStatus.Error(UNKNOWN_HOST_EXCEPTION)
            }
            is SocketTimeoutException -> {
                NetworkStatus.Error(SOCKET_TIME_OUT_EXCEPTION)
            }
            is HttpException -> {
                NetworkStatus.Error(UNKNOWN_NETWORK_EXCEPTION)
            }
            is SocketException -> {
                NetworkStatus.Error(e.message)
            }
            else -> {
                NetworkStatus.Error(UNKNOWN_NETWORK_EXCEPTION)
            }
        }
    }
}