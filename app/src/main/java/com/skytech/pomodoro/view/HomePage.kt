package com.skytech.pomodoro.view

import android.media.MediaPlayer
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.FastForward
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Icon
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
import com.skytech.pomodoro.R
import com.skytech.pomodoro.view_model.HomeViewModel
import kotlinx.coroutines.delay



@Composable
fun HomePage(){
    val homeViewModel: HomeViewModel = viewModel()
    val viewState by homeViewModel.pomoState
    val context = LocalContext.current
    val mediaPlayer = remember { MediaPlayer.create(context, R.raw.timer) }


    var isPaused by remember { mutableStateOf(true) }
    var timeLeft by remember { mutableStateOf(viewState.durationInMinutes) }
    var cycleCount by remember{ mutableStateOf(0) }
    val openAlertDialog = remember { mutableStateOf(false) }


    LaunchedEffect(key1=timeLeft,key2 = isPaused){
        if (timeLeft==0){
            homeViewModel.nextState(viewState,cycleCount)
            timeLeft=viewState.durationInMinutes
            cycleCount++
        }

        if (timeLeft == 3 && !isPaused&& homeViewModel.sound.value) {
            // Play sound when timeLeft is 3 seconds and not paused
            mediaPlayer.start()
        }


        while (timeLeft > 0&& !isPaused) {
        delay(1000L)
        timeLeft--
    }

    }
    Surface(modifier = Modifier.fillMaxSize(),
        color = viewState.backgroundColor){
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            //---------------FOCUS-----------//
            Box(
                modifier = Modifier
                    .border(
                        1.dp,
                        color = Color.Black,
                        shape = RoundedCornerShape(16.dp)
                    )

                    .background(viewState.titleColor, shape = RoundedCornerShape(16.dp))


            )
            {
                Row(modifier = Modifier.padding(8.dp))
                {
                    Icon(imageVector = Icons.Default.AccessTime, contentDescription = "Brain")
                    Spacer(modifier = Modifier.size(10.dp))
                    Text(
                        text = viewState.description,
                        fontSize = 19.sp
                    )
                }
            }


            //-------------------------------TIME----------------------------//
            Text(
                text = "${(timeLeft / 60).toString().padStart(2, '0')}\n\n\n\n\n\n\n${
                    (timeLeft % 60).toString().padStart(2, '0')
                }",
                fontSize = 210.sp,
                fontWeight = FontWeight(800),
                color = viewState.clockColor
            )


            //----------------------------BUTTONS------------------------------------------//

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {

                PomoButton(
                    height = 80.dp,
                    width = 80.dp,
                    backgroundColor = viewState.buttonColor,
                    buttonIcon = Icons.Default.MoreHoriz,
                    onClick = {
                        openAlertDialog.value=true

                    })
                Spacer(modifier = Modifier.width(15.dp))
                PomoButton(
                    height = 96.dp,
                    width = 128.dp,
                    backgroundColor = viewState.timerColor,
                    buttonIcon = if (isPaused) Icons.Default.PlayArrow else Icons.Default.Pause,
                    onClick = { isPaused = !isPaused })
                Spacer(modifier = Modifier.width(15.dp))

                PomoButton(
                    height = 80.dp,
                    width = 80.dp,
                    backgroundColor = viewState.buttonColor,
                    buttonIcon = Icons.Default.FastForward,
                    onClick = {
                         homeViewModel.nextState(viewState, cycleCount )
                        mediaPlayer.stop()



                        timeLeft=viewState.durationInMinutes
                        cycleCount++





                    })

            }

            if (openAlertDialog.value==true){
                Dialog(onDismissRequest = {openAlertDialog.value=false
                    timeLeft=viewState.durationInMinutes
                                          },) {
                    SettingsScreen(closeButton = {
                        openAlertDialog.value=false
                        timeLeft=viewState.durationInMinutes


                    }, viewModel = homeViewModel)

                }
            }


        }
    }




}



