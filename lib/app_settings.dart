import 'dart:async';
import 'package:flutter/services.dart';

class AppSettings {
  // Static constant variable to initialize MethodChannel of 'app_settings'.
  static const MethodChannel _channel = const MethodChannel('app_settings');

  /// Future async method call to open WIFI settings.
  static Future<void> openWIFISettings() async {
    return _channel.invokeMethod('wifi');
  }

  /// Future async method call to open location settings.
  static Future<void> openLocationSettings() {
    return _channel.invokeMethod('location');
  }

  /// Future async method call to open security settings.
  static Future<void> openSecuritySettings() {
    return _channel.invokeMethod('security');
  }

  /// Future async method call to open bluetooth settings.
  static Future<void> openBluetoothSettings() {
    return _channel.invokeMethod('bluetooth');
  }

  /// Future async method call to open data roaming settings.
  static Future<void> openDataRoamingSettings() {
    return _channel.invokeMethod('data_roaming');
  }

  /// Future async method call to open date settings.
  static Future<void> openDateSettings() {
    return _channel.invokeMethod('date');
  }

  /// Future async method call to open display settings.
  static Future<void> openDisplaySettings() {
    return _channel.invokeMethod('display');
  }

  /// Future async method call to open notification settings.
  static Future<void> openNotificationSettings() {
    return _channel.invokeMethod('notification');
  }

  /// Future async method call to open sound settings.
  static Future<void> openSoundSettings() {
    return _channel.invokeMethod('sound');
  }

  /// Future async method call to open internal storage settings.
  static Future<void> openInternalStorageSettings() {
    return _channel.invokeMethod('internal_storage');
  }

  /// Future async method call to open app specific settings screen.
  static Future<void> openAppSettings() {
    return _channel.invokeMethod('app_settings');
  }
}
