import React, { Component } from "react";
import {
  StyleSheet,
  Text,
  View,
  TouchableOpacity,
  requireNativeComponent,
  NativeModules,
  findNodeHandle
} from "react-native";
import MapView from "./MapView";

export default class App extends Component {
  constructor(props) {
    super(props);
    this.state = {
      poi: "None"
    };
  }

  selectPoi = () => {
    this.mapview.focusPOI("hkchdgtbbeu-unit-room-1166580");
  };

  navigateP2P = () => {
    this.mapview.unfocusPOI();
    this.mapview.navigatePOIToPOI(
      "hkchdgtbbeu-unit-room-1166580",
      "hkchd1tbbpcu-unit-kiosk-1166932",
      false
    );
  };

  onPOIClick = poi => {
    this.setState({
      poi
    });
  };

  onPOIUnclick = () => {
    this.setState({
      poi: "None"
    });
  };

  render() {
    return (
      <View style={styles.container}>
        <MapView
          ref={ref => (this.mapview = ref)}
          onPOIClick={this.onPOIClick}
          onPOIUnclick={this.onPOIUnclick}
        />
        <View style={styles.buttonsContainer}>
          <TouchableOpacity onPress={this.selectPoi}>
            <View style={styles.button}>
              <Text>POI</Text>
            </View>
          </TouchableOpacity>
          <TouchableOpacity onPress={this.navigateP2P}>
            <View style={styles.button}>
              <Text>Navigate P2P</Text>
            </View>
          </TouchableOpacity>
          <View style={styles.poiContainer}>
            <Text>POI: {this.state.poi}</Text>
          </View>
        </View>
      </View>
    );
  }
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: "center",
    alignItems: "center",
    backgroundColor: "#F5FCFF"
  },
  buttonsContainer: {
    flexDirection: "row"
  },
  button: {
    height: 50,
    width: 80,
    borderWidth: 1,
    marginHorizontal: 8,
    justifyContent: "center",
    alignItems: "center",
    backgroundColor: "#bbb"
  },
  poiContainer: {
    height: 50,
    width: 160,
    marginHorizontal: 8,
    justifyContent: "center",
    alignItems: "center"
  }
});
