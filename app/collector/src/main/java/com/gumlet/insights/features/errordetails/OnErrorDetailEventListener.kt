package com.gumlet.insights.features.errordetails

interface OnErrorDetailEventListener {
    fun onError(code: Int?, message: String?, errorData: ErrorData?)
}
