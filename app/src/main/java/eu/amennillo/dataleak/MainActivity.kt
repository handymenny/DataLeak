package eu.amennillo.dataleak

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import eu.amennillo.dataleak.data.AppInfo
import eu.amennillo.dataleak.ui.AboutScreen
import eu.amennillo.dataleak.ui.MainScreen
import eu.amennillo.dataleak.ui.theme.DataLeakTheme
import eu.amennillo.dataleak.util.getInstalledAppsInLeak
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
  private var apps = listOf<AppInfo>()
  private var dataLoaded = false

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    splashScreen()
    loadApps()
    enableEdgeToEdge()
    mainContent()
  }

  private fun splashScreen() {
    installSplashScreen().setKeepOnScreenCondition({ !dataLoaded })
  }

  private fun loadApps() {
    lifecycleScope.launch {
      apps = getInstalledAppsInLeak(this@MainActivity)
      dataLoaded = true
    }
  }

  private fun mainContent() {
    setContent {
      DataLeakTheme {
        val navController = rememberNavController()
        NavHost(navController, startDestination = "main") {
          composable("main") { MainScreen(navController, apps) }
          composable("aboutLibraries") { AboutScreen() }
        }
      }
    }
  }
}


