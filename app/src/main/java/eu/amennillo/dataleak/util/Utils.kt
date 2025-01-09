package eu.amennillo.dataleak.util

import android.content.Context
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import eu.amennillo.dataleak.data.AppInfo
import eu.amennillo.dataleak.data.LeakedPackage

fun getInstalledPackages(context: Context): List<PackageInfo> {
  val packageManager = context.packageManager
  return packageManager.getInstalledPackages(PackageManager.GET_META_DATA)
}

fun getInstalledAppsInLeak(context: Context): List<AppInfo> {
  val leakedPackagesNames =
    LeakedPackage.import(context).filter { it.isAndroid() }.map { it.packageName }.toSet()
  val installedPackages = getInstalledPackages(context)
  val leakedPackages =
    installedPackages.filter { leakedPackagesNames.contains(it.packageName.lowercase()) }

  val apps = leakedPackages.map {
    AppInfo.fromPackageInfo(it, context)
  }.sortedWith(compareBy({ it.isDisabled }, { it.appName }))

  return apps
}

fun filterAppListByName(apps: List<AppInfo>, query: String): List<AppInfo> {
  val queryTrimmed = query.trim()
  if (queryTrimmed.isEmpty()) return apps


  return apps.filter {
    it.appName.contains(queryTrimmed, true) || it.packageName.contains(
      queryTrimmed,
      true
    )
  }
}
