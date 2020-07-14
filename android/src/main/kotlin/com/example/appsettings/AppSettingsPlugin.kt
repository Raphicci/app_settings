package com.example.appsettings

import android.content.Intent
import android.provider.Settings
import android.net.Uri

import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.embedding.engine.plugins.FlutterPlugin.FlutterPluginBinding
import io.flutter.embedding.engine.plugins.activity.ActivityAware
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.MethodCallHandler
import io.flutter.plugin.common.MethodChannel.Result
import android.app.Activity
import android.content.Context

class AppSettingsPlugin: MethodCallHandler, FlutterPlugin, ActivityAware, PluginRegistry.ActivityResultListener  {
  private var mActivity: Activity? = null
  private var _result: Result? = null

  /// Private method to open device settings window
  private fun openSettings(url: String) {
    try {
      this.mActivity?.startActivityForResult(Intent(url), 1234)
    } catch(e:Exception) {
      // Default to APP Settings if setting activity fails to load/be available on device
      openAppSettings()
    }
  }

  private fun openAppSettings() {
    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    val uri = Uri.fromParts("package", this.mActivity?.packageName, null)
    intent.data = uri
    this.mActivity?.startActivityForResult(intent, 1234)
  }

  override fun onActivityResult(requestCode: Int, result: Int, intent: Intent?): Boolean {
      if (requestCode == 1234) {
          _result?.success(null)
          return true
      }
      return false
  }

  override fun onAttachedToActivity(binding: ActivityPluginBinding) {
    mActivity = binding.getActivity()
      binding.addActivityResultListener(this)
  }

  override fun onReattachedToActivityForConfigChanges(binding: ActivityPluginBinding) {
    mActivity = binding.getActivity()
      binding.removeActivityResultListener(this)
      binding.addActivityResultListener(this)
  }

  override fun onDetachedFromActivity() {
    mActivity = null
  }

  override fun onDetachedFromActivityForConfigChanges() {
    mActivity = null
  }

  override fun onAttachedToEngine(binding: FlutterPluginBinding) {
    val channel = MethodChannel(binding.getBinaryMessenger(), "app_settings")
    channel.setMethodCallHandler(this)
  }

  override fun onDetachedFromEngine(binding: FlutterPluginBinding) {}

  /// Handler method to manage method channel calls.
  override fun onMethodCall(call: MethodCall, result: Result) {
    _result = result;
    when (call.method) {
        "wifi" -> openSettings(Settings.ACTION_WIFI_SETTINGS)
        "location" -> openSettings(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
        "security" -> openSettings(Settings.ACTION_SECURITY_SETTINGS)
        "bluetooth" -> openSettings(Settings.ACTION_BLUETOOTH_SETTINGS)
        "data_roaming" -> openSettings(Settings.ACTION_DATA_ROAMING_SETTINGS)
        "date" -> openSettings(Settings.ACTION_DATE_SETTINGS)
        "display" -> openSettings(Settings.ACTION_DISPLAY_SETTINGS)
        "notification" -> openSettings(Settings.ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS)
        "sound" -> openSettings(Settings.ACTION_SOUND_SETTINGS)
        "internal_storage" -> openSettings(Settings.ACTION_INTERNAL_STORAGE_SETTINGS)
        "app_settings" -> openAppSettings()
    }
  }
}