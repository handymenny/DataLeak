package eu.amennillo.dataleak.data

import android.content.Context
import android.util.Log
import com.opencsv.CSVParserBuilder
import com.opencsv.CSVReaderBuilder


data class LeakedPackage(val appName: String, var packageName: String, val occurrences: Int?) {
  init {
    this.packageName = packageName.trim().lowercase()
  }

  fun isAndroid(): Boolean {
    return packageName.toIntOrNull() == null
  }


  companion object {
    fun import(context: Context): List<LeakedPackage> {
      val resource = context.resources.openRawResource(eu.amennillo.dataleak.R.raw.merged_clean)
      val parser = CSVParserBuilder().build()
      val reader = CSVReaderBuilder(resource.bufferedReader())
        .withCSVParser(parser)
        .withSkipLines(1)
        .build()

      val leakedPackages = reader.readAll().mapNotNull { line ->
        if (line.size < 2 || line.size > 3) {
          Log.d("DataLeak", "Invalid leaked package: ${line.joinToString("|")}")
          return@mapNotNull null
        }

        LeakedPackage(line[0], line[1], line.getOrNull(2)?.toInt())
      }

      return leakedPackages
    }
  }
}