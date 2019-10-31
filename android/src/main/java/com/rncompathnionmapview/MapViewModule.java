package com.rncompathnionmapview;

import com.compathnion.sdk.SDK;
import com.compathnion.sdk.SDKConfig;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.uimanager.SimpleViewManager;
import com.facebook.react.uimanager.ThemedReactContext;

import javax.annotation.Nonnull;

public class MapViewModule extends SimpleViewManager<ContainerView> {
    private static final String REACT_CLASS = "CustomMapView";

    private final ReactApplicationContext reactApplicationContext;

    public MapViewModule(ReactApplicationContext reactApplicationContext) {
        this.reactApplicationContext = reactApplicationContext;
    }

    @Nonnull
    @Override
    public String getName() {
        return REACT_CLASS;
    }

    @Nonnull
    @Override
    protected ContainerView createViewInstance(@Nonnull ThemedReactContext reactContext) {
        return new ContainerView(reactContext, reactApplicationContext);
    }
}
