package com.kssidll.workin.ui.shared

import android.content.res.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.*
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.res.*
import androidx.compose.ui.tooling.preview.*
import androidx.compose.ui.unit.*
import com.kssidll.workin.R
import com.kssidll.workin.ui.theme.*

@Composable
fun SecondaryTopHeader(
    onBack: () -> Unit,
    content: @Composable () -> Unit
) {
    Box(
        contentAlignment = Alignment.CenterStart,
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.primary)
    ) {

        IconButton(
            onClick = onBack
        ) {
            Icon(
                Icons.Rounded.ArrowBack,
                contentDescription = stringResource(id = R.string.go_to_previews_screen_description),
                modifier = Modifier.size(30.dp),
                tint = MaterialTheme.colorScheme.onPrimary
            )
        }

        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            content()
        }
    }
}

/// Screen Preview ///
@Preview(
    group = "SecondaryTopHeader",
    name = "Secondary Top Header Dark",
    showBackground = true,
    apiLevel = 29,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Preview(
    group = "SecondaryTopHeader",
    name = "Secondary Top Header Light",
    showBackground = true,
    apiLevel = 29,
    uiMode = Configuration.UI_MODE_NIGHT_NO
)
@Composable
fun SecondaryTopHeaderPreview() {
    WorkINTheme {
        Surface(
            color = MaterialTheme.colorScheme.background
        ) {
            SecondaryTopHeader(
                onBack = {}
            ) {}
        }
    }
}
