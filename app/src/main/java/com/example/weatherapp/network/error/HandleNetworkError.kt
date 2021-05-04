package com.example.weatherapp.network.error

import androidx.annotation.StringRes
import com.example.weatherapp.R
import java.net.ConnectException
import java.net.UnknownHostException
import java.util.concurrent.TimeoutException
import javax.inject.Inject
import javax.net.ssl.SSLHandshakeException
import javax.net.ssl.SSLProtocolException

class HandleNetworkError @Inject constructor(): IHandleNetworkError {

    @StringRes
    override fun generateErrorID(it: Throwable): Int {
        if (it is UnknownHostException
            || it is SSLHandshakeException
            || it is TimeoutException
            || it is ConnectException
            || it is SSLProtocolException
        ) {
            return R.string.internet_error_text

        }
        return R.string.general_error_text
    }
}