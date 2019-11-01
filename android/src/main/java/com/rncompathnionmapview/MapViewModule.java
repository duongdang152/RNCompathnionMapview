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

    private interface OnCallbackListener {
        void callback(MapView mapView);
    }

    @Nonnull
    @Override
    public String getName() {
        return "MapViewModule";
    }

    private void getMapView(int reactTag, OnCallbackListener listener) {
        UIManagerModule uiManager = getReactApplicationContext().getNativeModule(UIManagerModule.class);
        uiManager.addUIBlock(nativeViewHierarchyManager -> {
            View view = nativeViewHierarchyManager.resolveView(reactTag);
            if (view instanceof ContainerView) {
                View currentView = ((ContainerView) view).getCurrentView();
                if (currentView instanceof MapView) {
                    listener.callback((MapView) currentView);
                }
            }
        });
    }

    @ReactMethod
    public void focusPOI(int reactTag, String poiCode) {
        getMapView(reactTag, mapView -> mapView.selectPOI(poiCode));
    }

    @ReactMethod
    public void unfocusPOI(int reactTag) {
        getMapView(reactTag, mapView -> mapView.unfocusPOI());
    }

    @ReactMethod
    public void navigatePOIToPOI(int reactTag, String startPOI, String destinationPOI, boolean disabledPath) {
        getMapView(reactTag, mapView -> mapView.demonstrateNavigation(startPOI, destinationPOI, disabledPath));
    }

    @ReactMethod
    public void navigateCurrentToPOI(int reactTag, String destinationPOI, boolean disabledPath) {
        getMapView(reactTag, mapView -> mapView.navigateFromCurrentLocation(destinationPOI, disabledPath));
    }
}
