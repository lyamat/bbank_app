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
        when (e.code()) {
            408 -> Result.Error(DataError.Network.REQUEST_TIMEOUT)
            413 -> Result.Error(DataError.Network.PAYLOAD_TOO_LARGE)
            else -> Result.Error(DataError.Network.UNKNOWN)
        }
    } catch (e: Exception) {
        Result.Error(DataError.Network.UNKNOWN)
    }
}

fun <T> Response<T>?.orNullResponseError(): Response<T> {
    return this ?: Response.error(500, "Response was null".toResponseBody(null))
}


