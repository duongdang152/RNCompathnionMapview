# RNCompathnionMapview

## Installation

```
npm install git+http://gitlab+deploy-token-2:xwZZxey6Mzu3e18S1x__@gitpub.compathnion.com/IPS/sdk/rn-map-view.git
react-native link RNCompathnionMapview
```

### Android

Add the following code to the project `build.gradle`

```
allprojects {
    repositories {
        ...
        maven {
            url "https://mavenrepo.compathnion.com/artifactory/compathnion-libs-release"
            credentials {
                username 'mapviewsdkusers'
                password 'x6fhZZRMYzDNJsHX'
            }
        }
        ...
    }
}
```

### iOS

1. Link the 2 frameworks `Maps.framework` and `Mapbox.framework` in `{projectDir}/node_modules/RNCompathnionMapview/ios/Frameworks` to the xCode project.
2. Go to the project's `General` page, at the `Framesworks, Libraries and Embedded Content` section, set `Maps.framework` to `Embed & Signed` and set `Mapbox.framework` to `Embed without signing`.
3. Open the project `Info.plist` and add the following:

```
<key>MGLMapboxAccessToken</key>
<string>pk.eyJ1IjoiYXJ0aHVyY2hhbiIsImEiOiJjanBiZDJyazQwNnRkM3BtdTdkZzF3YjkwIn0.An8wLxZ8sD75ZxQMsVKJbg</string>
<key>MGLMapboxMetricsEnabledSettingShownInApp</key>
<true/>
```

## Usage

### Props

| Parameter                | Type | Required | Description                                        |
| ------------------------ | ---- | -------- | -------------------------------------------------- |
| onPOIClick               | Func | No       | Callback for when user clicks on a POI             |
| onPOIUnclick             | Func | No       | Callback for when POI loses focus                  |
| onLocationMessageReceive | Func | No       | Callback for when user receives a location message |

### Methods

| Method               | Parameters                                                                                                                         | Description                                                                                                                                                                    |
| -------------------- | ---------------------------------------------------------------------------------------------------------------------------------- | ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------ |
| initMap              | baseHostUrl: String, venueCode: String, credUsername: String, credPassword: String, credClientId: String, credClientSecret: String | Initialise the SDK. Call this function on app launch.                                                                                                                          |
| focusPOI             | poiCode: String                                                                                                                    | Select the POI with code `poiCode`                                                                                                                                             |
| unfocusPOI           |                                                                                                                                    | Unfocus the selected POI                                                                                                                                                       |
| navigatePOIToPOI     | startPOI: String, endPOI: String, disabledPath: Boolean                                                                            | Start navigation from POI with code `startPOI` to POI with code `endPOI`. `disabledPath` determines whether the disabled path is used or not. Default `disabledPath` is false. |
| navigateCurrentToPOI | destinationPOI: String, disabledPath: Boolean                                                                                      | Start navigation from current location to POI with code `destinationPOI`. `disabledPath` determines whether the disabled path is used or not. Default `disabledPath` is false. |

## Changelog

### 0.1.0:

- Android version added.

### 0.2.0:

- iOS version added.

### 0.2.1:

- Update Map SDK.