package com.skytech.pomodoro.view


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.skytech.pomodoro.PreferencesManager
import com.skytech.pomodoro.R
import com.skytech.pomodoro.view.components.ChangeValueButton
import com.skytech.pomodoro.view_model.HomeViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    preferencesManager: PreferencesManager,
    closeButton: () -> Unit,
    viewModel: HomeViewModel
) {


    var focusTime = remember {
        mutableStateOf(HomeViewModel.PomodoroState.FOCUS.durationInMinutes / 60)
    }
    val shortTime = remember {
        mutableStateOf(HomeViewModel.PomodoroState.SHORT_BREAK.durationInMinutes / 60)
    }
    val longTime = remember {
        mutableStateOf(HomeViewModel.PomodoroState.LONG_BREAK.durationInMinutes / 60)
    }

    LaunchedEffect(key1 = true) {
        viewModel.loadSoundSetting(preferencesManager)

    }





    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(16.dp))  // Köşeleri yuvarlatır, istersen dp değerini değiştir
            .background(color = Color.White)
    ) {

        Column {
            ListItem(
                headlineText = { Text(text = "Settings", fontWeight = FontWeight.Bold) },
                trailingContent = {
                    IconButton(
                        onClick = closeButton
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.close_icon),
                            contentDescription = ""
                        )

                    }
                })

            ListItem(headlineText = { Text(text = "Pomodoro Lenght") }, trailingContent = {
                ChangeValueButton(
                    HomeViewModel.PomodoroState.FOCUS, focusTime
                )
            })
            ListItem(
                headlineText = { Text(text = "Short Break Lenght") },
                trailingContent = {
                    ChangeValueButton(
                        HomeViewModel.PomodoroState.SHORT_BREAK,
                        shortTime
                    )
                })

            ListItem(
                headlineText = { Text(text = "Long Break Lenght") },
                trailingContent = {
                    ChangeValueButton(
                        HomeViewModel.PomodoroState.LONG_BREAK,
                        longTime
                    )
                })

            ListItem(headlineText = { Text(text = "Sound ") }, trailingContent = {
                Switch(
                    checked = viewModel.sound.value,
                    onCheckedChange = {
                        viewModel.toggleSoundSetting(preferencesManager)
                    }
                )
            })


        }
    }
}