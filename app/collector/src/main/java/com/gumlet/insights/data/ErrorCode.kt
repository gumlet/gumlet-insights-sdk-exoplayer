package com.gumlet.insights.data

import com.gumlet.insights.features.errordetails.ErrorData

data class ErrorCode(val errorCode: Int, val description: String, val errorData: ErrorData, val legacyErrorData: LegacyErrorData? = null) {

    @Override
    override fun toString(): String {
        return "$errorCode: $description"
    }
}
