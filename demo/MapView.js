import React from "react";
import {
  StyleSheet,
  NativeModules,
  requireNativeComponent,
  findNodeHandle,
  NativeEventEmitter
} from "react-native";
import PropTypes from "prop-types";

const MapViewComponent = requireNativeComponent("MapViewComponent");
const MapViewModule = NativeModules.MapViewModule;
const eventEmitter = new NativeEventEmitter(NativeModules.CustomMapView);

const styles = StyleSheet.create({
  mapviewContainer: { flex: 1, width: "100%" }
});

export function initMap(
  baseHostUrl,
  venueCode,
  credUsername,
  credPassword,
  credClientId,
  credClientSecret
) {
  MapViewModule.initMap(
    baseHostUrl,
    venueCode,
    credUsername,
    credPassword,
    credClientId,
    credClientSecret
  );
}

class MapView extends React.Component {
  constructor(props) {
    super(props);
  }

  static propTypes = {
    style: PropTypes.oneOfType([
      PropTypes.object,
      PropTypes.number,
      PropTypes.array
    ]),
    onPOIClick: PropTypes.func,
    onPOIUnclick: PropTypes.func
  };

  componentDidMount() {
    eventEmitter.addListener("onPOIClick", event => {
      if (this.props.onPOIClick) this.props.onPOIClick(event.poiCode);
    });
    eventEmitter.addListener("onPOIUnclick", event => {
      if (this.props.onPOIUnclick) this.props.onPOIUnclick();
    });
  }

  componentWillUnmount() {
    eventEmitter.removeAllListeners();
  }

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
