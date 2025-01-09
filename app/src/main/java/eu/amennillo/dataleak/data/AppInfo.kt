package eu.amennillo.dataleak.data

import android.content.Context
import android.content.Intent
import android.content.pm.PackageInfo
import android.graphics.drawable.Drawable
import android.net.Uri
import android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS

data class AppInfo(
  val appName: String,
  val packageName: String,
  val appIcon: Drawable,
  val isDisabled: Boolean
) {

  fun openInfo(context: Context) {
    val intent = Intent(ACTION_APPLICATION_DETAILS_SETTINGS)
    intent.data = Uri.parse("package:${packageName}")

    context.startActivity(intent)
  }


  companion object {
    fun fromPackageInfo(packageInfo: PackageInfo, context: Context): AppInfo {
      val packageManager = context.packageManager
      val applicationInfo = packageInfo.applicationInfo
      val appName = applicationInfo?.let { packageManager.getApplicationLabel(it).toString() } ?: ""
      val appIcon =
        applicationInfo?.let { packageManager.getApplicationIcon(it) } ?: context.getDrawable(
          android.R.drawable.sym_def_app_icon
        )!!
      // check if application is disabled/frozen
      val isDisabled = applicationInfo?.enabled == false
      return AppInfo(appName, packageInfo.packageName, appIcon, isDisabled)
    }
  }
}