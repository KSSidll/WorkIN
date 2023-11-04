package com.kssidll.workin.ui.screen.session

import android.content.res.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.res.*
import androidx.compose.ui.tooling.preview.*
import androidx.compose.ui.unit.*
import com.kssidll.workin.R
import com.kssidll.workin.ui.theme.*

@Composable
fun SessionFinishPage(
    onButtonClick: () -> Unit,
) {
    Column {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Bottom,
            modifier = Modifier
                .weight(1F)
                .fillMaxWidth()
        ) {
            Text(
                text = stringResource(id = R.string.session_finished),
                fontSize = 24.sp
            )

            Spacer(modifier = Modifier.height(16.dp))

            Icon(
                painter = painterResource(id = R.drawable.hype),
                contentDescription = null,
                modifier = Modifier.size(260.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Column(
            modifier = Modifier
                .fillMaxHeight(0.4F)
                .fillMaxWidth()
        ) {
            Button(
                onClick = onButtonClick,
                shape = RoundedCornerShape(23.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(70.dp)
                    .padding(horizontal = 32.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxSize()
                ) {
                    Text(
                        text = stringResource(id = R.string.session_finished_return),
                        fontSize = 25.sp,
                    )
                }
            }
        }
    }
}

@Preview(
    group = "Session Finish",
    name = "Dark",
    showBackground = true,
    apiLevel = 29,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Preview(
    group = "Session Finish",
    name = "Light",
    showBackground = true,
    apiLevel = 29,
    uiMode = Configuration.UI_MODE_NIGHT_NO
)
@Composable
fun SessionFinishPagePreview() {
    WorkINTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            SessionFinishPage(
                onButtonClick = {},
            )
        }
    }
}
