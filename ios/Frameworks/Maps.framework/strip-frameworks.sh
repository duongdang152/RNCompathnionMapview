# Compathnion Technology Limited

MAPS_FRAMEWORK_EXECUTABLE_PATH="${BUILT_PRODUCTS_DIR}/${FRAMEWORKS_FOLDER_PATH}/Maps.framework/Maps"
MAPS_EXTRACTED_ARCHS=()
for ARCH in $ARCHS
do
lipo -extract "$ARCH" "$MAPS_FRAMEWORK_EXECUTABLE_PATH" -o "$MAPS_FRAMEWORK_EXECUTABLE_PATH-$ARCH"
MAPS_EXTRACTED_ARCHS+=("$MAPS_FRAMEWORK_EXECUTABLE_PATH-$ARCH")
done
lipo -o "$MAPS_FRAMEWORK_EXECUTABLE_PATH-merged" -create "${MAPS_EXTRACTED_ARCHS[@]}"
rm "${MAPS_EXTRACTED_ARCHS[@]}"
rm "$MAPS_FRAMEWORK_EXECUTABLE_PATH"
mv "$MAPS_FRAMEWORK_EXECUTABLE_PATH-merged" "$MAPS_FRAMEWORK_EXECUTABLE_PATH"

MapToken=$(/usr/libexec/PlistBuddy -c "print :MGLMapboxAccessToken" "${BUILT_PRODUCTS_DIR}/${FRAMEWORKS_FOLDER_PATH}/Maps.framework/Info.plist")
MapMetrics=$(/usr/libexec/PlistBuddy -c "print :MGLMapboxMetricsEnabledSettingShownInApp" "${BUILT_PRODUCTS_DIR}/${FRAMEWORKS_FOLDER_PATH}/Maps.framework/Info.plist")

if test -z $(/usr/libexec/PlistBuddy -c "print :MGLMapboxAccessToken" "${PRODUCT_SETTINGS_PATH}")
then
    /usr/libexec/PlistBuddy -c "Add :MGLMapboxAccessToken string ${MapToken}" "${PRODUCT_SETTINGS_PATH}"
fi

if test -z $(/usr/libexec/PlistBuddy -c "print :MGLMapboxMetricsEnabledSettingShownInApp" "${PRODUCT_SETTINGS_PATH}")
then
    /usr/libexec/PlistBuddy -c "Add :MGLMapboxMetricsEnabledSettingShownInApp bool ${MapMetrics}" "${PRODUCT_SETTINGS_PATH}"
fi


if test -z $(/usr/libexec/PlistBuddy -c "print :MGLMapboxAccessToken" "${BUILT_PRODUCTS_DIR}/${INFOPLIST_PATH}")
then
    /usr/libexec/PlistBuddy -c "Add :MGLMapboxAccessToken string ${MapToken}" "${BUILT_PRODUCTS_DIR}/${INFOPLIST_PATH}"
else
    /usr/libexec/PlistBuddy -c "Set :MGLMapboxAccessToken ${MapToken}" "${BUILT_PRODUCTS_DIR}/${INFOPLIST_PATH}"
fi

if test -z $(/usr/libexec/PlistBuddy -c "print :MGLMapboxMetricsEnabledSettingShownInApp" "${BUILT_PRODUCTS_DIR}/${INFOPLIST_PATH}")
then
    /usr/libexec/PlistBuddy -c "Add :MGLMapboxMetricsEnabledSettingShownInApp bool ${MapMetrics}" "${BUILT_PRODUCTS_DIR}/${INFOPLIST_PATH}"
else
    /usr/libexec/PlistBuddy -c "Set :MGLMapboxMetricsEnabledSettingShownInApp ${MapMetrics}" "${BUILT_PRODUCTS_DIR}/${INFOPLIST_PATH}"
fi
