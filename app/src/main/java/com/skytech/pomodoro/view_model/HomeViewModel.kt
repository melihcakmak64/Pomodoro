package com.skytech.pomodoro.view_model

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel

import com.skytech.pomodoro.ui.theme.focus_background_color
import com.skytech.pomodoro.ui.theme.focus_button_background_color
import com.skytech.pomodoro.ui.theme.focus_clock_color
import com.skytech.pomodoro.ui.theme.focus_icon_color
import com.skytech.pomodoro.ui.theme.focus_title_color
import com.skytech.pomodoro.ui.theme.long_background_color
import com.skytech.pomodoro.ui.theme.long_button_background_color
import com.skytech.pomodoro.ui.theme.long_clock_color
import com.skytech.pomodoro.ui.theme.long_icon_color
import com.skytech.pomodoro.ui.theme.long_title_color
import com.skytech.pomodoro.ui.theme.short_background_color
import com.skytech.pomodoro.ui.theme.short_button_background_color
import com.skytech.pomodoro.ui.theme.short_clock_color
import com.skytech.pomodoro.ui.theme.short_icon_color
import com.skytech.pomodoro.ui.theme.short_title_color

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
        val buttonColor: Color,
        val iconColor: Color,

        ) {
        object FOCUS : PomodoroState(25*60, "FOCUS", focus_background_color, focus_title_color,
            focus_clock_color, focus_button_background_color, focus_icon_color
        )
        object SHORT_BREAK : PomodoroState(5*60, "SHORT\nBREAK", short_background_color, short_title_color,
            short_clock_color, short_button_background_color,
            short_icon_color
        )
        object LONG_BREAK : PomodoroState(durationInMinutes = 10*60,"LONG\n" +
                "BREAK",  long_background_color, long_title_color,
            long_clock_color, long_button_background_color, long_icon_color
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


    fun changeSound(){
        sound.value=!sound.value

    }






}