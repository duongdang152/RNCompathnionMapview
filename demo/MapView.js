import React from "react";
import {
  StyleSheet,
  NativeModules,
  requireNativeComponent,
  findNodeHandle
} from "react-native";
import PropTypes from "prop-types";

const MapViewComponent = requireNativeComponent("CustomMapView");
const MapViewModule = NativeModules.MapViewModule;

const styles = StyleSheet.create({
  mapviewContainer: { flex: 1, width: "100%" }
});

class MapView extends React.Component {
  constructor(props) {
    super(props);
  }

  static propTypes = {
    style: PropTypes.oneOfType([
      PropTypes.object,
      PropTypes.number,
      PropTypes.array
    ])
  };

  focusPOI = poiCode => {
    MapViewModule.focusPOI(findNodeHandle(this), poiCode);
  };

  unfocusPOI = () => {
    MapViewModule.unfocusPOI(findNodeHandle(this));
  };

  navigatePOIToPOI = (startPOI, endPOI, disabledPath) => {
    MapViewModule.navigatePOIToPOI(
      findNodeHandle(this),
      startPOI,
      endPOI,
      disabledPath
    );
  };

  render() {
    return (
      <MapViewComponent
        style={[styles.mapviewContainer, { ...this.props.style }]}
      />
    );
  }
}

export default MapView;
