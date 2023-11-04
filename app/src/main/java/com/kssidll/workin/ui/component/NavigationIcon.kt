package com.kssidll.workin.ui.component

import androidx.compose.material.icons.*
import androidx.compose.material.icons.automirrored.filled.*
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*

@Composable
fun navigationIcon(
    type: NavigationIcon.Types = NavigationIcon.Types.None,
    contentDescription: String? = null,
    onClick: () -> Unit = {},
): NavigationIcon {
    return remember {
        NavigationIcon(
            type = type,
            contentDescription = contentDescription,
            onClick = onClick,
        )
    }
}

class NavigationIcon(
    private val type: Types,
    private val contentDescription: String?,
    private val onClick: () -> Unit,
) {
    enum class Types {
        None,
        Back,
        Close,
    }

    @Composable
    fun content(): @Composable () -> Unit {
        return type.content(
            contentDescription = contentDescription,
            onClick = onClick
        )
    }
}

@Composable
fun NavigationIcon.Types.content(
    contentDescription: String? = null,
    onClick: () -> Unit = {},
): @Composable () -> Unit {
    when (this) {
        NavigationIcon.Types.None -> {
            return {}
        }

        NavigationIcon.Types.Back -> {
            return {
                IconButton(
                    onClick = onClick,
                    modifier = Modifier
                        .minimumInteractiveComponentSize()
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = contentDescription,
                    )
                }
            }
        }

        NavigationIcon.Types.Close -> {
            return {
                IconButton(
                    onClick = onClick,
                    modifier = Modifier
                        .minimumInteractiveComponentSize()
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = contentDescription,
                    )
                }
            }
        }
    }
}