package com.gumlet.insights.features.errordetails

import com.gumlet.insights.utils.topOfStacktrace

data class ErrorData(val exceptionMessage: String? = null, val exceptionStacktrace: Collection<String>? = null, val additionalData: String? = null) {
    companion object {
        fun fromThrowable(throwable: Throwable, additionalData: String? = null): ErrorData = ErrorData(throwable.message, throwable.topOfStacktrace.toList(), additionalData)
    }
}
