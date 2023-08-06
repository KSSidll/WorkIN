package com.kssidll.workin.ui.dashboard

import android.content.res.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.tooling.preview.*
import com.kssidll.workin.*
import com.kssidll.workin.mock.*
import com.kssidll.workin.ui.shared.*
import com.kssidll.workin.ui.theme.*

/// Route ///
@Composable
fun DashboardRoute(

) {
    // val dashboardViewModel: DashboardViewModel = hiltViewModel()

    ScreenWithBottomNavBar {
        DashboardScreen()
    }
}


/// Screen ///
@Composable
fun DashboardScreen(

) {

}


/// Screen Preview ///
@Preview(
    group = "DashboardScreen",
    name = "Dashboard Screen Dark",
    showBackground = true,
    apiLevel = 29,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Preview(
    group = "DashboardScreen",
    name = "Dashboard Screen Light",
    showBackground = true,
    apiLevel = 29,
    uiMode = Configuration.UI_MODE_NIGHT_NO
)
@Composable
fun DashboardScreenPreview() {
    WorkINTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.surface,
        ) {
            ScreenWithBottomNavBar(
                navigationController = NavigationControllerMock(NavigationDestinations.DASHBOARD_ROUTE)
            ) {
                DashboardScreen(

                )
            }
        }
    }
}