package com.rncompathnionmapview;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.FrameLayout;

import com.facebook.react.ReactActivity;
import com.facebook.react.bridge.ActivityEventListener;
import com.facebook.react.bridge.LifecycleEventListener;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.modules.core.PermissionListener;

// Reference: https://github.com/react-native-mapbox-gl/maps/blob/master/android/rctmgl/src/main/java/com/mapbox/rctmgl/components/mapview/RCTMGLMapView.java
public abstract class ReactNativeBasedView extends FrameLayout {
    protected final ReactContext reactContext;
    protected final ReactApplicationContext reactApplicationContext;

    private ActivityEventListener activityEventListener;
    private LifecycleEventListener lifecycleEventListener;
    private boolean isViewPaused;

    protected final Bundle initialParameters;

    public ReactNativeBasedView(
            Context context, ReactApplicationContext reactApplicationContext
    ) {
        super(context);
        this.reactContext = (ReactContext) context;
        this.reactApplicationContext = reactApplicationContext;

        initialParameters = new Bundle();
    }

    public ReactNativeBasedView(
            Context context, ReactApplicationContext reactApplicationContext,
            Bundle initialParameters
    ) {
        super(context);
        this.reactContext = (ReactContext) context;
        this.reactApplicationContext = reactApplicationContext;

        if (initialParameters == null) {
            this.initialParameters = new Bundle();
        } else {
            this.initialParameters = initialParameters;
        }
    }

    @Override
    public void requestLayout() {
        super.requestLayout();

        post(() -> {
            measure(
                    MeasureSpec.makeMeasureSpec(getWidth(), MeasureSpec.EXACTLY),
                    MeasureSpec.makeMeasureSpec(getHeight(), MeasureSpec.EXACTLY));

            layout(getLeft(), getTop(), getRight(), getBottom());
        });
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        if (!isViewPaused) {
            super.onLayout(changed, left, top, right, bottom);
        }
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();

        LocalBroadcastAction.registerBackpressListener(
                reactContext,
                myBackpressTriggeredBroadcastReceiver
        );

        isViewPaused = false;

        lifecycleEventListener = new LifecycleEventListener() {
            @Override
            public void onHostResume() {
                onViewResume();
                isViewPaused = false;
            }

            @Override
            public void onHostPause() {
                onViewPause();
                isViewPaused = true;
            }

            @Override
            public void onHostDestroy() {
                reactContext.removeLifecycleEventListener(lifecycleEventListener);
                reactContext.removeActivityEventListener(activityEventListener);

                if (!isViewPaused) {
                    onViewPause();
                }

                onViewStop();
                onViewDestroy();
            }
        };

        activityEventListener = new ActivityEventListener() {
            @Override
            public void onActivityResult(Activity activity, int requestCode, int resultCode, Intent data) {
                onViewActivityResult(requestCode, resultCode, data);
            }

            @Override
            public void onNewIntent(Intent intent) {
                onViewNewIntent(intent);
            }
        };

        reactContext.addLifecycleEventListener(lifecycleEventListener);
        reactApplicationContext.addActivityEventListener(activityEventListener);

        onViewCreate();
    }

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();

        LocalBroadcastAction.unregisterBackpressListener(
                reactContext,
                myBackpressTriggeredBroadcastReceiver
        );

        onViewDestroy();
    }

    protected void requestPermission(String permission, int requestCode) {
        ((ReactActivity) reactContext.getCurrentActivity()).requestPermissions(
                new String[]{permission}, requestCode, myPermissionListener
        );
    }

    protected void requestPermission(String[] permissions, int requestCode) {
        ((ReactActivity) reactContext.getCurrentActivity()).requestPermissions(
                permissions, requestCode, myPermissionListener
        );
    }

    public void onViewCreate() {

    }

    public void onViewResume() {

    }

    public void onViewPause() {

    }

    public void onViewStop() {

    }

    /**
     * By defauult, this removes all view, so if you override this method, you should call
     * super.onViewDestroy() at the very end
     */
    public void onViewDestroy() {
        removeAllViews();
    }

    /**
     * @return true to resume normal behavior, false otherwise
     */
    public boolean onBackPress() {
        return true;
    }

    public void onViewActivityResult(int requestCode, int resultCode, Intent data) {

    }

    public void onViewNewIntent(Intent intent) {

    }

    public void onRequestPermissionResult(int requestCode, String[] permissions, int[] grantResult) {

    }

    private final BroadcastReceiver myBackpressTriggeredBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Address Checkmarx report: Improper Verification Of Intent By Broadcast Receiver
            if (!intent.getAction().equals(LocalBroadcastAction.ACTION_BACKPRESS_TRIGGERED)) {
                return;
            }

            if (onBackPress()) {
                LocalBroadcastAction.requestBackpress(context);
            }
        }
    };

    private final PermissionListener myPermissionListener = new PermissionListener() {
        @Override
        public boolean onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
            ReactNativeBasedView.this.onRequestPermissionResult(requestCode, permissions, grantResults);
            return true;
        }
    };
}
