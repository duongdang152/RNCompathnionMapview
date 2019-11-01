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

        LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(reactContext);
        localBroadcastManager.registerReceiver(myRequestViewBroadcastReceiver, filterRequestView);

        SDK.getInstance().getDataApi().addDataUpdateCallback(myDataApiDataUpdateCallback);
    }

    @Override
    public void onViewDestroy() {
        LocalBroadcastManager
                .getInstance(reactContext)
                .unregisterReceiver(myRequestViewBroadcastReceiver);

        SDK.getInstance().getDataApi().removeDataUpdateCallback(myDataApiDataUpdateCallback);

        super.onViewDestroy();
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
            curViewName = viewName;
            ContainerView.this.addView(v);
        }
    };

    private final DataApi.DataUpdateCallback myDataApiDataUpdateCallback = new DataApi.DataUpdateCallback() {
        @Override
        public void onProgress(int i) {

        }

        @Override
        public void onFinished() {
            SDK.getInstance().getDataApi().removeDataUpdateCallback(myDataApiDataUpdateCallback);

            LocalBroadcastAction.requestView(reactContext, LocalBroadcastAction.VIEW_MAP);
        }

        @Override
        public void onFailed() {
            SDK.getInstance().getDataApi().removeDataUpdateCallback(myDataApiDataUpdateCallback);
        }
    };
}
