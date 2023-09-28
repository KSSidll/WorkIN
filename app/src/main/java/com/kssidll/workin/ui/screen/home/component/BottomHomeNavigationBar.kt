package com.kssidll.workin.ui.screen.home.component

import android.content.res.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.painter.*
import androidx.compose.ui.graphics.vector.*
import androidx.compose.ui.tooling.preview.*
import androidx.compose.ui.unit.*
import com.kssidll.workin.ui.screen.home.*
import com.kssidll.workin.ui.theme.*

@Composable
@ReadOnlyComposable
fun navigationBarItemColors(
    selectedIconColor: Color = MaterialTheme.colorScheme.onSecondaryContainer,
    selectedTextColor: Color = MaterialTheme.colorScheme.onSurface,
    selectedIndicatorColor: Color = MaterialTheme.colorScheme.secondaryContainer,
    unselectedIconColor: Color = MaterialTheme.colorScheme.onSecondaryContainer.copy(0.7F),
    unselectedTextColor: Color = MaterialTheme.colorScheme.onSecondaryContainer.copy(0.7F),
    disabledIconColor: Color = MaterialTheme.colorScheme.onSecondaryContainer.copy(0.1F),
    disabledTextColor: Color = MaterialTheme.colorScheme.onSecondaryContainer.copy(0.1F),
) = NavigationBarItemColors(
    selectedIconColor = selectedIconColor,
    selectedTextColor = selectedTextColor,
    selectedIndicatorColor = selectedIndicatorColor,
    unselectedIconColor = unselectedIconColor,
    unselectedTextColor = unselectedTextColor,
    disabledIconColor = disabledIconColor,
    disabledTextColor = disabledTextColor,
)

private val topPadding = 10.dp
private val bottomPadding = 8.dp

@Composable
fun BottomHomeNavigationBar(
    currentLocation: HomeScreenLocations,
    onLocationChange: (HomeScreenLocations) -> Unit,
) {
    NavigationBar(
        modifier = Modifier.height(65.dp),
        containerColor = MaterialTheme.colorScheme.surfaceContainer,
    ) {
        HomeScreenLocations.entries.forEach {
            if (it.imageVector != null) {
                BottomHomeNavigationBarItem(
                    selected = currentLocation == it,
                    onClick = {
                        onLocationChange(it)
                    },
                    imageVector = it.imageVector!!,
                    description = it.description,
                )
            } else if (it.painter != null) {
                BottomHomeNavigationBarItem(
                    selected = currentLocation == it,
                    onClick = {
                        onLocationChange(it)
                    },
                    painter = it.painter!!,
                    description = it.description,
                )
            } else {
                error("A HomeScreenLocations entry needs to have either ImageVector or Painter data set to something other than null")
            }
        }
    }
}

@Composable
private fun RowScope.BottomHomeNavigationBarItem(
    modifier: Modifier = Modifier,
    selected: Boolean,
    onClick: () -> Unit,
    imageVector: ImageVector,
    description: String,
) {
    NavigationBarItem(
        selected = selected,
        modifier = modifier,
        onClick = onClick,
        icon = {
            Icon(
                imageVector = imageVector,
                contentDescription = description,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = topPadding, bottom = bottomPadding)
            )
        },
        colors = navigationBarItemColors(),
    )
}

@Composable
private fun RowScope.BottomHomeNavigationBarItem(
    modifier: Modifier = Modifier,
    selected: Boolean,
    onClick: () -> Unit,
    painter: Painter,
    description: String,
) {
    NavigationBarItem(
        selected = selected,
        modifier = modifier,
        onClick = onClick,
        icon = {
            Icon(
                painter = painter,
                contentDescription = description,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = topPadding, bottom = bottomPadding)
            )
        },
        colors = navigationBarItemColors(),
    )
}

@Preview(
    group = "Bottom Home Navigation Bar",
    name = "Dark",
    showBackground = true,
    apiLevel = 29,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Preview(
    group = "Bottom Home Navigation Bar",
    name = "Light",
    showBackground = true,
    apiLevel = 29,
    uiMode = Configuration.UI_MODE_NIGHT_NO
)
@Composable
fun BottomHomeNavigationBarPreview() {
    WorkINTheme {
        Surface {
            BottomHomeNavigationBar(
                currentLocation = HomeScreenLocations.Dashboard,
                onLocationChange = {},
            )
        }
    }
}
