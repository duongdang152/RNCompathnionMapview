package com.rncompathnionmapview;

import android.view.View;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.uimanager.UIManagerModule;

import javax.annotation.Nonnull;

public class MapViewModule extends ReactContextBaseJavaModule {

    public MapViewModule(@Nonnull ReactApplicationContext reactContext) {
        super(reactContext);
    }

    @Nonnull
    @Override
    public String getName() {
        return "MapViewModule";
    }

    @ReactMethod
    public void focusPOI(int reactTag, String poiCode) {
        UIManagerModule uiManager = getReactApplicationContext().getNativeModule(UIManagerModule.class);
        uiManager.addUIBlock(nativeViewHierarchyManager -> {
            View view = nativeViewHierarchyManager.resolveView(reactTag);
            if (view instanceof ContainerView) {
                View currentView = ((ContainerView) view).getCurrentView();
                if (currentView instanceof MapView) {
                    ((MapView) currentView).selectPOI(poiCode);
                }
            }
        });
    }

    @ReactMethod
    public void unfocusPOI(int reactTag) {
        UIManagerModule uiManager = getReactApplicationContext().getNativeModule(UIManagerModule.class);
        uiManager.addUIBlock(nativeViewHierarchyManager -> {
            View view = nativeViewHierarchyManager.resolveView(reactTag);
            if (view instanceof ContainerView) {
                View currentView = ((ContainerView) view).getCurrentView();
                if (currentView instanceof MapView) {
                    ((MapView) currentView).unfocusPOI();
                }
            }
        });
    }

    @ReactMethod
    public void navigatePOIToPOI(int reactTag, String startPOI, String destinationPOI, boolean disabledPath) {
        UIManagerModule uiManager = getReactApplicationContext().getNativeModule(UIManagerModule.class);
        uiManager.addUIBlock(nativeViewHierarchyManager -> {
            View view = nativeViewHierarchyManager.resolveView(reactTag);
            if (view instanceof ContainerView) {
                View currentView = ((ContainerView) view).getCurrentView();
                if (currentView instanceof MapView) {
                    ((MapView) currentView).demonstrateNavigation(startPOI, destinationPOI, disabledPath);
                }
            }
        });
    }
}
