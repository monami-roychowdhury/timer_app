package com.example.countdowntimerapp.viewmodel

import android.os.CountDownTimer
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.example.countdowntimerapp.worker.NotificationWorker
import com.example.countdowntimerapp.utils.TimeUtils.TIME_COUNTDOWN
import com.example.countdowntimerapp.utils.TimeUtils.TIME_COUNTDOWN_END
import com.example.countdowntimerapp.utils.TimeUtils.TIME_COUNTDOWN_INTERVAL
import com.example.countdowntimerapp.utils.TimeUtils.TIME_COUNTDOWN_START
import com.example.countdowntimerapp.utils.TimeUtils.formatTime
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class TimerViewModel(private val workManager: WorkManager) : ViewModel(), DefaultLifecycleObserver {
    private var countDownTimer: CountDownTimer? = null

    private val _time = MutableStateFlow(TIME_COUNTDOWN_START)
    val time = _time.asStateFlow()

    private val _isTimerRunning = MutableStateFlow(false)
    val isTimerRunning = _isTimerRunning.asStateFlow()

    private val _isTimerPaused = MutableStateFlow(false)
    val isTimerPaused = _isTimerPaused.asStateFlow()

    private val _progress = MutableStateFlow(0f)
    val progress = _progress.asStateFlow()

    private var currentMillis = 0L

    private var isAppInBackground = false


    /** Start timer */
    fun startTimer() {
        createTimer(TIME_COUNTDOWN)
    }

    /** Pause timer */
    fun pauseTimer() {
        countDownTimer?.cancel()
        _isTimerPaused.value = true
        _isTimerRunning.value = false

    }

    /** Resume timer */
    fun resumeTimer() {
        createTimer(currentMillis)
    }


    /** Stop and reset timer */
    fun stopAndResetTimer() {
        countDownTimer?.cancel()
        _time.value = TIME_COUNTDOWN_START
        _isTimerRunning.value = false
        _isTimerPaused.value = false
        _progress.value = 0f
    }

    private fun createTimer(millisInFuture: Long) {
        countDownTimer = object : CountDownTimer(millisInFuture, TIME_COUNTDOWN_INTERVAL) {
            override fun onTick(millisUntilFinished: Long) {
                currentMillis = millisUntilFinished
                _time.value = millisUntilFinished.formatTime()
                _isTimerRunning.value = true
                _progress.value = millisUntilFinished.toFloat() / TIME_COUNTDOWN
            }

            override fun onFinish() {
                countDownTimer?.cancel()
                _isTimerRunning.value = false
                _isTimerPaused.value = false
                _time.value = TIME_COUNTDOWN_END
                _progress.value = 0f
                if (isAppInBackground) {
                    showNotification()
                }

            }

        }.start()
    }

    override fun onPause(owner: LifecycleOwner) {
        super.onPause(owner)
        isAppInBackground = true
    }

    override fun onResume(owner: LifecycleOwner) {
        super.onResume(owner)
        isAppInBackground = false
    }


    private fun showNotification() {
        val workRequest = OneTimeWorkRequestBuilder<NotificationWorker>().build()
        workManager.enqueue(workRequest)

    }
}