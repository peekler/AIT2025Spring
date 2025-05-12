package hu.ait.servicedemocompose

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import hu.ait.servicedemocompose.ui.theme.ServiceDemoComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val intentMyTimeService = Intent(
            this,
            MyTimeService::class.java)


        setContent {
            ServiceDemoComposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ServiceScreen(intentMyTimeService)
                }
            }
        }
    }
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun ServiceScreen(intentMyTimeService: Intent) {
    val context = LocalContext.current

    var permissionGranted by remember { mutableStateOf(false) }

    val permissionsState = rememberMultiplePermissionsState(
        permissions = listOf(
            android.Manifest.permission.FOREGROUND_SERVICE,
            android.Manifest.permission.POST_NOTIFICATIONS
        ),
        onPermissionsResult = {
            if (it[android.Manifest.permission.FOREGROUND_SERVICE]!! &&
                it[android.Manifest.permission.POST_NOTIFICATIONS]!!) {
                permissionGranted = true
            }
        }
    )

    LaunchedEffect(true) {
        permissionsState.launchMultiplePermissionRequest()
    }


    Column() {
        if (permissionGranted) {
            Button(onClick = {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

                    context.startForegroundService(
                        intentMyTimeService
                    )

                }
            }) {
                Text(text = "Start service")
            }


            Button(onClick = {
                context.stopService(intentMyTimeService)
            }) {
                Text(text = "Stop service")
            }
        }
    }
}