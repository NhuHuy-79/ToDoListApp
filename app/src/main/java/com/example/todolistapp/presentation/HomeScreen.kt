package com.example.todolistapp.presentation

import android.widget.Toast.LENGTH_SHORT
import android.widget.Toast.makeText
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.todolistapp.R
import com.example.todolistapp.data.model.ToDo
import com.example.todolistapp.presentation.components.Dialog
import com.example.todolistapp.presentation.components.EditTextField
import com.example.todolistapp.presentation.components.Menu
import com.example.todolistapp.presentation.components.SearchBar
import com.example.todolistapp.presentation.components.TimerUndo
import com.example.todolistapp.presentation.components.ToDoFAB
import com.example.todolistapp.presentation.events.OnClickEvent
import com.example.todolistapp.presentation.theme.ColorByTheme
import com.example.todolistapp.presentation.theme.fontKanit
import com.example.todolistapp.presentation.utils.Options
import com.example.todolistapp.presentation.viewmodels.HomeViewModel
import com.example.todolistapp.presentation.viewmodels.ToDoViewModel


@Composable
fun HomeScreen(
    toDoViewModel: ToDoViewModel,
    focusManager: FocusManager,
    focusRequester: FocusRequester,
    homeViewModel: HomeViewModel,
    onEvent: (OnClickEvent) -> Unit
) {
    val uiState = homeViewModel.uiStateFlow.collectAsState().value
    val toDoState = toDoViewModel.toDos.collectAsState()
    val toDos = toDoState.value.toDos
    val value by toDoViewModel.searchQuery.collectAsState()
    val currentOption by toDoViewModel.option.collectAsState()
    val context = LocalContext.current

    Scaffold(
        topBar = {
            Text(
                text = "TODO LIST",

                color = ColorByTheme(
                    lightScheme = colorResource(R.color.black),
                    darkScheme = colorResource(R.color.white),
                    darkTheme = uiState.isDarkMode.value
                )(),
                fontSize = 26.sp,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )
        },
        floatingActionButton = {
            ToDoFAB(
                darkTheme = uiState.isDarkMode.value,
                onAddToDo = { homeViewModel.openDialog() },
                onRemove = {
                    onEvent(OnClickEvent.OnRemove)
                    makeText(context, "Delete all ToDos successfully", LENGTH_SHORT ).show()
                           },
                modifier = Modifier,
            )
        },
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        color = ColorByTheme(
                            lightScheme = if (uiState.isDialogOpened.value || uiState.isEdit.value.second) colorResource(
                                R.color.black70
                            ) else colorResource(
                                R.color.white
                            ),
                            darkScheme = colorResource(R.color.black),
                            darkTheme = uiState.isDarkMode.value
                        )()
                    )
                    .padding(innerPadding)
                    .padding(32.dp)

            ) {
                SearchBar(
                    value = value,
                    onValueChange = { newText ->
                        toDoViewModel.updateQuery(newText)
                    },
                    darkTheme = uiState.isDarkMode.value
                )
                Spacer(modifier = Modifier.height(10.dp))
                Row(modifier = Modifier.fillMaxWidth()) {
                    Menu(
                        modifier = Modifier,
                        darkTheme = uiState.isDarkMode.value,
                        options = Options.toList(),
                        currOption = currentOption,
                        onClick = { option ->
                            toDoViewModel.updateOption(option)
                        }
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    DarkLightButton(
                        onDarkMode = { homeViewModel.toggleDarkMode() },
                        darkTheme = uiState.isDarkMode.value
                    )
                }

                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    items(items = toDos, key = { it.id }) { toDo ->
                        ToDoItem(
                            toDo = toDo,
                            onComplete = { onEvent(OnClickEvent.OnComplete(toDo.id)) },
                            onDelete = { onEvent(OnClickEvent.OnDelete(toDo.id)) },
                            darkTheme = uiState.isDarkMode.value,
                            modifier = Modifier.animateItem(
                                fadeInSpec = tween(
                                    durationMillis = 200,
                                    easing = FastOutLinearInEasing
                                ),

                                fadeOutSpec = tween(
                                    durationMillis = 200,
                                    easing = FastOutLinearInEasing
                                )
                            ),
                            openEditTxt = { homeViewModel.openEditTxtField(toDo.id) },
                            updateToDo = { toDoViewModel.updateToDo(toDo.id, toDo.isDone) },
                            value = toDoState.value.content.value,
                            onValueChange = {newText -> toDoState.value.content.value = newText},
                            keyboardActions = KeyboardActions(
                                onDone = {
                                    onEvent(OnClickEvent.OnEdit(
                                        id = toDo.id,
                                        value = toDoState.value.content.value,
                                        isDone = toDo.isDone,
                                    ))
                                    homeViewModel.closeEditTxtField()
                                }
                            ),
                            onCancel = { homeViewModel.closeEditTxtField() },
                            placeholder = toDo.content,
                            focusRequester = focusRequester,
                            focusManager = focusManager,
                            isEditTxtField = homeViewModel.onExitTxtField(toDo.id)
                        )
                        if (toDo != toDos.last()) {
                            HorizontalDivider(
                                color = ColorByTheme(
                                    lightScheme = colorResource(R.color.black),
                                    darkScheme = colorResource(R.color.purple),
                                    darkTheme = uiState.isDarkMode.value
                                )()
                            )
                        }
                    }
                }
            }
            toDos.ifEmpty {
                Image(
                    painter = if (uiState.isDarkMode.value) painterResource(
                        R.drawable.light_bg
                    ) else painterResource(
                        R.drawable.dark_bg
                    ),
                    contentDescription = null,
                    modifier = Modifier.scale(2f)
                )
            }
            Dialog(
                isOpen = uiState.isDialogOpened.value,
                darkTheme = uiState.isDarkMode.value,
                onClose = { homeViewModel.cancelDialog() },
                onValueChange = { newText ->
                    toDoState.value.content.value = newText
                },
                onClick = {
                    onEvent(
                        OnClickEvent.OnApply(
                            value = toDoState.value.content.value
                        )
                    )
                    if(toDoState.value.content.value == ""){
                        makeText(context,"ToDo has empty content", LENGTH_SHORT).show()
                    }
                    homeViewModel.cancelDialog()
                    focusManager.clearFocus()
                },
                textInputState = toDoState.value.content,
                placeholder = "Input your note...",
                btnText = "APPLY",
                title = "NEW TODO",
                focusManager = focusManager,
                focusRequester = focusRequester
            )
            TimerUndo(
                darkTheme = uiState.isDarkMode.value,
                isDisplayed = toDoViewModel.undoState.value,
                onUndo = { onEvent(OnClickEvent.OnUndo) },
                modifier = Modifier.align(Alignment.BottomCenter)
            )
        }
    }
}


@Composable
fun ToDoItem(
    toDo: ToDo,
    focusRequester: FocusRequester,
    focusManager: FocusManager,
    value: String,
    onValueChange: (String) -> Unit,
    keyboardActions: KeyboardActions,
    onCancel: ()-> Unit,
    placeholder:String,
    isEditTxtField: Boolean,
    onComplete: (Boolean) -> Unit,
    onDelete: () -> Unit,
    darkTheme: Boolean,
    openEditTxt: () -> Unit,
    modifier: Modifier,
    updateToDo: () -> Unit,
) {
   AnimatedContent(
        targetState = isEditTxtField,
        transitionSpec = { slideInHorizontally { -it } togetherWith slideOutHorizontally { it } }
    ) { isCrossFaded ->
        if (!isCrossFaded) {
            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .height(50.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = toDo.isDone,
                    onCheckedChange = onComplete,
                    colors = CheckboxDefaults.colors(
                        checkmarkColor = ColorByTheme(
                            color = colorResource(R.color.white)
                        )(),
                        checkedColor = ColorByTheme(
                            lightScheme = colorResource(R.color.black),
                            darkScheme = colorResource(R.color.purple),
                            darkTheme = darkTheme
                        )()
                    )
                )
                Text(
                    text = toDo.content,
                    color = if (toDo.isDone) colorResource(R.color.unselected_color) else ColorByTheme(
                        lightScheme = colorResource(R.color.black),
                        darkScheme = colorResource(R.color.white),
                        darkTheme = darkTheme
                    )(),
                    style = TextStyle(
                        fontFamily = fontKanit,
                        fontSize = 20.sp,
                        textDecoration = if (toDo.isDone) TextDecoration.LineThrough else TextDecoration.None
                    ),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.weight(1f))
                IconButton(
                    onClick = onDelete
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.icon_delete),
                        tint = ColorByTheme(
                            lightScheme = colorResource(R.color.black),
                            darkScheme = colorResource(R.color.selected_delete_btn),
                            darkTheme = darkTheme
                        )(),
                        contentDescription = "Delete",
                        modifier = Modifier.size(24.dp)
                    )
                }
                IconButton(
                    onClick = {
                        openEditTxt()
                        updateToDo()
                    }
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.icon_edit),
                        tint = ColorByTheme(
                            lightScheme = colorResource(R.color.black),
                            darkScheme = colorResource(R.color.selected_delete_btn),
                            darkTheme = darkTheme
                        )(),
                        contentDescription = "Delete",
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        }

        else {
            EditTextField(
                placeholder = placeholder,
                value = value,
                focusRequester = focusRequester,
                darkTheme = darkTheme,
                onValueChange = onValueChange,
                keyboardActions = keyboardActions,
                onCancel = {  focusManager.clearFocus(true)
                    onCancel()
                           },
                modifier = Modifier.focusRequester(focusRequester)
            )
        }
    }
}


@Composable
fun DarkLightButton(
    onDarkMode: () -> Unit,
    darkTheme: Boolean,
) {
    Box(
        modifier = Modifier.size(40.dp),
        contentAlignment = Alignment.Center
    ) {
        Button(
            onClick = onDarkMode,
            modifier = Modifier.size(40.dp),
            shape = RoundedCornerShape(6.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = ColorByTheme(
                    lightScheme = colorResource(R.color.black),
                    darkScheme = colorResource(R.color.purple),
                    darkTheme = darkTheme
                )(),
                contentColor = colorResource(R.color.selected_add_btn)
            ),
        ) {}
        AnimatedContent(
            targetState = darkTheme,
            transitionSpec = {
                slideInVertically { -it } togetherWith slideOutVertically { it }
            }
        ) { darkTheme ->
            if (darkTheme) {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.icon_moon),
                    contentDescription = "Dark mode",
                    modifier = Modifier.size(24.dp),
                    tint = colorResource(R.color.white)
                )
            } else {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.icon_light),
                    contentDescription = "Dark mode",
                    modifier = Modifier.size(24.dp),
                    tint = colorResource(R.color.white)
                )
            }
        }
    }
}