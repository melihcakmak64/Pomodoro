package com.skytech.pomodoro.view.components

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.painterResource
import com.skytech.pomodoro.R
import com.skytech.pomodoro.view_model.PomodoroState

@Composable
fun ChangeValueButton(state: PomodoroState, lenght: MutableState<Int>) {

    Row(verticalAlignment = Alignment.CenterVertically) {
        IconButton(onClick = {
            if (lenght.value - 1 > 0) {
                lenght.value -= 1
                state.durationInMinutes = lenght.value * 60
            }
        }) {
            Icon(
                painter = painterResource(id = R.drawable.baseline_remove_24),
                contentDescription = "Increment"
            )
        }
        Text(text = (lenght.value).toString())
        IconButton(
            onClick = {
                lenght.value += 1
                state.durationInMinutes = lenght.value * 60

            }, enabled = lenght.value < 90
        ) {
            Icon(
                painter = painterResource(id = R.drawable.increment_icon),
                contentDescription = "Decrement"
            )
        }
    }
}


