package com.gumlet.insights.adapters;

import com.gumlet.insights.config.SourceMetadata;
import com.gumlet.insights.data.manipulators.EventDataManipulatorPipeline;
import com.gumlet.insights.features.Feature;
import com.gumlet.insights.license.FeatureConfigContainer;
import java.util.Collection;

public interface PlayerAdapter {
    Collection<Feature<FeatureConfigContainer, ?>> init();

    void release();

    void resetSourceRelatedState();

    void registerEventDataManipulators(EventDataManipulatorPipeline pipeline);

    long getPosition();

    Long getDRMDownloadTime();

    void clearValues();

    SourceMetadata getCurrentSourceMetadata();

    default AdAdapter createAdAdapter() {
        return null;
    }
}
