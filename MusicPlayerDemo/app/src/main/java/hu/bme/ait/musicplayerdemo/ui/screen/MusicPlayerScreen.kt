package hu.bme.ait.musicplayerdemo.ui.screen


import android.media.MediaPlayer
import android.media.RingtoneManager
import android.widget.SeekBar
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun MusicPlayerScreen(
    mediaPlayerViewModel: MusicPlayerViewModel =
        viewModel(factory = MusicPlayerViewModel.factory)
) {
    val context = LocalContext.current


    val uriNotif = RingtoneManager.getDefaultUri(
        RingtoneManager.TYPE_NOTIFICATION
    )
    val notiftone = RingtoneManager.getRingtone(
        context, uriNotif
    )

    Column() {
        Button(
            onClick = {
                //notiftone.play()
                mediaPlayerViewModel.startPlayer()
            },
            enabled = mediaPlayerViewModel.mediaPlayerPrepared.value
        ) {
            Text(text = "Play")
        }

        Button(
            onClick = {
                //notiftone.play()
                mediaPlayerViewModel.mediaPlayer.pause()
            },
            enabled = mediaPlayerViewModel.mediaPlayerPrepared.value
        ) {
            Text(text = "Stop")
        }

        var sliderPosition by remember { mutableStateOf(50f) }
        Slider(value = sliderPosition,
            enabled = mediaPlayerViewModel.mediaPlayerPrepared.value,
            valueRange = 0f..100f,
            onValueChange = {
                sliderPosition = it
                mediaPlayerViewModel.mediaPlayer.setVolume(it/100,
                    it.toFloat()/100)
            })


        Slider(value = mediaPlayerViewModel.mediaProgress.value,
            enabled = mediaPlayerViewModel.mediaPlayerPrepared.value,
            valueRange = 0f..mediaPlayerViewModel.duration.value.toFloat(),
            onValueChange = {
                mediaPlayerViewModel.mediaProgress.value = it
                mediaPlayerViewModel.mediaPlayer.seekTo(
                    mediaPlayerViewModel.mediaProgress.value.toInt())
            })
    }
}