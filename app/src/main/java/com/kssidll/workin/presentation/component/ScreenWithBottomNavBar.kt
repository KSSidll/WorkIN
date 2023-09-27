package com.kssidll.workin.presentation.component

import android.content.res.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.*
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.res.*
import androidx.compose.ui.tooling.preview.*
import androidx.compose.ui.unit.*
import com.kssidll.workin.*
import com.kssidll.workin.R
import com.kssidll.workin.presentation.theme.*
import dev.olshevski.navigation.reimagined.*

/// NavBar ///
@Composable
fun ScreenWithBottomNavBar(
    navigationController: NavController<Screen> = LocalNavigation.current,
    content: @Composable () -> Unit,
) {
    Column {

        // Content //
        Box(
            modifier = Modifier.weight(1F, true)
        ) {
            content()
        }

        // Bottom Nav Bar //
        val iconBottomPadding: Dp = 5.dp

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            IconButton(
                enabled = navigationController.backstack.entries.last().destination != Screen.Dashboard,
                modifier = Modifier
                    .weight(1F, true)
                    .fillMaxHeight(),
                onClick = {
                    navigationController.navigate(Screen.Dashboard)
                },
                colors = IconButtonColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    disabledContainerColor = MaterialTheme.colorScheme.surface,
                    contentColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4F),
                    disabledContentColor = MaterialTheme.colorScheme.onSurface
                )
            ) {
                Icon(
                    imageVector = Icons.Default.Home,
                    contentDescription = stringResource(
                        id = R.string
                            .navigate_to_dashboard_description
                    ),
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(bottom = iconBottomPadding)
                )
            }

            VerticalDivider()

            IconButton(
                enabled = navigationController.backstack.entries.last().destination != Screen.Workouts,
                modifier = Modifier
                    .weight(1F, true)
                    .fillMaxHeight(),
                onClick = {
                    navigationController.popUpTo { it == Screen.Dashboard }
                    navigationController.navigate(Screen.Workouts)
                },
                colors = IconButtonColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    disabledContainerColor = MaterialTheme.colorScheme.surface,
                    contentColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4F),
                    disabledContentColor = MaterialTheme.colorScheme.onSurface
                )
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.exercise),
                    contentDescription = stringResource(
                        id = R.string
                            .navigate_to_dashboard_description
                    ),
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(bottom = iconBottomPadding)
                )
            }
        }

    }
}

/// NavBar Preview ///
@Preview(
    group = "ScreenWithBottomNavBar",
    name = "Dark",
    showBackground = true,
    apiLevel = 29,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Preview(
    group = "ScreenWithBottomNavBar",
    name = "Light",
    showBackground = true,
    apiLevel = 29,
    uiMode = Configuration.UI_MODE_NIGHT_NO
)
@Composable
fun ScreenWithBottomNavBarPreview() {
    WorkINTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            ScreenWithBottomNavBar(
                navigationController = rememberNavController(startDestination = Screen.Dashboard),
                content = {}
            )
        }
    }
}
