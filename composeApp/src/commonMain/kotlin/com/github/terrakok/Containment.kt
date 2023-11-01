package com.github.terrakok

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import kotlinx.coroutines.launch

private val modalBottomSheetInfoUrl =
    "https://developer.android.com/reference/kotlin/androidx/compose/material3/package-summary?hl=en#ModalBottomSheet(kotlin.Function0,androidx.compose.ui.Modifier,androidx.compose.material3.SheetState,androidx.compose.ui.unit.Dp,androidx.compose.ui.graphics.Shape,androidx.compose.ui.graphics.Color,androidx.compose.ui.graphics.Color,androidx.compose.ui.unit.Dp,androidx.compose.ui.graphics.Color,kotlin.Function0,androidx.compose.foundation.layout.WindowInsets,androidx.compose.ui.window.SecureFlagPolicy,kotlin.Function1)"

private val cardsInfoUrl =
    "https://developer.android.com/reference/kotlin/androidx/compose/material3/package-summary#Card(androidx.compose.ui.Modifier,androidx.compose.ui.graphics.Shape,androidx.compose.material3.CardColors,androidx.compose.material3.CardElevation,androidx.compose.foundation.BorderStroke,kotlin.Function1)"

private val dialogsInfoUrl =
    "https://developer.android.com/reference/kotlin/androidx/compose/material3/package-summary#AlertDialog(kotlin.Function0,kotlin.Function0,androidx.compose.ui.Modifier,kotlin.Function0,kotlin.Function0,kotlin.Function0,kotlin.Function0,androidx.compose.ui.graphics.Shape,androidx.compose.ui.graphics.Color,androidx.compose.ui.graphics.Color,androidx.compose.ui.graphics.Color,androidx.compose.ui.graphics.Color,androidx.compose.ui.unit.Dp,androidx.compose.ui.window.DialogProperties)"

@Composable
fun Containment() {
    ParentSection("Containment") {
        ChildSection(
            title = "Bottom sheet",
            infoUrl = modalBottomSheetInfoUrl
        ) {
            BottomSheetDemo()
        }

        ChildSection(
            title = "Cards",
            infoUrl = cardsInfoUrl
        ) {
            CardsDemo()
        }

        ChildSection(
            title = "Dialog",
            infoUrl = dialogsInfoUrl
        ) {
            DialogsDemo()
        }
    }
}

@Composable
private fun DialogsDemo() {
    val openAlertDialog = remember { mutableStateOf(false) }
    val openFullScreenDialog = remember { mutableStateOf(false) }

    OutlinedCard {
        Row(
            modifier = Modifier
                .requiredWidthIn(400.dp)
                .width(600.dp)
                .padding(16.dp)
        ) {
            TextButton(
                modifier = Modifier
                    .padding(horizontal = 8.dp, vertical = 4.dp)
                    .weight(1f),
                enabled = true,
                onClick = { openAlertDialog.value = true },
                content = { Text("Show dialog") }
            )

            TextButton(
                modifier = Modifier
                    .padding(horizontal = 8.dp, vertical = 4.dp)
                    .weight(1f),
                enabled = true,
                onClick = { openFullScreenDialog.value = true },
                content = { Text("Show full-screen dialog") }
            )
        }
    }


    if (openAlertDialog.value) {
        AlertDialog(
            title = {
                Text(text = "What is a dialog?")
            },
            text = {
                Text(text = "A dialog is a type of modal window that appears in front of app content to provide critical information, or prompt for a decision to be made.")
            },
            onDismissRequest = { openAlertDialog.value = false },
            confirmButton = {
                TextButton(
                    onClick = { openAlertDialog.value = false }
                ) {
                    Text("Okay")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { openAlertDialog.value = false }
                ) {
                    Text("Dismiss")
                }
            }
        )
    }

    if (openFullScreenDialog.value) {
        FullScreenDialog {
            openFullScreenDialog.value = false
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
private val DefaultDialogProperties = DialogProperties(
    dismissOnBackPress = true,
    dismissOnClickOutside = true,
    usePlatformDefaultWidth = false,
    usePlatformInsets = false
)

@Composable
private fun FullScreenDialog(onDismissRequest: () -> Unit) {
    Dialog(onDismissRequest = { onDismissRequest() }, properties = DefaultDialogProperties) {
        Box(Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .clickable { onDismissRequest() }
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    IconButton(onClick = onDismissRequest) {
                        Icon(imageVector = Icons.Default.Close, contentDescription = null)
                    }

                    Text(
                        text = "Full-screen dialog",
                        style = MaterialTheme.typography.headlineSmall,
                    )
                }

                TextButton(onClick = { onDismissRequest() }, modifier = Modifier) {
                    Text("Close")
                }
            }
        }
    }
}

@Composable
private fun CardsDemo() {

    @Composable
    fun CardTemplate(
        title: String,
        elevation: CardElevation,
        colors: CardColors,
        border: BorderStroke? = null
    ) {
        Card(
            modifier = Modifier.size(width = 115.dp, height = 100.dp),
            elevation = elevation,
            colors = colors,
            border = border
        ) {
            Box(
                modifier = Modifier.fillMaxSize().padding(10.dp, 5.dp, 5.dp, 10.dp)
            ) {
                Box(
                    modifier = Modifier.matchParentSize(),
                    contentAlignment = Alignment.BottomStart
                ) {
                    Text(title, fontSize = 14.sp)
                }

                Box(
                    modifier = Modifier.matchParentSize(),
                    contentAlignment = Alignment.TopEnd
                ) {
                    IconButton(onClick = {}) {
                        Icon(Icons.Default.MoreVert, contentDescription = null)
                    }
                }
            }
        }
    }
    OutlinedCard {
        Row(
            modifier = Modifier
                .requiredWidthIn(400.dp)
                .width(600.dp)
                .padding(16.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            CardTemplate(
                title = "Elevated",
                elevation = CardDefaults.elevatedCardElevation(defaultElevation = 3.dp),
                colors = CardDefaults.elevatedCardColors()
            )
            Spacer(modifier = Modifier.width(16.dp))
            CardTemplate(
                title = "Filled",
                elevation = CardDefaults.cardElevation(),
                colors = CardDefaults.cardColors(),
            )
            Spacer(modifier = Modifier.width(16.dp))
            CardTemplate(
                title = "Outlined",
                elevation = CardDefaults.outlinedCardElevation(),
                colors = CardDefaults.outlinedCardColors(),
                border = BorderStroke(1.dp, Color.DarkGray)
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun BottomSheetDemo() {
    var isModalSheetOpen by rememberSaveable { mutableStateOf(false) }

    OutlinedCard {
        Row(
            modifier = Modifier
                .requiredWidthIn(400.dp)
                .width(600.dp)
                .padding(16.dp)
        ) {
            TextButton(
                modifier = Modifier
                    .padding(horizontal = 8.dp, vertical = 4.dp)
                    .weight(1f),
                enabled = true,
                onClick = {
                    isModalSheetOpen = true
                },
                content = { Text("Show modal bottom sheet") }
            )

            var bottomSheetShown by remember { mutableStateOf(false) }
            val scope = rememberCoroutineScope()
            val bottomSheetScaffoldState = LocalBottomSheetScaffoldState.current
            TextButton(
                modifier = Modifier
                    .padding(horizontal = 8.dp, vertical = 4.dp)
                    .weight(1f),
                enabled = true,
                onClick = {
                    bottomSheetShown = !bottomSheetShown
                    scope.launch {
                        if (bottomSheetShown) {
                            bottomSheetScaffoldState.bottomSheetState.expand()
                        } else {
                            bottomSheetScaffoldState.bottomSheetState.partialExpand()
                        }
                    }
                },
                content = {
                    Text("${if(bottomSheetShown) "Hide" else "Show"} bottom sheet")
                }
            )
        }
    }

    val bottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    if (isModalSheetOpen) {
        ModalBottomSheet(
            onDismissRequest = { isModalSheetOpen = false },
            sheetState = bottomSheetState,
        ) {
            BottomSheetContent()
        }
    }
}

@Composable
internal fun ColumnScope.BottomSheetContent() {
    Row(modifier = Modifier.padding(vertical = 20.dp).align(Alignment.CenterHorizontally)) {
        BottomSheetButton(
            title = "Share",
            icon = Icons.Outlined.Share
        )
        BottomSheetButton(
            title = "Add to",
            icon = Icons.Outlined.Add
        )
        BottomSheetButton(
            title = "Trash",
            icon = Icons.Outlined.Delete
        )
        BottomSheetButton(
            title = "Archive",
            icon = Icons.Outlined.Archive
        )
        BottomSheetButton(
            title = "Settings",
            icon = Icons.Outlined.Settings
        )
        BottomSheetButton(
            title = "Favorite",
            icon = Icons.Outlined.Favorite
        )
    }
}

@Composable
private fun BottomSheetButton(
    icon: ImageVector,
    title: String
)  {
    NavigationRailItem(
        selected = false,
        onClick = { },
        icon = {
            Icon(
                imageVector = icon,
                contentDescription = null
            )
        },
        label = { Text(title) }
    )
}