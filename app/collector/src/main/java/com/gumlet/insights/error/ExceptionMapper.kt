package com.gumlet.insights.error

import com.gumlet.insights.data.ErrorCode

interface ExceptionMapper<in T> {
    fun map(throwable: T): ErrorCode
}
