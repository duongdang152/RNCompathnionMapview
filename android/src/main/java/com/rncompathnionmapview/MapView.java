package com.rncompathnionmapview;

import android.app.AlertDialog;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;

import androidx.annotation.NonNull;

import com.compathnion.sdk.CustomMapView;
import com.compathnion.sdk.DataApi;
import com.compathnion.sdk.LocaleHelper;
import com.compathnion.sdk.LocationEngine;
import com.compathnion.sdk.SDK;
import com.compathnion.sdk.data.db.realm.Notification;
import com.compathnion.sdk.data.db.realm.Poi;
import com.compathnion.sdk.data.model.VenueLocation;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.modules.core.DeviceEventManagerModule;
import com.mapbox.geojson.Point;
import com.mapbox.turf.TurfConstants;
import com.mapbox.turf.TurfMeasurement;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MapView extends ReactNativeBasedView implements
        CustomMapView.OnPoiClickListener {
    private static final String TAG = "MapView";

    private static final int REQUESTCODE_ACCESS_LOCATION = 100;

    private CustomMapView mapview;

    private static Set<Integer> deliverLocBasedMessageIds = Collections.synchronizedSet(new HashSet<>());
    private LocationEngine locationEngine;
    private List<Notification> notificationList;

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

        notificationList = SDK.getInstance().getDataApi().getNotifications();
    }

    @Override
    public void onViewResume() {
        super.onViewResume();

        if (
                notificationList != null
                        && notificationList.size() > 0
                        && locationEngine.isLocationPermissionGranted()
        ) {
            locationEngine.addEngineStatusCallback(myLocationEngineCallback);
        }
    }

    @Override
    public void onViewPause() {
        super.onViewPause();

        locationEngine.removeEngineStatusCallback(myLocationEngineCallback);
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

    private final LocationEngine.EngineCallback myLocationEngineCallback = new LocationEngine.EngineCallback() {
        @Override
        public void onInitializationDone(boolean b) {

        }

        @Override
        public void onLocationUpdated(VenueLocation location) {
            // No more message to deliver
            if (deliverLocBasedMessageIds.size() == notificationList.size()) {
                return;
            }

            Point pointLocation = location.getTurfPoint();

            for (int i = 0; i < notificationList.size(); ++i) {
                if (deliverLocBasedMessageIds.contains(i)) {
                    continue;
                }

                Notification curMessage = notificationList.get(i);

                if (location.level != curMessage.getLevel()) {
                    continue;
                }

                boolean messageCanDeliver = false;

                List<Double> coordinates = curMessage.getCoordinates();
                for (int j = 0; j < coordinates.size(); j++) {
                    Double lat = coordinates.get(j);
                    Double lng = coordinates.get(++j);
                    double dist = TurfMeasurement.distance(
                            pointLocation,
                            Point.fromLngLat(lng, lat),
                            TurfConstants.UNIT_METERS
                    );

                    //TODO: No more threshold, what to do?
                    if (dist <= 5) {
                        messageCanDeliver = true;
                        break;
                    }
                }

                if (!messageCanDeliver) {
                    continue;
                }

                deliverLocBasedMessageIds.add(i);

                String title = "";
                title = LocaleHelper.getName(getContext(), curMessage.getTitle());

                String message = "";
                message = LocaleHelper.getName(getContext(), curMessage.getMessage());

                WritableMap event = Arguments.createMap();
                event.putString("title", title);
                event.putString("message", message);
                reactContext.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class).emit("onLocationMessageReceive", event);
            }
        }

        @Override
        public void onResetWiFiNeeded() {

        }
    };
}
