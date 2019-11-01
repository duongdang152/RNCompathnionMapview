package com.rncompathnionmapview;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;

import androidx.annotation.NonNull;

import com.compathnion.sdk.CustomMapView;
import com.compathnion.sdk.DataApi;
import com.compathnion.sdk.LocationEngine;
import com.compathnion.sdk.SDK;
import com.compathnion.sdk.data.db.realm.Poi;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.modules.core.DeviceEventManagerModule;

public class MapView extends ReactNativeBasedView implements
        CustomMapView.OnPoiClickListener {
    private static final String TAG = "MapView";

    private static final int REQUESTCODE_ACCESS_LOCATION = 100;

    private CustomMapView mapview;

    private LocationEngine locationEngine;

    public MapView(@NonNull Context context, ReactApplicationContext reactApplicationContext) {
        super(context, reactApplicationContext);

        init();
    }

    public MapView(Context context, ReactApplicationContext reactApplicationContext, Bundle initialParameters) {
        super(context, reactApplicationContext, initialParameters);

        init();
    }

    private void init() {
        locationEngine = SDK.getInstance().getLocationEngine();
    }

    @Override
    public void onViewCreate() {
        super.onViewCreate();

        CustomMapView.initializeMapbox(reactContext);
        LayoutInflater.from(reactContext.getCurrentActivity()).inflate(R.layout.view_map, this);

        setupMapView();

        postDelayed(() -> {
            if (!SDK.getInstance().getLocationEngine().isLocationPermissionGranted()) {
                LocationEngine.requestLocationPermission(
                        reactContext.getCurrentActivity(), REQUESTCODE_ACCESS_LOCATION
                );
            }
        }, 2000);
    }

    @Override
    public void onViewResume() {
        super.onViewResume();
    }

    @Override
    public void onViewPause() {
        super.onViewPause();
    }

    @Override
    public void onViewDestroy() {
        super.onViewDestroy();
    }

    // BEGIN: CustomMapView.OnPoiClickListener
    @Override
    public void onPoiClick(Poi poi) {
        WritableMap event = Arguments.createMap();
        event.putString("poiCode", poi.getCode());
        reactContext.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class).emit("onPOIClick", event);
    }

    @Override
    public void onPoiUnclick() {
        WritableMap event = Arguments.createMap();
        reactContext.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class).emit("onPOIUnclick", event);
    }
    // END: CustomMapView.OnPoiClickListener

    private void setupMapView() {
        mapview = findViewById(R.id.mapview);

        mapview.setDefaultViewTextColor(getResources().getColor(R.color.venue_theme_color));
        mapview.setNavigationLineBackgroundColor(getResources().getColor(R.color.venue_theme_color));

        mapview.setOnPoiClickListener(this);
    }

    public void selectPOI(String poiCode) {
        mapview.addMarkerToPoi(poiCode);
    }

    public void unfocusPOI() {
        mapview.removeCurrentPoiMarker();
    }

    public void demonstrateNavigation(String startPOI, String endPOI, boolean disabledPath) {
        DataApi dataApi = SDK.getInstance().getDataApi();
        Poi start = dataApi.getPoiByCode(startPOI);
        Poi end = dataApi.getPoiByCode(endPOI);
        mapview.demonstrateNavigation(start, end, disabledPath);
    }

    public void navigateFromCurrentLocation(String destinationPOI, boolean disabledPath) {
        Poi destination = SDK.getInstance().getDataApi().getPoiByCode(destinationPOI);
        mapview.navigateFromCurrentLocation(destination, disabledPath);
    }
}
