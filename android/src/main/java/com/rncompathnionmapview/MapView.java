package com.rncompathnionmapview;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.RecyclerView;

import com.compathnion.sdk.CustomMapView;
import com.compathnion.sdk.DataApi;
import com.compathnion.sdk.LocaleHelper;
import com.compathnion.sdk.LocationEngine;
import com.compathnion.sdk.SDK;
import com.compathnion.sdk.SDKConfig;
import com.compathnion.sdk.data.db.realm.Category;
import com.compathnion.sdk.data.db.realm.Poi;
import com.facebook.react.bridge.ReactApplicationContext;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class MapView extends ReactNativeBasedView implements
        CustomMapView.OnPoiClickListener
//        NavigationView.OnNavigationViewInteractionListener,
//        CategorySearchView.OnSearchedPoiClickListener
{
    private static final String TAG = "MapView";

    public static final String INIT_PARAM_POICODE = "poiCode";
    public static final String INIT_PARAM_CATEGORYSLUG = "categorySlug";

    private static final int REQUESTCODE_ACCESS_LOCATION = 100;

    private CustomMapView mapview;

    private SearchView searchbox;
//    private NavigationView navigationView;
//    private CategorySearchView categorySearchView;

    private boolean isShowingCategorySearchView = false;
    private boolean isShowingNavigation = false;

    private ViewGroup bottomSheetPoi;
    private BottomSheetBehavior bottomSheetBehaviorPoi;

    private Button btnSheetNavigate;
    private boolean btnSheetNavigateClicked = false;

    private ImageView btnCloseBottomSheet;
    private ImageView imgSheetPoi;
    private ImageView imgSheetSharePoi;
    private ImageView imgSheetSaveLocation;
    private ImageView imgSheetCallPoi;
    private TextView txtSheetPoiName;
    private TextView txtSheetPoiCategory;
    private TextView txtPoiSheetPoiAddress;
    private TextView txtPoiSheetPoiPhone;
    private TextView txtPoiSheetPoiOpeningHours;

    private ViewGroup bottomSheetHotspot;
    private BottomSheetBehavior bottomSheetBehaviorHotspot;
    private RecyclerView recyclerViewHotspot;

    private Map<String, Category> categoryBySlug;
    private Map<String, Integer> mapPoiCodeCatCount;
    private Map<String, List<Category>> mapCatSlugSubcats;
    private Map<String, List<Poi>> mapCatSlugPois;

    private RecyclerView recyclerSubViewForHotspot;
    private boolean isShowingSubViewForHotspot = false;
    private ArrayList<String> pathsAtHotspotSubView = new ArrayList<>();

    private String poiCodeFocusAtStart = null;
    private Category categoryFocusAtStart = null;

    private static Set<Integer> deliverLocBasedMessageIds = Collections.synchronizedSet(new HashSet<>());
    private LocationEngine locationEngine;
//    private LocationBasedMessage[] allLocationBasedMessage;

    public MapView(@NonNull Context context, ReactApplicationContext reactApplicationContext) {
        super(context, reactApplicationContext);

        init();
    }

    public MapView(Context context, ReactApplicationContext reactApplicationContext, Bundle initialParameters) {
        super(context, reactApplicationContext, initialParameters);

        init();
    }

    private void init() {
        SDKConfig config = new SDKConfig.Builder()
                .withBaseHostUrl(getContext().getString(R.string.base_host_url))
                .withVenueCode(getContext().getString(R.string.venue_code))
                .withCredentials(
                        getContext().getString(R.string.username),
                        getContext().getString(R.string.password),
                        getContext().getString(R.string.client_id),
                        getContext().getString(R.string.client_secret)
                )
                .build();

        SDK.initialize(getContext(), config);

        locationEngine = SDK.getInstance().getLocationEngine();
    }

    @Override
    public void onViewCreate() {
        super.onViewCreate();

        Resources resources = LocaleHelper.getResources(reactContext);

        CustomMapView.initializeMapbox(reactContext);

        LayoutInflater.from(reactContext.getCurrentActivity()).inflate(R.layout.view_map, this);

        setupMapView();

//        setupHotspoBottomSheet(resources);
//        setupPoiBottomSheet(resources);

//        categorySearchView = findViewById(R.id.categorysearchView);
//        categorySearchView.setOnSearchedPoiClickListener(this);

//        setupSearchBox(resources);

        postDelayed(() -> {
            if (!SDK.getInstance().getLocationEngine().isLocationPermissionGranted()) {
                LocationEngine.requestLocationPermission(
                        reactContext.getCurrentActivity(), REQUESTCODE_ACCESS_LOCATION
                );
            }
        }, 2000);

//        // Load data for location-based message. Ignore if failed to load
//        try {
//            Gson gson = new Gson();
//
//            allLocationBasedMessage = gson.fromJson(
//                    new InputStreamReader(reactContext.getAssets().open("locationBasedMessage.json")),
//                    LocationBasedMessage[].class
//            );
//
//        } catch (Exception e) {
//            Log.e(TAG, "Cannot load data for location-based message. Reason: " + e.getMessage());
//
//            allLocationBasedMessage = new LocationBasedMessage[0];
//        }

    }

    @Override
    public void onViewResume() {
        super.onViewResume();

//        if (
//                allLocationBasedMessage != null
//                        && allLocationBasedMessage.length > 0
//                        && locationEngine.isLocationPermissionGranted()
//        ) {
//            locationEngine.addEngineStatusCallback(myLocationEngineCallback);
//        }

    }

    @Override
    public void onViewPause() {
        super.onViewPause();

//        locationEngine.removeEngineStatusCallback(myLocationEngineCallback);
    }

    @Override
    public void onViewDestroy() {
//        onCloseView();
        super.onViewDestroy();
    }

    @Override
    public boolean onBackPress() {
        // Close hotspot recycler sub view if it is currently visible
//        if (isShowingSubViewForHotspot) {
//            pathsAtHotspotSubView.remove(pathsAtHotspotSubView.size() - 1);
//
//            if (pathsAtHotspotSubView.size() == 0) {
//                closeRecyclerSubViewForHotspot();
//            } else {
//                renderRecyclerSubViewForHotspot();
//            }
//
//            return false;
//        }

        // Close navigation view if it is currently opened
        if (btnSheetNavigateClicked) {
//            onCloseView();
            return false;
        }

//        // Close category search view if it is currently opened
//        if (categorySearchView.getVisibility() == VISIBLE) {
//            showHideCategorySearchViewWhenTriggerSearch(false);
//            return false;
//        }

        // Otherwise, close view
        LocalBroadcastAction.requestCloseView(reactContext);
        return false;
    }

//    // BEGIN: CategorySearchView.OnSearchedPoiClickListener
//    @Override
//    public void onSearchedPoiClick(Poi poi) {
//        showHideCategorySearchViewWhenTriggerSearch(false);
//
//        InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
//        imm.hideSoftInputFromWindow(categorySearchView.getWindowToken(), 0);
//
//        mapview.postDelayed(() -> {
//            mapview.addMarkerToPoi(poi.getCode());
//        }, 500);
//
//    }
//    // END: CategorySearchView.OnSearchedPoiClickListener

//    // BEGIN: NavigationView.OnNavigationViewInteractionListener
//    @Override
//    public void onDemonstrateNavigation(Poi origin, Poi destination, boolean disabledPath) {
//        mapview.demonstrateNavigation(origin, destination, disabledPath);
//    }
//
//
//    @Override
//    public void onNavigateFromCurrentLocation(Poi destination, boolean disabledPath) {
//        mapview.navigateFromCurrentLocation(destination, disabledPath);
//    }
//
//
//    @Override
//    public void onCloseView() {
//        removeView(navigationView);
//        btnSheetNavigateClicked = false;
//
//    }
//    // END: NavigationView.OnNavigationViewInteractionListener

    // BEGIN: CustomMapView.OnPoiClickListener
    @Override
    public void onPoiClick(Poi poi) {
//        bottomSheetBehaviorHotspot.setState(BottomSheetBehavior.STATE_COLLAPSED);
//        bottomSheetHotspot.setVisibility(GONE);
//
//        bottomSheetBehaviorPoi.setState(BottomSheetBehavior.STATE_COLLAPSED);
//        bottomSheetPoi.setVisibility(View.VISIBLE);
//
//        presentPoiInfo(poi);
//
//
//        final String categorySlugFocusAtStartForSavedLocation;
//
//        if (categoryFocusAtStart == null) {
//            categorySlugFocusAtStartForSavedLocation = null;
//        } else {
//            categorySlugFocusAtStartForSavedLocation = categoryFocusAtStart.getSlug();
//        }
//
//        MyBottomSheetButtonOnClickListener bottomSheetButtonOnClickListener = new MyBottomSheetButtonOnClickListener(
//                poi, categorySlugFocusAtStartForSavedLocation
//        );
//
//        btnSheetNavigate.setOnClickListener(bottomSheetButtonOnClickListener);
//        imgSheetSaveLocation.setOnClickListener(bottomSheetButtonOnClickListener);
//        imgSheetSharePoi.setOnClickListener(bottomSheetButtonOnClickListener);
//        imgSheetCallPoi.setOnClickListener(bottomSheetButtonOnClickListener);
//        btnCloseBottomSheet.setOnClickListener(bottomSheetButtonOnClickListener);
//
//        searchbox.setIconified(true);
//        searchbox.clearFocus();
//
////        categorySearchView.setVisibility(View.GONE);
//
//        String imageUrl = "https://hkch-staging.compathnion.com/" + poi.getImageUrl();
//
//        if (!TextUtils.isEmpty(imageUrl)) {
//            Picasso.get()
//                    .load(imageUrl)
//                    .placeholder(R.drawable.ha_logo)
//                    .into(imgSheetPoi, new Callback() {
//                        @Override
//                        public void onSuccess() {
//                            imgSheetPoi.setScaleType(ImageView.ScaleType.FIT_CENTER);
//                            imgSheetPoi.setAdjustViewBounds(true);
//                        }
//
//                        @Override
//                        public void onError(Exception e) {
//                        }
//                    });
//
//        } else {
//            imgSheetPoi.setImageDrawable(getResources().getDrawable(R.drawable.ha_logo));
//            imgSheetPoi.setScaleType(ImageView.ScaleType.FIT_CENTER);
//            imgSheetPoi.setAdjustViewBounds(true);
//        }
//
//        // Clean focus-at-start state
//        poiCodeFocusAtStart = null;
//        categoryFocusAtStart = null;
    }

    @Override
    public void onPoiUnclick() {
//        bottomSheetPoi.setVisibility(View.GONE);
//
//        if (!isShowingCategorySearchView && !isShowingNavigation) {
//            bottomSheetHotspot.setVisibility(VISIBLE);
//        }
    }
    // END: CustomMapView.OnPoiClickListener

    private void showHideCategorySearchViewWhenTriggerSearch(boolean searchBegin) {
        isShowingCategorySearchView = searchBegin;

        if (searchBegin) {
            mapview.removeCurrentPoiMarker();

            searchbox.setIconified(false);
//            categorySearchView.setVisibility(VISIBLE);
//            categorySearchView.filter("");

//            closeRecyclerSubViewForHotspot();

            bottomSheetHotspot.setVisibility(GONE);
        } else {
//            categorySearchView.setVisibility(View.GONE);

            if (mapview.getClickedPoi() == null && !isShowingNavigation) {
                bottomSheetBehaviorHotspot.setState(BottomSheetBehavior.STATE_COLLAPSED);
                bottomSheetHotspot.setVisibility(VISIBLE);
            }
        }
    }

    private void setupMapView() {
        mapview = findViewById(R.id.mapview);

        // Check if we need to focus on POI at start
        poiCodeFocusAtStart = initialParameters.getString(INIT_PARAM_POICODE);

        if (poiCodeFocusAtStart != null) {
            String categorySlugFocusAtStart = initialParameters.getString(INIT_PARAM_CATEGORYSLUG);

            for (Category category : SDK.getInstance().getDataApi().getCategories()) {
                if (category.getSlug().equals(categorySlugFocusAtStart)) {
                    categoryFocusAtStart = category;
                    break;
                }
            }

            mapview.setFocusOnBlueDotAtStart(false);

            mapview.setOnMapInitializationDoneListener(new CustomMapView.OnMapInitializationDoneListener() {
                @Override
                public void onSuccess() {
                    mapview.addMarkerToPoi(poiCodeFocusAtStart);
                }

                @Override
                public void onFailed() {

                }
            });
        }

        mapview.setDefaultViewTextColor(getResources().getColor(R.color.venue_theme_color));
        mapview.setNavigationLineBackgroundColor(getResources().getColor(R.color.venue_theme_color));

        mapview.setOnPoiClickListener(this);

//        MyNavigationCallback myNavigationCallback = new MyNavigationCallback();
//        mapview.setNavigationDemonstrationCallback(myNavigationCallback);
//        mapview.setNavigationFromCurrentLocationCallback(myNavigationCallback);
    }

//    private void setupPoiBottomSheet(Resources resources) {
//        bottomSheetPoi = findViewById(R.id.bottomsheetPoi);
//
//        btnSheetNavigate = findViewById(R.id.btnSheetNavigate);
//        btnCloseBottomSheet = findViewById(R.id.btnCloseBottomSheet);
//        imgSheetPoi = findViewById(R.id.imgSheetPoiImg);
//        imgSheetSharePoi = findViewById(R.id.imgSharePoi);
//        imgSheetCallPoi = findViewById(R.id.imgSheetCallPoi);
//        txtSheetPoiName = findViewById(R.id.txtSheetPoiName);
//        imgSheetSaveLocation = findViewById(R.id.imgSaveLocation);
//        txtSheetPoiCategory = findViewById(R.id.txtSheetPoiCategory);
//        txtPoiSheetPoiAddress = findViewById(R.id.txtSheetPoiAddress);
//        txtPoiSheetPoiPhone = findViewById(R.id.txtSheetPoiPhone);
//        txtPoiSheetPoiOpeningHours = findViewById(R.id.txtSheetPoiOpeningHours);
//
//        bottomSheetPoi.setVisibility(View.GONE);
//        bottomSheetBehaviorPoi = BottomSheetBehavior.from(bottomSheetPoi);
//        bottomSheetBehaviorPoi.setState(BottomSheetBehavior.STATE_COLLAPSED);
//
//        btnSheetNavigate.setText(resources.getString(R.string.navigate));
//
//        ((TextView)findViewById(R.id.txtLocation)).setText(resources.getString(R.string.location));
//        ((TextView)findViewById(R.id.txtPhoneNumber)).setText(resources.getString(R.string.phonenumber));
//        ((TextView)findViewById(R.id.txtOpeningHours)).setText(resources.getString(R.string.openinghours));
//    }

//    private void setupHotspoBottomSheet(Resources resources) {
//        recyclerSubViewForHotspot = findViewById(R.id.recyclerSubViewForHotspot);
//        recyclerSubViewForHotspot.setLayoutManager(new LinearLayoutManager(reactContext));
//
//        TextView txtHotspot = findViewById(R.id.txtHotspot);
//
//        bottomSheetHotspot = findViewById(R.id.bottomSheetHotspot);
//
//        bottomSheetBehaviorHotspot = BottomSheetBehavior.from(bottomSheetHotspot);
//        bottomSheetBehaviorHotspot.setState(BottomSheetBehavior.STATE_COLLAPSED);
//
//        txtHotspot.setText(resources.getString(R.string.hotspot));
//
//
//        DataApi dataApi = SDK.getInstance().getDataApi();
//
//        List<Category> allCategory = dataApi.getCategories();
//        List<Poi> allPoi = dataApi.getPois();
//
//        mapCatSlugSubcats = new HashMap<>();
//        categoryBySlug = new HashMap<>();
//
//        for (Category category : allCategory) {
//            categoryBySlug.put(category.getSlug(), category);
//
//            String parentSlug = category.getParentId();
//
//            if (TextUtils.isEmpty(parentSlug)) {
//                continue;
//            }
//
//            if (!mapCatSlugSubcats.containsKey(parentSlug)) {
//                mapCatSlugSubcats.put(parentSlug, new ArrayList<>());
//            }
//
//            mapCatSlugSubcats.get(parentSlug).add(category);
//        }
//
//
//
//        mapCatSlugPois = new HashMap<>();
//        mapPoiCodeCatCount = new HashMap<>();
//
//        for (Poi poi : allPoi) {
//            List<Category> allCategoryOfPoi = new ArrayList<>();
//
//            List<Category> poiAmenityCategories = poi.getAmenityCategories();
//
//            if (poiAmenityCategories != null) {
//                allCategoryOfPoi.addAll(poiAmenityCategories);
//            }
//
//            List<Category> poiOccupantCategories = poi.getOccupantCategories();
//
//            if (poiOccupantCategories != null) {
//                allCategoryOfPoi.addAll(poiOccupantCategories);
//            }
//
//            mapPoiCodeCatCount.put(poi.getCode(), allCategoryOfPoi.size());
//
//            for (Category category : allCategoryOfPoi) {
//                String catSlug = category.getSlug();
//
//                if (!mapCatSlugPois.containsKey(catSlug)) {
//                    mapCatSlugPois.put(catSlug, new ArrayList<>());
//                }
//
//                mapCatSlugPois.get(catSlug).add(poi);
//            }
//
//        }
//
//        List<HotspotItem> allHotspotDataItem = new ArrayList<>();
//
//        for (Category category : allCategory) {
//            if (category.getBookmarkOrder() != 0) {
//                HotspotItem hotspotCategory = new HotspotItem();
//                hotspotCategory.category = category;
//                hotspotCategory.bookmarkOrder = category.getBookmarkOrder();
//
//                allHotspotDataItem.add(hotspotCategory);
//            }
//
//        }
//
//        for (Poi poi : allPoi) {
//            if (poi.getBookmarkOrder() != 0) {
//                HotspotItem hotspotPoi = new HotspotItem();
//                hotspotPoi.poi = poi;
//                hotspotPoi.bookmarkOrder = poi.getBookmarkOrder();
//
//                allHotspotDataItem.add(hotspotPoi);
//            }
//
//        }
//
//        Collections.sort(allHotspotDataItem, (a, b) -> a.bookmarkOrder - b.bookmarkOrder);
//
//        List<ServiceDirectoryListItem> allHotspotServiceDirectoryItem = new ArrayList<>();
//
//        for (HotspotItem hotspotItem : allHotspotDataItem) {
//            if (hotspotItem.category != null) {
//                Category category = hotspotItem.category;
//
//                allHotspotServiceDirectoryItem.add(new ServiceDirectoryListItem(
//                        ServiceDirectoryListItem.TYPE_CATEGORY,
//                        category.getSlug(),
//                        LocaleHelper.getName(reactContext, category.getName()),
//                        "",
//                        category.getSlug()
//                ));
//
//            } else {
//                Poi poi = hotspotItem.poi;
//                Category poiCategory = null;
//
//                if (poi.getOccupantCategories() != null && poi.getOccupantCategories().size() != 0) {
//                    poiCategory = poi.getOccupantCategories().get(0);
//                } else if (poi.getAmenityCategories() != null && poi.getAmenityCategories().size() != 0) {
//                    poiCategory = poi.getAmenityCategories().get(0);
//                }
//
//                allHotspotServiceDirectoryItem.add(new ServiceDirectoryListItem(
//                        ServiceDirectoryListItem.TYPE_POI,
//                        poi.getCode(),
//                        LocaleHelper.getName(reactContext, poi.getName()),
//                        "",
//                        poiCategory == null ? "" : poiCategory.getSlug()
//                ));
//
//            }
//
//        }
//
//        recyclerViewHotspot = findViewById(R.id.recyclerViewHotspot);
//        recyclerViewHotspot.setLayoutManager(new GridLayoutManager(reactContext, 4));
//        recyclerViewHotspot.setAdapter(new ServiceDirectoryAdapter(
//                reactContext,
//                allHotspotServiceDirectoryItem,
//                R.layout.itemgrid_hotspot_service_directory,
//                item -> {
//                    if (item.type == ServiceDirectoryListItem.TYPE_POI) {
//                        mapview.addMarkerToPoi(item.id);
//                        return;
//                    }
//
//                    List<Poi> poisOfCat = mapCatSlugPois.get(item.id);
//
//                    if (poisOfCat != null && poisOfCat.size() == 1) {
//                        mapview.addMarkerToPoi(poisOfCat.get(0).getCode());
//                        return;
//                    }
//
//                    isShowingSubViewForHotspot = true;
//
//                    pathsAtHotspotSubView.clear();
//                    pathsAtHotspotSubView.add(item.id);
//
//                    bottomSheetHotspot.setVisibility(GONE);
//                    recyclerSubViewForHotspot.setVisibility(VISIBLE);
//
//                    renderRecyclerSubViewForHotspot();
//                }
//        ));
//
//    }

//    private void setupSearchBox(Resources resources) {
//        searchbox = findViewById(R.id.searchbox);
//
//        searchbox.setOnQueryTextFocusChangeListener((v, hasFocus) -> {
//            if (hasFocus) {
//                showHideCategorySearchViewWhenTriggerSearch(true);
//            }
//
//        });
//
//        searchbox.setOnClickListener(v -> {
//            if (!v.hasFocus()) {
//                showHideCategorySearchViewWhenTriggerSearch(true);
//            }
//
//        });
//
//        searchbox.setOnCloseListener(() -> {
//            showHideCategorySearchViewWhenTriggerSearch(false);
//            return false;
//        });
//
//        searchbox.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                return false;
//            }
//
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                String trimmedText = newText.trim();
//
//                // Only track long enough search text
//                if (trimmedText.length() >= 2) {
//                    SDK.getInstance().getDataApi().reportSearchPoiEvent(newText);
//                }
//
//                categorySearchView.filter(newText);
//
//                return false;
//            }
//
//        });
//
//        searchbox.setQueryHint(resources.getString(R.string.search_hint));
//    }

    private void showHideViewWhenTriggerNavigation(boolean navigationBegin) {
        if (navigationBegin) {
            mapview.removeCurrentPoiMarker();

            searchbox.setIconified(true);
            searchbox.clearFocus();

            searchbox.setVisibility(View.GONE);

            bottomSheetHotspot.setVisibility(GONE);
        } else {
            searchbox.setVisibility(View.VISIBLE);

            bottomSheetBehaviorHotspot.setState(BottomSheetBehavior.STATE_COLLAPSED);
            bottomSheetHotspot.setVisibility(VISIBLE);
        }
    }

    private void presentPoiInfo(Poi poi) {
        String categoryName = null;

        if (categoryFocusAtStart != null) {
            categoryName = LocaleHelper.getName(getContext(), categoryFocusAtStart.getName());

            txtSheetPoiCategory.setText(categoryName);
        } else {
            txtSheetPoiCategory.setText("");
        }

        String[] allHourComponent = LocaleHelper.getName(getContext(), poi.getSchedule())
                .split("\\|\\s+");

        String hourText = "";

        if (categoryFocusAtStart != null) {
            for (String hourComponent : allHourComponent) {
                if (hourComponent.indexOf(categoryName) != 0) {
                    continue;
                }

                int posBeginToTrim = categoryName.length() + 1;

                while (posBeginToTrim < hourComponent.length()) {
                    char c = hourComponent.charAt(posBeginToTrim);

                    if (c != ' ' && c != ':' && c != '：') {
                        break;
                    }

                    ++posBeginToTrim;
                }

                if (posBeginToTrim < hourComponent.length()) {
                    hourText = hourComponent.substring(posBeginToTrim);
                    break;
                }
            }
        }

        if (TextUtils.isEmpty(hourText) && allHourComponent.length > 1) {
            boolean began = false;

            for (String hourComponent : allHourComponent) {
                if (began) {
                    hourText += "<br>";
                }

                if (hourComponent.indexOf(":") != -1) {
                    hourComponent = "<b>" + hourComponent.replaceAll(":\\s+", ":</b>");
                }

                hourText = hourText + hourComponent;

                began = true;
            }
        } else if (TextUtils.isEmpty(hourText) && allHourComponent.length == 1) {
            hourText = allHourComponent[0];
        }

        txtSheetPoiName.setText(LocaleHelper.getName(getContext(), poi.getName()));
        txtPoiSheetPoiAddress.setText(LocaleHelper.getName(getContext(), poi.getDisplayAddress()));
        txtPoiSheetPoiPhone.setText(LocaleHelper.getName(getContext(), poi.getPhoneNumber()));
        txtPoiSheetPoiOpeningHours.setText(
                Html.fromHtml(hourText)
        );
    }

//    private void renderRecyclerSubViewForHotspot() {
//        List<ServiceDirectoryListItem> items = new ArrayList<>();
//
//        String lastItemInPath = pathsAtHotspotSubView.get(pathsAtHotspotSubView.size() - 1);
//
//        List<Category> subcats = mapCatSlugSubcats.get(lastItemInPath);
//
//        if (subcats != null && subcats.size() != 0) {
//            for (Category category : subcats) {
//                items.add(new ServiceDirectoryListItem(
//                        ServiceDirectoryListItem.TYPE_CATEGORY,
//                        category.getSlug(),
//                        LocaleHelper.getName(reactContext, category.getName()),
//                        null,
//                        category.getSlug()
//                ));
//
//            }
//
//        } else {
//            List<Poi> poisAtCat = mapCatSlugPois.get(lastItemInPath);
//
//            for (Poi poi : poisAtCat) {
//                items.add(new ServiceDirectoryListItem(
//                        ServiceDirectoryListItem.TYPE_POI,
//                        poi.getCode(),
//                        LocaleHelper.getName(reactContext, poi.getName()),
//                        LocaleHelper.getName(reactContext, poi.getDisplayAddress()),
//                        lastItemInPath
//                ));
//
//            }
//
//        }
//
//        recyclerSubViewForHotspot.setAdapter(new ServiceDirectoryAdapter(
//                reactContext,
//                items,
//                R.layout.item_service_directory,
//                clickItem -> {
//                    if (clickItem.type == ServiceDirectoryListItem.TYPE_CATEGORY) {
//                        List<Poi> poisAtCat = mapCatSlugPois.get(clickItem.id);
//
//                        if (poisAtCat.size() == 1) {
//                            closeRecyclerSubViewForHotspot();
//
//                            focusOnPoiClickedFromHotspot(
//                                    poisAtCat.get(0).getCode(),
//                                    categoryBySlug.get(clickItem.id)
//                            );
//
//                        } else {
//                            pathsAtHotspotSubView.add(clickItem.id);
//                            renderRecyclerSubViewForHotspot();
//                        }
//
//                    } else {
//                        Category parentCategory = categoryBySlug.get(
//                                pathsAtHotspotSubView.get(pathsAtHotspotSubView.size() - 1)
//                        );
//
//                        closeRecyclerSubViewForHotspot();
//
//                        focusOnPoiClickedFromHotspot(clickItem.id, parentCategory);
//
//                        mapview.addMarkerToPoi(clickItem.id);
//                    }
//
//                }
//
//        ));
//
//    }

//    private void closeRecyclerSubViewForHotspot() {
//        isShowingSubViewForHotspot = false;
//
//        recyclerSubViewForHotspot.setVisibility(GONE);
//        recyclerSubViewForHotspot.setAdapter(null);
//        pathsAtHotspotSubView.clear();
//
//        if (!isShowingCategorySearchView) {
//            bottomSheetBehaviorHotspot.setState(BottomSheetBehavior.STATE_COLLAPSED);
//            bottomSheetHotspot.setVisibility(VISIBLE);
//        }
//
//    }

    private void focusOnPoiClickedFromHotspot(String poiCode, Category parentCategory) {
        poiCodeFocusAtStart = poiCode;

        if (mapPoiCodeCatCount.get(poiCode) > 1) {
            categoryFocusAtStart = parentCategory;
        }

        mapview.addMarkerToPoi(poiCode);
    }

//    private class MyBottomSheetButtonOnClickListener implements OnClickListener {
//        private Poi poi;
//        private String categoryId;
//        private Resources resources;
//
//
//
//        public MyBottomSheetButtonOnClickListener(Poi poi, String categoryId) {
//            this.poi = poi;
//            this.categoryId = categoryId;
//
//            resources = LocaleHelper.getResources(reactContext);
//        }
//
//
//        @Override
//        public void onClick(View v) {
//            switch (v.getId()) {
//                case R.id.btnSheetNavigate:
//                    if (btnSheetNavigateClicked) {
//                        return;
//                    }
//
//                    navigationView = new NavigationView(reactContext, poi.getCode(), MapView.this);
//                    addView(navigationView);
//
//                    btnSheetNavigateClicked = true;
//
//                    break;
//
//                case R.id.imgSaveLocation:
//                    Preferences.addSavedLocation(
//                            reactContext,
//                            new Preferences.SaveLocation(poi.getCode(), categoryId)
//                    );
//
//                    new AlertDialog.Builder(reactContext)
//                            .setTitle(resources.getString(R.string.feedback_dialog_title))
//                            .setMessage(resources.getString(R.string.location_is_saved))
//                            .setPositiveButton(resources.getString(R.string.ok), (dialog, which) -> {
//                                dialog.dismiss();
//                            })
//                            .show();
//
//                    break;
//
//                case R.id.imgSharePoi:
//                    String shareMessage = String.format(
//                            "%s | %s: %s | %s: %s | %s: %s",
//
//                            LocaleHelper.getName(getContext(), poi.getName()),
//
//                            resources.getString(R.string.location),
//                            LocaleHelper.getName(getContext(), poi.getDisplayAddress()),
//
//                            resources.getString(R.string.phonenumber),
//                            LocaleHelper.getName(getContext(), poi.getPhoneNumber()),
//
//                            resources.getString(R.string.openinghours),
//                            LocaleHelper.getName(getContext(), poi.getSchedule())
//                    );
//
//                    Intent intentShare = new Intent();
//
//                    intentShare.setAction(Intent.ACTION_SEND);
//                    intentShare.setType("text/plain");
//                    intentShare.putExtra(Intent.EXTRA_TEXT, shareMessage);
//
//                    intentShare = Intent.createChooser(intentShare, resources.getString(R.string.share));
//                    getContext().startActivity(intentShare);
//
//                    break;
//
//                case R.id.btnCloseBottomSheet:
//                    mapview.removeCurrentPoiMarker();
//
//                    break;
//
//                case R.id.imgSheetCallPoi:
//                    String phoneNumber = LocaleHelper.getName(reactContext, poi.getPhoneNumber()).trim();
//
//                    if (!TextUtils.isEmpty(phoneNumber)) {
//                        Intent intent = new Intent(Intent.ACTION_DIAL);
//                        intent.setData(Uri.parse("tel: " + phoneNumber));
//
//                        reactContext.startActivity(intent);
//                    }
//
//                    break;
//
//            }
//
//        }
//
//    }

//    private final LocationEngine.EngineCallback myLocationEngineCallback = new LocationEngine.EngineCallback() {
//        @Override
//        public void onInitializationDone(boolean b) {
//
//        }
//
//        @Override
//        public void onLocationUpdated(VenueLocation location) {
//            // No more message to deliver
//            if (deliverLocBasedMessageIds.size() == allLocationBasedMessage.length) {
//                return;
//            }
//
//            Resources resources = LocaleHelper.getResources(reactContext);
//            String language = Preferences.getLanguage(reactContext);
//
//            Point pointLocation = location.getTurfPoint();
//
//            for (int i = 0; i < allLocationBasedMessage.length; ++i) {
//                if (deliverLocBasedMessageIds.contains(i)) {
//                    continue;
//                }
//
//                LocationBasedMessage curMessage = allLocationBasedMessage[i];
//
//                if (location.level != curMessage.level) {
//                    continue;
//                }
//
//                boolean messageCanDeliver = false;
//
//                for (List<Double> lnglat : curMessage.nodes) {
//                    double dist = TurfMeasurement.distance(
//                            pointLocation,
//                            Point.fromLngLat(lnglat.get(0), lnglat.get(1)),
//                            TurfConstants.UNIT_METERS
//                    );
//
//                    if (dist <= curMessage.thresholdMeter) {
//                        messageCanDeliver = true;
//                        break;
//                    }
//
//                }
//
//                if (!messageCanDeliver) {
//                    continue;
//                }
//
//                deliverLocBasedMessageIds.add(i);
//
//                String title = "";
//
//                if (curMessage.title.containsKey(language)) {
//                    title = curMessage.title.get(language);
//                }
//
//                String message = "";
//
//                if (curMessage.message.containsKey(language)) {
//                    message = curMessage.message.get(language);
//                }
//
//                String titleForFinal = title;
//                String messageForFinal = message;
//
//                post(() -> {
//                    new AlertDialog.Builder(reactContext)
//                            .setTitle(titleForFinal)
//                            .setMessage(messageForFinal)
//                            .setPositiveButton(resources.getString(R.string.ok), (dialogInterface, which) -> {
//                                dialogInterface.dismiss();
//                            })
//                            .show();
//
//                });
//
//            }
//
//        }
//
//        @Override
//        public void onResetWiFiNeeded() {
//
//        }
//
//    };

//    private class MyNavigationCallback implements CustomMapView.NavigationDemonstrationCallback, CustomMapView.NavigationCallback {
//        private boolean started = false;
//
//
//
//        @Override
//        public void onProgress(double v) {
//
//        }
//
//
//        @Override
//        public void onArrive() {
//            doCommonWorkWhenNavigationEnd();
//        }
//
//
//        @Override
//        public void onStart() {
//            isShowingNavigation = true;
//
//            onCloseView();
//
//            started = true;
//            showHideViewWhenTriggerNavigation(true);
//        }
//
//
//        @Override
//        public void onFailed() {
//            doCommonWorkWhenNavigationEnd();
//        }
//
//
//        @Override
//        public void onEnd() {
//            doCommonWorkWhenNavigationEnd();
//        }
//
//
//        private void doCommonWorkWhenNavigationEnd() {
//            isShowingNavigation = false;
//
//            if (started) {
//                showHideViewWhenTriggerNavigation(false);
//            }
//
//        }
//
//    }

    //START: Refactor these code

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

    //END: Refactor these code

    private class HotspotItem {
        public Poi poi = null;
        public Category category = null;
        public int bookmarkOrder = 0;
    }
}
