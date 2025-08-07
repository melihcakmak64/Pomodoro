package com.skytech.pomodoro.view_model

import android.app.Application
import android.content.Context
import android.media.MediaPlayer
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.skytech.pomodoro.PreferencesManager
import com.skytech.pomodoro.R
import kotlinx.coroutines.launch

class HomeViewModel(application: Application) : AndroidViewModel(application) {
    private val preferencesManager = PreferencesManager.getInstance(application)

    private val _pomoState = mutableStateOf<PomodoroState>(PomodoroState.FOCUS)
    val pomoState: State<PomodoroState> = _pomoState

    val sound = mutableStateOf(false)
    val isPaused = mutableStateOf(true)
    val timeLeft = mutableStateOf(_pomoState.value.durationInMinutes)
    val cycleCount = mutableStateOf(0)

    private var mediaPlayer: MediaPlayer? = null

    fun initMediaPlayer(context: Context) {
        mediaPlayer = MediaPlayer.create(context, R.raw.timer)
    }

    fun togglePause() {
        isPaused.value = !isPaused.value
    }

    fun resetTime() {
        timeLeft.value = _pomoState.value.durationInMinutes
    }

    fun skipToNextState() {
        nextState(_pomoState.value, cycleCount.value)
        mediaPlayer?.stop()
        resetTime()
        cycleCount.value += 1
    }

    fun onTick() {
        if (timeLeft.value > 0) {
            timeLeft.value -= 1

            if (timeLeft.value == 3 && !isPaused.value && sound.value) {
                mediaPlayer?.start()
            }
        } else {
            nextState(_pomoState.value, cycleCount.value)
            resetTime()
            cycleCount.value += 1
        }
    }

    fun nextState(pomodoroState: PomodoroState, cycleCount: Int): PomodoroState {
        return when (pomodoroState) {
            PomodoroState.FOCUS -> {
                _pomoState.value = if (cycleCount % 8 == 6) {
                    PomodoroState.LONG_BREAK
                } else {
                    PomodoroState.SHORT_BREAK
                }
                _pomoState.value
            }

            PomodoroState.SHORT_BREAK,
            PomodoroState.LONG_BREAK -> {
                _pomoState.value = PomodoroState.FOCUS
                PomodoroState.FOCUS
            }
        }
    }

    fun loadSoundSetting() {
        viewModelScope.launch {
            sound.value = preferencesManager.getSound()
        }
    }

    fun toggleSoundSetting() {
        sound.value = !sound.value
        viewModelScope.launch {
            preferencesManager.setSound(sound.value)
        }
    }


}