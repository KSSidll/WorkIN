package com.kssidll.workin.ui.component

import android.content.res.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.tooling.preview.*
import com.kssidll.workin.ui.theme.*


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WorkINTopAppBar(
    modifier: Modifier = Modifier,
    title: @Composable () -> Unit = {},
    navigationIcon: NavigationIcon = navigationIcon(),
    actions: @Composable RowScope.() -> Unit = {},
    windowInsets: WindowInsets = TopAppBarDefaults.windowInsets,
    scrollBehavior: TopAppBarScrollBehavior? = null,
) {
    CenterAlignedTopAppBar(
        title = title,
        modifier = modifier,
        navigationIcon = navigationIcon.content(),
        actions = actions,
        windowInsets = windowInsets,
        scrollBehavior = scrollBehavior,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(
    group = "WorkIN Top App Bar",
    name = "Dark",
    showBackground = true,
    apiLevel = 29,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Preview(
    group = "WorkIN Top App Bar",
    name = "Light",
    showBackground = true,
    apiLevel = 29,
    uiMode = Configuration.UI_MODE_NIGHT_NO
)
@Composable
fun WorkINTopAppBarPreview() {
    Surface {
        WorkINTopAppBar(
            title = {
                Text(
                    text = "test",
                    style = Typography.titleLarge,
                )
            }
        )
    }
}
