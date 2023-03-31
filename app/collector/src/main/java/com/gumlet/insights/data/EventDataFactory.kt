package com.gumlet.insights.data

import com.gumlet.insights.GumletInsightsConfig
import com.gumlet.insights.config.SourceMetadata
import com.gumlet.insights.data.manipulators.EventDataManipulator
import com.gumlet.insights.data.manipulators.EventDataManipulatorPipeline

class EventDataFactory(private val config: GumletInsightsConfig, private val userIdProvider: UserIdProvider) : EventDataManipulatorPipeline {
    private val eventDataManipulators = mutableListOf<EventDataManipulator>()

    // TODO DeviceInformationProvider for now is only available after `attachPlayerAdapter`, but can also be moved to the constructor of GumletInsights and also in this class
    fun create(impressionId: String, sourceMetadata: SourceMetadata?, deviceInformation: DeviceInformation): EventData {
        val eventData = EventData(
                deviceInformation,
                impressionId,
                userIdProvider.userId(),
                config.key,
                config.playerKey,
                if (sourceMetadata == null) config.videoId else sourceMetadata.videoId,
                if (sourceMetadata == null) config.title else sourceMetadata.title,
                if(config.userData != null) config.userData.customUserId else "",
                if (sourceMetadata == null) {if(config.customData != null && config.customData.customData1 != null) config.customData.customData1 else ""} else sourceMetadata.customData1,
                if (sourceMetadata == null) {if(config.customData != null && config.customData.customData2 != null) config.customData.customData2 else ""}  else sourceMetadata.customData2,
                if (sourceMetadata == null) {if(config.customData != null && config.customData.customData3 != null) config.customData.customData3 else ""}  else sourceMetadata.customData3,
                if (sourceMetadata == null) {if(config.customData != null && config.customData.customData4 != null) config.customData.customData4 else ""}  else sourceMetadata.customData4,
                if (sourceMetadata == null) {if(config.customData != null && config.customData.customData5 != null) config.customData.customData5 else ""}  else sourceMetadata.customData5,
                if (sourceMetadata == null) {if(config.customData != null && config.customData.customData6 != null) config.customData.customData6 else ""}  else sourceMetadata.customData6,
                if (sourceMetadata == null) {if(config.customData != null && config.customData.customData7 != null) config.customData.customData7 else ""}  else sourceMetadata.customData7,
                if (sourceMetadata == null) config.path else sourceMetadata.path,
                //if (sourceMetadata == null) config.experimentName else sourceMetadata.experimentName,
                if (sourceMetadata == null) "" else "",
                if (sourceMetadata == null) config.cdnProvider else sourceMetadata.cdnProvider,
                /*TODO This will always be overridden in the adapters, we need a logic like with m3u8 url*/
                config.playerType?.toString())

        for (decorator in eventDataManipulators) {
            decorator.manipulate(eventData)
        }

        return eventData
    }

    override fun clearEventDataManipulators() {
        eventDataManipulators.clear()
    }

    override fun registerEventDataManipulator(manipulator: EventDataManipulator) {
        eventDataManipulators.add(manipulator)
    }
}
