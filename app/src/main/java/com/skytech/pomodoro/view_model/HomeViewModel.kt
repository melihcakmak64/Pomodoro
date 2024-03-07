package com.skytech.pomodoro.view_model

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import com.skytech.pomodoro.ui.theme.background_color_Blue_50
import com.skytech.pomodoro.ui.theme.background_color_Green50
import com.skytech.pomodoro.ui.theme.background_color_Red50
import com.skytech.pomodoro.ui.theme.clock_color_Blue900
import com.skytech.pomodoro.ui.theme.clock_color_Green900
import com.skytech.pomodoro.ui.theme.clock_color_Red900
import com.skytech.pomodoro.ui.theme.focus_color_RedAlpha100
import com.skytech.pomodoro.ui.theme.long_color_BlueAlpha100
import com.skytech.pomodoro.ui.theme.play_color_BlueAlpha600
import com.skytech.pomodoro.ui.theme.play_color_GreenAlph600
import com.skytech.pomodoro.ui.theme.play_color_RedAlpha700
import com.skytech.pomodoro.ui.theme.short_color_GreenAlpha100

class HomeViewModel : ViewModel(){
    private var _pomoState= mutableStateOf<PomodoroState>(PomodoroState.FOCUS)
    var pomoState: State<PomodoroState> =_pomoState
    var sound=
        mutableStateOf(false)






    sealed class PomodoroState(
        var durationInMinutes: Int,
        val description: String,
        val backgroundColor: Color,
        val titleColor: Color,
        val clockColor: Color,
        val timerColor: Color,
        val buttonColor: Color,
    ) {
        object FOCUS : PomodoroState(25*60, "Focus", background_color_Red50, focus_color_RedAlpha100,
            clock_color_Red900, play_color_RedAlpha700, focus_color_RedAlpha100
        )
        object SHORT_BREAK : PomodoroState(5*60, "Short Break", background_color_Green50, short_color_GreenAlpha100,
            clock_color_Green900, play_color_GreenAlph600, short_color_GreenAlpha100
        )
        object LONG_BREAK : PomodoroState(durationInMinutes = 10*60,"Long Break", background_color_Blue_50, long_color_BlueAlpha100,
            clock_color_Blue900, play_color_BlueAlpha600, long_color_BlueAlpha100
        )



        fun updateStateDuration(duration:Int){
            durationInMinutes=duration

        }
    }

    fun nextState(pomodoroState: PomodoroState, cycleCount: Int): PomodoroState {

        // Pomodoro durumuna göre işlemleri yap
        return when (pomodoroState) {
            PomodoroState.FOCUS -> {
                // Focus süresi bitti, Break süresini başlat
                if (cycleCount % 8 == 6 && cycleCount != 0) {
                    _pomoState.value = PomodoroState.LONG_BREAK
                    PomodoroState.LONG_BREAK
                } else {
                    _pomoState.value = PomodoroState.SHORT_BREAK
                    PomodoroState.SHORT_BREAK
                }
            }
            PomodoroState.SHORT_BREAK -> {
                // Break süresi bitti, Focus süresini başlat
                _pomoState.value = PomodoroState.FOCUS
                PomodoroState.FOCUS
            }
            else -> {
                _pomoState.value = PomodoroState.FOCUS
                PomodoroState.FOCUS
            }
        }
    }
    fun updateDuration(stateType:PomodoroState,newDuration: Int) {
        when (_pomoState.value) {
            is PomodoroState.FOCUS -> (_pomoState.value as PomodoroState.FOCUS).updateStateDuration(newDuration)
            is PomodoroState.SHORT_BREAK -> (_pomoState.value as PomodoroState.SHORT_BREAK).updateStateDuration(newDuration)
            is PomodoroState.LONG_BREAK -> (_pomoState.value as PomodoroState.LONG_BREAK).updateStateDuration(newDuration)
        }
    }

    fun changeSound(){
        sound.value=!sound.value

    }






}