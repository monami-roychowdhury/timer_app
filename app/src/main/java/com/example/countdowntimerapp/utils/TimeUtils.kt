package com.example.countdowntimerapp.utils

import java.util.concurrent.TimeUnit

object TimeUtils {
    const val TIME_COUNTDOWN: Long = 60000L
    const val TIME_COUNTDOWN_INTERVAL: Long = 1L
    const val TIME_COUNTDOWN_END = "00:00:00"
    const val TIME_COUNTDOWN_START = "01:00:00"
    private const val TIME_FORMAT = "%02d:%02d:%03d"

    fun Long.formatTime(): String = String.format(
        TIME_FORMAT,
        TimeUnit.MILLISECONDS.toMinutes(this),
        TimeUnit.MILLISECONDS.toSeconds(this) % 60,
        TimeUnit.MILLISECONDS.toMillis(this) % 1000
    )
}