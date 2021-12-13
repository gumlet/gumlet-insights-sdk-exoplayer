package com.gumlet.insights

import com.gumlet.insights.config.SourceMetadata
import com.gumlet.insights.data.CustomData

internal class SourceMetadataExtension {
    companion object {
        fun SourceMetadata.setCustomData(customData: CustomData) {
            customData1 = customData.customData1
            customData2 = customData.customData2
            customData3 = customData.customData3
            customData4 = customData.customData4
            customData5 = customData.customData5
            customData6 = customData.customData6
            customData7 = customData.customData7
            experimentName = customData.experimentName
        }

        fun SourceMetadata.getCustomData(): CustomData {
            return CustomData(customData1, customData2, customData3, customData4, customData5, customData6, customData7, experimentName)
        }
    }
}
