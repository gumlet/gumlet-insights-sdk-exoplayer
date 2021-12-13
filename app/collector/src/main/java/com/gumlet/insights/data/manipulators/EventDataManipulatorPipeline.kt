package com.gumlet.insights.data.manipulators

interface EventDataManipulatorPipeline {
    fun clearEventDataManipulators()
    fun registerEventDataManipulator(manipulator: EventDataManipulator)
}
