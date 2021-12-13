package com.gumlet.insights

import com.gumlet.insights.data.CustomData

internal class CustomDataHelpers {
    interface Getter {
        fun getCustomData(): CustomData
    }
    interface Setter {
        fun setCustomData(customData: CustomData)
    }
}
