package com.kssidll.workin.ui.shared

import android.content.res.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.*
import androidx.compose.material.icons.automirrored.rounded.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.vector.*
import androidx.compose.ui.res.*
import androidx.compose.ui.tooling.preview.*
import androidx.compose.ui.unit.*
import com.kssidll.workin.R
import com.kssidll.workin.ui.theme.*

/// Header ///
@Composable
fun SecondaryTopHeader(
    onIconClick: () -> Unit,
    icon: ImageVector = Icons.AutoMirrored.Rounded.ArrowBack,
    iconDescription: String = stringResource(id = R.string.go_to_previews_screen_description),
    additionalContent: @Composable BoxScope.() -> Unit = {},
    text: @Composable () -> Unit,
) {
    Box(
        contentAlignment = Alignment.CenterStart,
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surfaceContainer)
            .height(48.dp)
    ) {

        IconButton(
            onClick = onIconClick
        ) {
            Icon(
                icon,
                iconDescription,
                modifier = Modifier.size(30.dp),
                tint = MaterialTheme.colorScheme.onSurface
            )
        }

        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            text()
        }

        additionalContent()
    }
}

/// Header Preview ///
@Preview(
    group = "SecondaryTopHeader",
    name = "Dark",
    showBackground = true,
    apiLevel = 29,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Preview(
    group = "SecondaryTopHeader",
    name = "Light",
    showBackground = true,
    apiLevel = 29,
    uiMode = Configuration.UI_MODE_NIGHT_NO
)
@Composable
fun SecondaryTopHeaderPreview() {
    WorkINTheme {
        Surface(color = MaterialTheme.colorScheme.surface) {
            SecondaryTopHeader(
                onIconClick = {},
            ) {}
        }
    }
}
