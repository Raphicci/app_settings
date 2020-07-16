import Flutter
import UIKit

/// Swift app settings plugin with method channel call handler.
public class SwiftAppSettingsPlugin: NSObject, FlutterPlugin, FlutterApplicationLifeCycleDelegate {
  var _result: FlutterResult? = nil
  
  /// Private method to open device settings window
  private func openSettings() {
      if let url = URL(string: UIApplication.openSettingsURLString) {
        if #available(iOS 10.0, *) {
            UIApplication.shared.open(url, options: [:])
        } else {
            UIApplication.shared.openURL(url)
        }
      }
  }

  /// Public register method for Flutter plugin registrar.
  public static func register(with registrar: FlutterPluginRegistrar) {
    let channel = FlutterMethodChannel(name: "app_settings", binaryMessenger: registrar.messenger())
    let instance = SwiftAppSettingsPlugin()
    registrar.addApplicationDelegate(instance)
    registrar.addMethodCallDelegate(instance, channel: channel)
  }

  /// Public handler method for managing method channel calls.
  public func handle(_ call: FlutterMethodCall, result: @escaping FlutterResult) {
    _result = result
    openSettings()
  }

  public func applicationDidBecomeActive(_ application: UIApplication) {
    guard let result = _result else {
        return
    }
    result(nil)
  }
}