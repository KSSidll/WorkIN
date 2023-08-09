package com.kssidll.workin.ui.shared

import android.content.res.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.*
import androidx.compose.ui.res.*
import androidx.compose.ui.tooling.preview.*
import androidx.compose.ui.unit.*
import com.kssidll.workin.R
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
                Row(modifier = Modifier.fillMaxWidth()) {
                    Box(
                        modifier = Modifier
                            .padding(horizontal = 24.dp, vertical = 8.dp)
                            .border(
                                width = OutlinedTextFieldDefaults.UnfocusedBorderThickness,
                                color = MaterialTheme.colorScheme.outline,
                                shape = RoundedCornerShape(24.dp)
                            )
                            .clickable {
                                onSelect(it)
                            }
                    ) {
                        Column {
                            Spacer(modifier = Modifier.height(12.dp))

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Text(
                                    text = it.name,
                                    fontSize = 20.sp
                                )
                            }

                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 24.dp),
                            ) {
                                val description = it.description
                                if (description.isBlank()) {
                                    Text(
                                        text = stringResource(id = R.string.workout_no_description_text),
                                        fontSize = 16.sp,
                                        modifier = Modifier.alpha(0.45F),
                                    )
                                } else {
                                    Text(
                                        text = description,
                                        fontSize = 16.sp,
                                        modifier = Modifier.alpha(0.8F),
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(20.dp))
                        }
                    }
                }
            }
        }

    }
}

/// Subpage Preview ///
@Preview(
    group = "SelectWorkoutSubpage",
    name = "Dark",
    showBackground = true,
    apiLevel = 29,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Preview(
    group = "SelectWorkoutSubpage",
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
