# RNCompathnionMapview

## Installation

```
npm install git+http://gitlab+deploy-token-1:M5zxNiMUEgz4wo7hNe-L@gitpub.compathnion.com/IPS/sdk/rn-map-view.git
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
