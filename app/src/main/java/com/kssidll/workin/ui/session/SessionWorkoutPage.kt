package com.kssidll.workin.ui.session

import android.content.res.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.tooling.preview.*
import com.kssidll.workin.ui.theme.*

/// Page ///
@Composable
fun SessionWorkoutPage(

) {

}


/// Page Preview ///
@Preview(
    group = "SessionWorkoutPage",
    name = "Dark",
    showBackground = true,
    apiLevel = 29,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Preview(
    group = "SessionWorkoutPage",
    name = "Light",
    showBackground = true,
    apiLevel = 29,
    uiMode = Configuration.UI_MODE_NIGHT_NO
)
@Composable
fun SessionWorkoutPagePreview() {
    WorkINTheme {
        Surface(modifier = Modifier.fillMaxSize()) {

        }
    }
}