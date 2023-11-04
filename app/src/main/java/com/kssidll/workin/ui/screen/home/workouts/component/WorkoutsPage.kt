package com.kssidll.workin.ui.screen.home.workouts.component

import android.content.res.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.res.*
import androidx.compose.ui.tooling.preview.*
import com.kssidll.workin.R
import com.kssidll.workin.data.data.*
import com.kssidll.workin.ui.component.*
import com.kssidll.workin.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WorkoutsPage(
    collectedWorkouts: List<Workout>,
    onWorkoutClick: (Workout) -> Unit,
) {
    Column {
        WorkINTopAppBar(
            title = {
                Text(
                    text = stringResource(R.string.workouts),
                    style = Typography.titleLarge,
                )
            }
        )

        SelectWorkoutSubpage(
            workouts = collectedWorkouts,
            onSelect = onWorkoutClick,
        )
    }
}

@Preview(
    group = "Workouts Page",
    name = "Dark",
    showBackground = true,
    apiLevel = 29,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Preview(
    group = "Workouts Page",
    name = "Light",
    showBackground = true,
    apiLevel = 29,
    uiMode = Configuration.UI_MODE_NIGHT_NO
)
@Composable
fun WorkoutsPagePreview() {
    WorkINTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            WorkoutsPage(
                collectedWorkouts = listOf(
                    Workout("test", "A")
                ),
                onWorkoutClick = {},
            )
        }
    }
}
