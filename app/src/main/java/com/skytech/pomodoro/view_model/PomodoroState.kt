package com.skytech.pomodoro.view_model

import androidx.compose.ui.graphics.Color
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

sealed class PomodoroState(
    var durationInMinutes: Int,
    val description: String,
    val backgroundColor: Color,
    val titleColor: Color,
    val clockColor: Color,
    val buttonColor: Color,
    val iconColor: Color,
) {
    object FOCUS : PomodoroState(
        25 * 60, "FOCUS", focus_background_color, focus_title_color,
        focus_clock_color, focus_button_background_color, focus_icon_color
    )

    object SHORT_BREAK : PomodoroState(
        5 * 60, "SHORT\nBREAK", short_background_color, short_title_color,
        short_clock_color, short_button_background_color, short_icon_color
    )

    object LONG_BREAK : PomodoroState(
        10 * 60, "LONG\nBREAK", long_background_color, long_title_color,
        long_clock_color, long_button_background_color, long_icon_color
    )

}