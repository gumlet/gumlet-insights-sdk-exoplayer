package com.gumlet.insights.data.manipulators

import com.gumlet.insights.data.EventData

interface EventDataManipulator {
    fun manipulate(data: EventData)
}
