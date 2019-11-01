package com.rncompathnionmapview;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.compathnion.sdk.DataApi;
import com.compathnion.sdk.SDK;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.modules.core.DeviceEventManagerModule;

public class ContainerView extends ReactNativeBasedView {
    String curViewName;
    private View currentView;

    public ContainerView(@NonNull Context context, ReactApplicationContext reactApplicationContext) {
        super(context, reactApplicationContext);
    }

    @Override
    public void onViewCreate() {
        super.onViewCreate();

        curViewName = null;

        IntentFilter filterRequestView = new IntentFilter(LocalBroadcastAction.ACTION_REQUESTVIEW);
        IntentFilter filterRequestCloseView = new IntentFilter(LocalBroadcastAction.ACTION_REQUESTCLOSEVIEW);

        LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(reactContext);

        localBroadcastManager.registerReceiver(myRequestViewBroadcastReceiver, filterRequestView);
        localBroadcastManager.registerReceiver(myRequestViewBroadcastReceiver, filterRequestCloseView);

        // Load Splash Screen, and waiting for data initialization to finish
        addView(new MapView(reactContext, reactApplicationContext));
        SDK.getInstance().getDataApi().addDataUpdateCallback(myDataApiDataUpdateCallback);

        // Broadcast language change event to React Native side
        // to help set header properly
        reactContext
                .getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
                .emit(LocalBroadcastAction.JSEVENT_LANGUAGE_CHANGE, "EN");
    }

    @Override
    public void onViewDestroy() {
        LocalBroadcastManager
                .getInstance(reactContext)
                .unregisterReceiver(myRequestViewBroadcastReceiver);

        SDK.getInstance().getDataApi().removeDataUpdateCallback(myDataApiDataUpdateCallback);

        super.onViewDestroy();
    }

    @Override
    public boolean onBackPress() {
//		if (curViewName != null) {
//			return false;
//		}
//
//		return true;

        return false;
    }

    public View getCurrentView() {
        return currentView;
    }

    private final BroadcastReceiver myRequestViewBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            switch (intent.getAction()) {
                case LocalBroadcastAction.ACTION_REQUESTVIEW:
                    onReceiveView(intent);
                    break;

                case LocalBroadcastAction.ACTION_REQUESTCLOSEVIEW:
                    onReceiveCloseView(intent);
                    break;
            }
        }

        private void onReceiveView(Intent intent) {
            String viewName = intent.getStringExtra(LocalBroadcastAction.EXTRA_VIEWNAME);
            Bundle initialParameters = intent.getBundleExtra(LocalBroadcastAction.EXTRA_INIT_PARAMS);

            if (viewName.equals(curViewName)) {
                return;
            }

            View v = null;

            switch (viewName) {
                case LocalBroadcastAction.VIEW_MAP:
                    v = new MapView(reactContext, reactApplicationContext, initialParameters);
                    break;
            }

            if (v == null) {
                return;
            }
            currentView = v;

            reactContext
                    .getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
                    .emit(LocalBroadcastAction.JSEVENT_VIEW_CHANGE, viewName);

            curViewName = viewName;
//            ContainerView.this.removeAllViews();
            ContainerView.this.addView(v);
        }

        private void onReceiveCloseView(Intent intent) {
            curViewName = null;
            removeAllViews();

            LocalBroadcastAction.requestView(reactContext, LocalBroadcastAction.VIEW_HOME);
        }
    };

    private final DataApi.DataUpdateCallback myDataApiDataUpdateCallback = new DataApi.DataUpdateCallback() {
        @Override
        public void onProgress(int i) {

        }

        @Override
        public void onFinished() {
            SDK.getInstance().getDataApi().removeDataUpdateCallback(myDataApiDataUpdateCallback);

//            if (Preferences.getShouldShowActivationCodePage(reactContext)) {
//                LocalBroadcastAction.requestView(reactContext, LocalBroadcastAction.VIEW_ACTIVATION);
//            } else {
//                if (Preferences.getShouldShowTutorialPage(reactContext)) {
//                    LocalBroadcastAction.requestView(reactContext, LocalBroadcastAction.VIEW_HOWTOUSE);
//                } else {
            LocalBroadcastAction.requestView(reactContext, LocalBroadcastAction.VIEW_MAP);
            reactContext
                    .getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
                    .emit(LocalBroadcastAction.JSEVENT_DATA_INIT_DONE, null);
//                }
//            }
        }

        @Override
        public void onFailed() {
            SDK.getInstance().getDataApi().removeDataUpdateCallback(myDataApiDataUpdateCallback);
        }
    };
}
