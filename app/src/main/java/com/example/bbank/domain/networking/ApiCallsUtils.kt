package com.example.bbank.domain.networking

import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

internal suspend fun <T> safeApiCall(
    apiCall: suspend () -> Response<T>
): Result<T, DataError.Network> {
    return try {
        val response = apiCall()
        if (response.isSuccessful && response.body() != null) {
            Result.Success(response.body()!!)
        } else {
            Result.Error(DataError.Network.SERVER_ERROR)
        }
    } catch (e: IOException) {
        Result.Error(DataError.Network.NO_INTERNET)
    } catch (e: HttpException) {
        Result.Error(handleHttpException(e))
    } catch (e: Exception) {
        Result.Error(DataError.Network.UNKNOWN)
    }
}

private fun handleHttpException(e: HttpException): DataError.Network {
    return when (e.code()) {
        408 -> DataError.Network.REQUEST_TIMEOUT
        413 -> DataError.Network.PAYLOAD_TOO_LARGE
        else -> DataError.Network.UNKNOWN
    }
}

internal fun <T> Response<T>?.orNullResponseError(): Response<T> {
    return this ?: Response.error(500, "Response was null".toResponseBody(null))
}