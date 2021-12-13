package com.gumlet.insights.features.errordetails

import com.gumlet.insights.features.httprequesttracking.HttpRequest
import com.gumlet.insights.utils.Util

data class ErrorDetail(val platform: String, val licenseKey: String, val domain: String, val impressionId: String, val errorId: Long, val timestamp: Long, val code: Int?, val message: String?, val data: ErrorData, val httpRequests: List<HttpRequest>?, val analyticsVersion: String = Util.getAnalyticsVersion())
