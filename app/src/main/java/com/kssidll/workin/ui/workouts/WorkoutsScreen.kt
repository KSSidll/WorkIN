package com.kssidll.workin.ui.workouts

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
fun WorkoutsRoute(

) {
    // val WorkoutsViewModel: WorkoutsViewModel = hiltViewModel()

    ScreenWithBottomNavBar {
        WorkoutsScreen()
    }
}


/// Screen ///
@Composable
fun WorkoutsScreen(

) {

}


/// Screen Preview ///
@Preview(
    group = "WorkoutsScreen",
    name = "Workouts Screen Dark",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Preview(
    group = "WorkoutsScreen",
    name = "Workouts Screen Light",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_NO
)
@Composable
fun WorkoutsScreenPreview() {
    WorkINTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background,
        ) {
            ScreenWithBottomNavBar(
                navigationController = NavigationControllerMock(NavigationDestinations.WORKOUTS_ROUTE)
            ) {
                WorkoutsScreen(

                )
            }
        }
    }
}

