package com.kssidll.workin.ui.workouts

import android.content.res.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.*
import androidx.compose.material.icons.*
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.tooling.preview.*
import androidx.compose.ui.unit.*
import androidx.hilt.navigation.compose.*
import com.kssidll.workin.*
import com.kssidll.workin.data.data.*
import com.kssidll.workin.mock.*
import com.kssidll.workin.ui.shared.*
import com.kssidll.workin.ui.theme.*
import kotlinx.coroutines.flow.*

/// Route ///
@Composable
fun WorkoutsRoute(
    onAddWorkout: () -> Unit
) {
    val workoutsViewModel: WorkoutsViewModel = hiltViewModel()

    ScreenWithBottomNavBar {
        WorkoutsScreen(
            workouts = workoutsViewModel.getAllDescFlow(),
            onAddWorkout = onAddWorkout
        )
    }
}


/// Screen ///
@Composable
fun WorkoutsScreen(
    workouts: Flow<List<Workout>>,
    onAddWorkout: () -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {

        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            val workoutItems = workouts.collectAsState(initial = emptyList()).value
            LazyColumn {
                items(workoutItems) {
                    Row {
                        Text(text = it.name)
                    }
                }
            }
        }

        Box(
            modifier = Modifier
                .padding(8.dp)
                .align(Alignment.BottomEnd)
        ) {
            FilledIconButton(
                modifier = Modifier
                    .size(64.dp)
                    .shadow(6.dp, CircleShape),
                onClick = onAddWorkout,
                colors = IconButtonColors(
                    disabledContainerColor = Color.Transparent,
                    disabledContentColor = Color.Transparent,
                    containerColor = MaterialTheme.colorScheme.secondary,
                    contentColor = MaterialTheme.colorScheme.onSecondary
                )
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add new item",
                    modifier = Modifier.fillMaxSize()
                )
            }

        }
    }
}


/// Screen Preview ///
@Preview(
    group = "WorkoutsScreen",
    name = "Workouts Screen Dark",
    showBackground = true,
    apiLevel = 29,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Preview(
    group = "WorkoutsScreen",
    name = "Workouts Screen Light",
    showBackground = true,
    apiLevel = 29,
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
                    workouts = flowOf(),
                    onAddWorkout = {}
                )
            }
        }
    }
}

