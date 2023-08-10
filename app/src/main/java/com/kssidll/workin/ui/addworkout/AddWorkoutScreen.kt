package com.kssidll.workin.ui.addworkout

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
import androidx.hilt.navigation.compose.*
import com.kssidll.workin.R
import com.kssidll.workin.ui.shared.*
import com.kssidll.workin.ui.theme.*

/// Route ///
@Composable
fun AddWorkoutRoute(
    onBack: () -> Unit
) {
    val addWorkoutViewModel: AddWorkoutViewModel = hiltViewModel()

    AddWorkoutScreen(
        onBack = onBack,
        onWorkoutAdd = {
            addWorkoutViewModel.addWorkout(it)
            onBack()
        },
    )
}


/// Screen ///
@Composable
fun AddWorkoutScreen(
    onBack: () -> Unit,
    onWorkoutAdd: suspend (AddWorkoutData) -> Unit
) {
    EditWorkoutDataSubpage(
        onBack = onBack,
        submitButtonContent = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = stringResource(
                        R.string
                            .confirm_add_workout_description
                    ),
                    modifier = Modifier.size(30.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = stringResource(R.string.confirm_add_workout_text),
                    fontSize = 20.sp
                )
            }
        },
        onSubmit = {
            onWorkoutAdd(
                AddWorkoutData(
                    name = it.name,
                    description = it.description,
                )
            )
        }
    )
}


/// Screen Preview ///
@Preview(
    group = "AddWorkoutScreen",
    name = "Dark",
    showBackground = true,
    apiLevel = 29,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Preview(
    group = "AddWorkoutScreen",
    name = "Light",
    showBackground = true,
    apiLevel = 29,
    uiMode = Configuration.UI_MODE_NIGHT_NO
)
@Composable
fun AddWorkoutScreenPreview() {
    WorkINTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            AddWorkoutScreen(
                onBack = {},
                onWorkoutAdd = {},
            )
        }
    }
}

