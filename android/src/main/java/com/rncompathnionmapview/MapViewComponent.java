package com.rncompathnionmapview;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.uimanager.SimpleViewManager;
import com.facebook.react.uimanager.ThemedReactContext;

import javax.annotation.Nonnull;

public class MapViewComponent extends SimpleViewManager<ContainerView> {
    private static final String REACT_CLASS = "MapViewComponent";

    private final ReactApplicationContext reactApplicationContext;

    public MapViewComponent(ReactApplicationContext reactApplicationContext) {
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
