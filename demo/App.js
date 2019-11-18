import React, { Component } from "react";
import {
  StyleSheet,
  Text,
  View,
  TouchableOpacity,
  requireNativeComponent,
  NativeModules,
  findNodeHandle,
  Alert
} from "react-native";
import MapView, { initMap } from "./MapView";

export default class App extends Component {
  constructor(props) {
    super(props);
    this.state = {
      poi: "None",
      locationTitle: null,
      locationMessage: null
    };
  }

  componentDidMount() {
    initMap(
      "https://hkch-staging.compathnion.com/",
      "hkch",
      "hkch@sagadigits.com",
      "G^sx4;(yEV",
      "0e8de7c60c1c",
      "zlsYYDkWx6n9ph9BZPQVjlSU"
    );
  }

  componentDidUpdate(prevProps, prevState) {
    if (
      prevState.locationMessage !== this.state.locationMessage &&
      this.state.locationMessage !== null
    ) {
      Alert.alert(
        this.state.locationTitle,
        this.state.locationMessage,
        [
          {
            text: "OK",
            onPress: () =>
              this.setState({ locationTitle: null, locationMessage: null })
          }
        ],
        { cancelable: false }
      );
    }
  }

  selectPoi = () => {
    this.mapview.focusPOI("cm1tr1-100");
  };

  navigateP2P = () => {
    this.mapview.unfocusPOI();
    this.mapview.navigatePOIToPOI("cm1tr1-100", "cm1ta1-101", false);
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

  onLocationMessageReceive = (title, message) => {
    this.setState({
      locationTitle: title,
      locationMessage: message
    });
  };

  // render() {
  //   // <MapView
  //       //   ref={ref => (this.mapview = ref)}
  //       //   onPOIClick={this.onPOIClick}
  //       //   onPOIUnclick={this.onPOIUnclick}
  //       //   onLocationMessageReceive={this.onLocationMessageReceive}
  //       // />
  //   return (
  //     <View style={styles.container}>
  //       <MapView/>
  //       <View style={styles.buttonsContainer}>
  //         <TouchableOpacity onPress={this.selectPoi}>
  //           <View style={styles.button}>
  //             <Text>POI</Text>
  //           </View>
  //         </TouchableOpacity>
  //         <TouchableOpacity onPress={this.navigateP2P}>
  //           <View style={styles.button}>
  //             <Text>Navigate P2P</Text>
  //           </View>
  //         </TouchableOpacity>
  //         <View style={styles.poiContainer}>
  //           <Text>POI: {this.state.poi}</Text>
  //         </View>
  //       </View>
  //     </View>
  //   );
  // }

    render() {
    return (
      <View style={styles.container}>
        <MapView
          ref={ref => (this.mapview = ref)}
          onPOIClick={this.onPOIClick}
          onPOIUnclick={this.onPOIUnclick}
          onLocationMessageReceive={this.onLocationMessageReceive}
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
