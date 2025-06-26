package com.ketch.internal.utils

data class DownloadException(
    val exception: Exception,
    val statusCode: Int?,
) : Exception(exception.message)
