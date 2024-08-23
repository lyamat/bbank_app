package com.example.bbank.presentation.utils

import android.content.Context
import androidx.annotation.StringRes
import com.example.bbank.R
import com.example.bbank.domain.networking.DataError
import com.example.bbank.domain.networking.Result

sealed class UiText {
    data class DynamicString(val value: String) : UiText()
    class StringResource(
        @StringRes val id: Int, val args: Array<Any> = arrayOf()
    ) : UiText()

    fun asString(context: Context): String {
        return when (this) {
            is DynamicString -> value
            is StringResource -> context.getString(id, *args)
        }
    }
}

internal fun DataError.asUiText(): UiText {
    return when (this) {
        DataError.Network.REQUEST_TIMEOUT -> UiText.StringResource(
            R.string.the_request_timed_out
        )

        DataError.Network.TOO_MANY_REQUESTS -> UiText.StringResource(
            R.string.youve_hit_your_rate_limit
        )

        DataError.Network.NO_INTERNET -> UiText.StringResource(
            R.string.no_internet
        )

        DataError.Network.PAYLOAD_TOO_LARGE -> UiText.StringResource(
            R.string.file_too_large
        )

        DataError.Network.SERVER_ERROR -> UiText.StringResource(
            R.string.server_error
        )

        DataError.Network.SERIALIZATION -> UiText.StringResource(
            R.string.error_serialization
        )

        DataError.Network.UNKNOWN -> UiText.StringResource(
            R.string.unknown_error
        )

        DataError.Local.DISK_FULL -> UiText.StringResource(
            R.string.error_disk_full
        )
    }
}

internal fun Result.Error<*, DataError>.asErrorUiText(): UiText {
    return error.asUiText()
}