package eu.amennillo.dataleak.ui


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.graphics.drawable.toBitmap
import eu.amennillo.dataleak.R
import eu.amennillo.dataleak.data.AppInfo
import eu.amennillo.dataleak.util.filterAppListByName

@Composable
fun FilteredList(apps: List<AppInfo>, modifier: Modifier) {
  var searchQuery by remember { mutableStateOf("") }
  val filteredApps = filterAppListByName(apps, searchQuery)

  Column(modifier = modifier) {
    SearchBar(searchQuery) { query -> searchQuery = query }
    LeakedPackagesList(apps = filteredApps)
  }
}

@Composable
private fun SearchBar(query: String, onQueryChange: (String) -> Unit) {
  BasicTextField(
    value = query,
    onValueChange = onQueryChange,
    modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp)
        .border(2.dp, MaterialTheme.colorScheme.onSurface, MaterialTheme.shapes.small),
    singleLine = true,
    cursorBrush = SolidColor(MaterialTheme.colorScheme.onSurface),
    textStyle = MaterialTheme.typography.bodyLarge.copy(color = MaterialTheme.colorScheme.onSurface),
    decorationBox = { innerTextField ->
      Box(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.surface, MaterialTheme.shapes.small)
            .padding(horizontal = 16.dp, vertical = 12.dp)
      ) {
        if (query.isEmpty()) {
          Text(
            "Search...",
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.alpha(0.5f)
          )
        }
        innerTextField()
      }
    }
  )
}

@Composable
private fun LeakedPackagesList(apps: List<AppInfo>, modifier: Modifier = Modifier) {
  LazyColumn(modifier = modifier) {
    items(apps) { app ->
      LeakedPackageItem(app)
    }
  }
}

@Composable
private fun LeakedPackageItem(app: AppInfo, modifier: Modifier = Modifier) {
  val context = LocalContext.current

  val alpha: Float
  val appName: String
  if (app.isDisabled) {
    alpha = 0.5f
    appName = stringResource(R.string.app_disabled, app.appName)
  } else {
    alpha = 1f
    appName = app.appName
  }

  Row(modifier = modifier.padding(8.dp)) {
    Image(
      bitmap = app.appIcon.toBitmap().asImageBitmap(),
      contentDescription = null,
      modifier = Modifier
          .size(40.dp)
          .alpha(alpha)
    )
    Spacer(modifier = Modifier.width(8.dp))
    Column(
      modifier = Modifier
          .weight(1f)
          .alpha(alpha)
    ) {
      Text(text = appName, style = MaterialTheme.typography.titleMedium)
      Text(text = app.packageName, style = MaterialTheme.typography.titleSmall)
    }
    IconButton(onClick = { app.openInfo(context) }) {
      Icon(
        imageVector = Icons.Default.Info,
        contentDescription = stringResource(R.string.app_info_btn)
      )
    }
  }
}