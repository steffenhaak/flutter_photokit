package land.crater.flutterphotokit

import android.util.Log
import java.io.File

import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.MethodCallHandler
import io.flutter.plugin.common.MethodChannel.Result
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.PluginRegistry.Registrar

class FlutterPhotokitPlugin(): MethodCallHandler {
  companion object {
    @JvmStatic
    fun registerWith(registrar: Registrar): Unit {
      val channel = MethodChannel(registrar.messenger(), "flutter_photokit")
      channel.setMethodCallHandler(FlutterPhotokitPlugin())
    }
  }

  override fun onMethodCall(call: MethodCall, result: Result): Unit {
    if (call.method.equals("saveToCameraRoll")) {
      val filePath: String = call.argument("filePath")
      val srcFile: File = File(filePath)
      val fileName: String = srcFile.nameWithoutExtension + "." + srcFile.extension
      val file = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "spesh")
      if (!file?.mkdirs()) {
          Log.e("", "Directory not created")
      }
      val file = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "spesh", fileName)
      File(filePath).copyTo(target: file)
      result.success(file.filePath)
    } else {
      result.notImplemented()
    }
  }
}
