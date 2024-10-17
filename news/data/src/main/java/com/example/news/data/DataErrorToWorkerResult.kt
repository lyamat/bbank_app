package com.example.news.data

import androidx.work.ListenableWorker.Result
import com.example.core.domain.util.DataError

fun DataError.toWorkerResult(): Result {
    return when (this) {
        DataError.Local.DISK_FULL -> Result.failure()
        DataError.Network.REQUEST_TIMEOUT -> Result.retry()
        DataError.Network.UNAUTHORIZED -> Result.retry()
        DataError.Network.CONFLICT -> Result.retry()
        DataError.Network.TOO_MANY_REQUESTS -> Result.retry()
        DataError.Network.NO_INTERNET -> Result.retry()
        DataError.Network.PAYLOAD_TOO_LARGE -> Result.failure()
        DataError.Network.SERVER_ERROR -> Result.retry()
        DataError.Network.SERIALIZATION -> Result.failure()
        DataError.Network.UNKNOWN -> Result.failure()
        DataError.Local.NOT_FOUND -> Result.failure()
    }
}