package eu.amennillo.dataleak.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.mikepenz.aboutlibraries.ui.compose.m3.LibrariesContainer
import eu.amennillo.dataleak.BuildConfig
import eu.amennillo.dataleak.R

@Composable
fun AboutScreen() {
  val appVersion = BuildConfig.VERSION_NAME
  val appName = stringResource(R.string.app_name)

  Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
    Column(modifier = Modifier.padding(innerPadding)) {
      Text(
        text = stringResource(R.string.about_title),
        style = MaterialTheme.typography.titleLarge,
        modifier = Modifier
          .padding(16.dp)
          .fillMaxWidth()
          .wrapContentWidth(Alignment.CenterHorizontally)
      )
      Column {
        Text(
          text = stringResource(R.string.version_desc, appName, appVersion),
          style = MaterialTheme.typography.bodyLarge,
          textAlign = TextAlign.Center,
          modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
        )
        Text(
          text = stringResource(R.string.about_desc),
          style = MaterialTheme.typography.bodyLarge,
          textAlign = TextAlign.Center,
          modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
        )
      }
      LibrariesContainer()
    }
  }
}
