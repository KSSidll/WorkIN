package com.kssidll.workin.presentation.screen.dashboard.component

import android.content.res.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.*
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.painter.*
import androidx.compose.ui.graphics.vector.*
import androidx.compose.ui.res.*
import androidx.compose.ui.tooling.preview.*
import androidx.compose.ui.unit.*
import com.kssidll.workin.*
import com.kssidll.workin.R
import com.kssidll.workin.presentation.theme.*
import dev.olshevski.navigation.reimagined.*

private val containerColor @Composable get() = MaterialTheme.colorScheme.surfaceContainer
private val navigationBarItemColors
    @Composable get() = NavigationBarItemColors(
        selectedIconColor = MaterialTheme.colorScheme.onSecondaryContainer,
        selectedTextColor = MaterialTheme.colorScheme.onSurface,
        selectedIndicatorColor = MaterialTheme.colorScheme.secondaryContainer,
        unselectedIconColor = MaterialTheme.colorScheme.onSecondaryContainer.copy(0.7F),
        unselectedTextColor = MaterialTheme.colorScheme.onSecondaryContainer.copy(0.7F),
        disabledIconColor = MaterialTheme.colorScheme.onSecondaryContainer.copy(0.1F),
        disabledTextColor = MaterialTheme.colorScheme.onSecondaryContainer.copy(0.1F),
    )

private val topPadding = 10.dp
private val bottomPadding = 8.dp

@Composable
fun BottomDashboardNavigationBar(
    navigationController: NavController<Screen> = LocalNavigation.current
) {
    NavigationBar(
        modifier = Modifier.height(65.dp),
        containerColor = containerColor,
    ) {
        BottomDashboardNavigationBarItem(
            selected = navigationController.backstack.entries.last().destination == Screen.Dashboard,
            onClick = {
                navigationController.popUpTo { it == Screen.Dashboard }
            },
            imageVector = Icons.Rounded.Home,
            description = stringResource(id = R.string.navigate_to_dashboard_description),
        )

        BottomDashboardNavigationBarItem(
            selected = navigationController.backstack.entries.last().destination == Screen.Workouts,
            onClick = {
                navigationController.popUpTo { it == Screen.Dashboard }
                navigationController.navigate(Screen.Workouts)
            },
            painter = painterResource(R.drawable.exercise),
            description = stringResource(id = R.string.navigate_to_dashboard_description),
        )
    }
}

@Composable
private fun RowScope.BottomDashboardNavigationBarItem(
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
        colors = navigationBarItemColors,
    )
}

@Composable
private fun RowScope.BottomDashboardNavigationBarItem(
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
        colors = navigationBarItemColors,
    )
}

@Preview(
    group = "Bottom Dashboard Navigation Bar",
    name = "Dark",
    showBackground = true,
    apiLevel = 29,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Preview(
    group = "Bottom Dashboard Navigation Bar",
    name = "Light",
    showBackground = true,
    apiLevel = 29,
    uiMode = Configuration.UI_MODE_NIGHT_NO
)
@Composable
fun BottomBashboardNavigationBarPreview() {
    WorkINTheme {
        Surface {
            CompositionLocalProvider(LocalNavigation provides rememberNavController(Screen.Dashboard)) {
                BottomDashboardNavigationBar()
            }
        }
    }
}
