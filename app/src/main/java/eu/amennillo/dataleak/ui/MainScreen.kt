package eu.amennillo.dataleak.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import eu.amennillo.dataleak.R
import eu.amennillo.dataleak.data.AppInfo

@Composable
fun MainScreen(navController: NavHostController, installedPackagesInLeak: List<AppInfo>) {
  Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
    Column(modifier = Modifier.padding(innerPadding)) {
      Description()
      FilteredList(installedPackagesInLeak, Modifier.weight(1f))
      Spacer(modifier = Modifier.weight(0.01f))
      Button(
        onClick = { navController.navigate("aboutLibraries") },
        modifier = Modifier
          .fillMaxWidth()
          .padding(8.dp)
      ) {
        Text(
          text = stringResource(R.string.about_btn),
          style = MaterialTheme.typography.titleMedium,
          modifier = Modifier.padding(8.dp)
        )
      }
    }
  }
}

@Composable
fun Description() {
  Column {
    Text(
      text = stringResource(R.string.main_desc),
      style = MaterialTheme.typography.titleMedium,
      textAlign = TextAlign.Center,
      modifier = Modifier
        .padding(16.dp)
        .fillMaxWidth()
        .wrapContentWidth(Alignment.CenterHorizontally)
    )
  }
}