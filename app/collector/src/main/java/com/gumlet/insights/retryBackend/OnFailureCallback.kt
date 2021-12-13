package com.gumlet.insights.retryBackend

interface OnFailureCallback {
    fun onFailure(e: Exception, cancel: () -> Unit)
}
