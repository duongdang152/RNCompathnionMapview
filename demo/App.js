import React, {Component} from 'react';
import {StyleSheet, Text, View, TouchableOpacity, requireNativeComponent} from 'react-native';

let MapView = requireNativeComponent('CustomMapView');

export default class App extends Component {
  
  selectPoi = () => {

  }

  render() {
    return (
      <View style={styles.container}>
        <MapView style={styles.mapView}/>
        <View style={styles.buttonsContainer}>
          <TouchableOpacity onPress={this.selectPoi}>
            <View style={styles.button}>
              <Text>POI</Text>
            </View>
          </TouchableOpacity>
        </View>
      </View>
    );
  }
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
    backgroundColor: '#F5FCFF',
  },
  mapView: {
    flex: 1,
    width: "100%"
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
  }
});
