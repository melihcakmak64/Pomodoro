package com.skytech.pomodoro.view

import android.media.MediaPlayer
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FastForward
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import com.skytech.pomodoro.PreferencesManager
import com.skytech.pomodoro.R
import com.skytech.pomodoro.ui.theme.RubikMonoOne
import com.skytech.pomodoro.view.components.PomoButton
import com.skytech.pomodoro.view.components.ProgressOutlineRoundedRect
import com.skytech.pomodoro.view_model.HomeViewModel
import kotlinx.coroutines.delay


@Composable
fun HomePage() {
    val homeViewModel: HomeViewModel = viewModel()
    val viewState by homeViewModel.pomoState
    val timeLeft by homeViewModel.timeLeft
    val isPaused by homeViewModel.isPaused
    val context = LocalContext.current
    val openAlertDialog = remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        homeViewModel.initMediaPlayer(context)
        homeViewModel.loadSoundSetting()
    }

    LaunchedEffect(timeLeft, isPaused) {
        if (!isPaused && timeLeft >= 0) {
            delay(1000L)
            homeViewModel.onTick()
        }
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = viewState.backgroundColor
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {

            //-------------------------------TIME----------------------------//
            Box(
                modifier = Modifier
                    .size(height = 245.dp, width = 240.dp)
                    .background(
                        color = Color.White,
                        shape = RoundedCornerShape(bottomStart = 120.dp, bottomEnd = 120.dp)
                    ), contentAlignment = Alignment.Center
            ) {


                ProgressOutlineRoundedRect(
                    modifier = Modifier.fillMaxSize(),
                    progress = timeLeft / viewState.durationInMinutes.toFloat(),
                    color = viewState.buttonColor,
                    strokeWidth = 6.dp,
                    cornerRadius = 120.dp
                )

                // Dijital Saat
                Text(
                    text = "${(timeLeft / 60).toString().padStart(2, '0')}\n${
                        (timeLeft % 60).toString().padStart(2, '0')
                    }",
                    fontSize = 96.sp,
                    fontWeight = FontWeight(400),
                    color = viewState.clockColor,
                    fontFamily = RubikMonoOne,
                    lineHeight = 92.sp
                )
            }

            Spacer(modifier = Modifier.height(30.dp))


            //---------------FOCUS-----------//
            Box(modifier = Modifier.height(150.dp)) {
                Text(
                    text = viewState.description,
                    fontSize = 40.sp,
                    fontWeight = FontWeight.W400,
                    lineHeight = 64.sp,
                    fontFamily = RubikMonoOne,
                    color = viewState.titleColor

                )
            }


            //----------------------------BUTTONS------------------------------------------//

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {


                PomoButton(
                    height = 72.dp,
                    width = 72.dp,
                    backgroundColor = viewState.buttonColor,
                    buttonIcon = if (isPaused) Icons.Default.PlayArrow else Icons.Default.Pause,
                    iconColor = viewState.iconColor,
                    onClick = { homeViewModel.togglePause() })
                Spacer(modifier = Modifier.width(15.dp))

                PomoButton(
                    height = 72.dp,
                    width = 72.dp,
                    backgroundColor = viewState.buttonColor,
                    buttonIcon = Icons.Default.FastForward,
                    iconColor = viewState.iconColor,
                    onClick = {
                        homeViewModel.skipToNextState()
                    })

            }
            Spacer(modifier = Modifier.height(50.dp))
            PomoButton(
                height = 72.dp,
                width = 72.dp,
                backgroundColor = Color.Transparent,
                buttonIcon = Icons.Default.Settings,
                iconColor = viewState.iconColor,
                onClick = {
                    homeViewModel.isPaused.value = true
                    openAlertDialog.value = true

                })

            if (openAlertDialog.value == true) {
                Dialog(
                    onDismissRequest = {
                        openAlertDialog.value = false
                        homeViewModel.resetTime()
                    },
                ) {

                    SettingsScreen(closeButton = {
                        openAlertDialog.value = false
                        homeViewModel.resetTime()


                    }, viewModel = homeViewModel)

                }
            }

        }
    }

}



