import React from "react";
import {
  NativeModules,
  requireNativeComponent,
  findNodeHandle
} from "react-native";

const MapViewComponent = requireNativeComponent("CustomMapView");
const MapViewModule = NativeModules.MapViewModule;

class MapView extends React.Component {
  focusPOI = poiCode => {
    // MapViewModule.focusPOI(findNodeHandle(this.mapview), poiCode);
    MapViewModule.focusPOI(findNodeHandle(this), poiCode);
  };

  render() {
    return (
      <MapViewComponent
        ref={ref => (this.mapview = ref)}
        style={{ flex: 1, width: "100%" }}
      />
    );
  }
}

export default MapView;
