package com.kssidll.workin.ui.component

import android.content.res.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.tooling.preview.*
import androidx.compose.ui.unit.*
import com.kssidll.workin.data.data.*
import com.kssidll.workin.ui.theme.*

@Composable
fun SelectWorkoutSubpage(
    workouts: List<Workout>,
    onSelect: (Workout) -> Unit,
) {
    Column {
        Spacer(modifier = Modifier.height(12.dp))

        // TODO search

        Spacer(modifier = Modifier.height(12.dp))

        LazyColumn {
            items(workouts) {
                WorkoutCardItem(
                    workout = it,
                    onSelect = {
                        onSelect(it)
                    }
                )
            }
        }

    }
}

@Preview(
    group = "Select Workout Subpage",
    name = "Dark",
    showBackground = true,
    apiLevel = 29,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Preview(
    group = "Select Workout Subpage",
    name = "Light",
    showBackground = true,
    apiLevel = 29,
    uiMode = Configuration.UI_MODE_NIGHT_NO
)
@Composable
fun SelectWorkoutSubpagePreview() {
    WorkINTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            SelectWorkoutSubpage(
                workouts = listOf(
                    Workout(0, "name", "description"),
                    Workout(0, "name", "description"),
                    Workout(0, "name", ""),
                    Workout(0, "name", "description"),
                    Workout(0, "name", ""),
                    Workout(0, "name", "description"),
                ),
                onSelect = {},
            )
        }
    }
}
