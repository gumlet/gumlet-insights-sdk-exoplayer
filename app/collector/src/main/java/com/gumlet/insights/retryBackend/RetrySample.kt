package com.gumlet.insights.retryBackend

import java.util.Date

class RetrySample<T>(val eventData: T, var totalTime: Int, var scheduledTime: Date, var retry: Int)
