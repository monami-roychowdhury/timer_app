package com.example.countdowntimerapp.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.example.countdowntimerapp.R
import com.example.countdowntimerapp.viewmodel.TimerViewModel

@Composable
fun CountDownTimerView(modifier: Modifier = Modifier, timerViewModel: TimerViewModel) {
    val currentTime = timerViewModel.time.collectAsState().value
    val isTimerRunning = timerViewModel.isTimerRunning.collectAsState().value
    val isTimerPaused = timerViewModel.isTimerPaused.collectAsState().value
    val progress = timerViewModel.progress.collectAsState().value

    val density = LocalDensity.current

    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(id = R.string.header),
            fontSize = with(density) {
                dimensionResource(id = R.dimen.sp_18).toSp()
            }
        )
        Spacer(modifier = Modifier.padding(dimensionResource(id = R.dimen.dp_20)))
        Box(
            modifier = Modifier.padding(horizontal = dimensionResource(id = R.dimen.dp_16)),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(
                color = Color.Green,
                modifier = Modifier.size(dimensionResource(id = R.dimen.dp_250)),
                progress = progress,
                strokeWidth = dimensionResource(id = R.dimen.dp_12)
            )
            Text(
                text = currentTime,
                fontSize = with(density) {
                    dimensionResource(id = R.dimen.sp_24).toSp()
                }, fontWeight = FontWeight.Bold
            )
        }
        Spacer(modifier = Modifier.padding(dimensionResource(id = R.dimen.dp_20)))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround) {
            val runBtnText = if (isTimerRunning) {
                stringResource(id = R.string.pause)
            } else {
                stringResource(id = R.string.start)
            }
            Button(onClick = {
                if (isTimerRunning) {
                    timerViewModel.pauseTimer()
                } else if (isTimerPaused) {
                    timerViewModel.resumeTimer()
                } else {
                    timerViewModel.startTimer()
                }
            }) {
                Text(text = runBtnText)
            }
            Button(onClick = {
                timerViewModel.stopAndResetTimer()
            }) {
                Text(text = stringResource(id = R.string.stop))
            }
        }

    }
}