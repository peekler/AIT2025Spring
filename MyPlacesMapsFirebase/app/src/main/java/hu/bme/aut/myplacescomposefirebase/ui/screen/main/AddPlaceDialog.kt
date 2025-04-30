package hu.bme.aut.myplacescomposefirebase.ui.screen.main

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.google.android.gms.maps.model.LatLng

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddNewPlaceDialog(
    latLng: LatLng,
    onAddPlace: (String, String) -> Unit,
    onDialogClose: () -> Unit = {},
) {
    var placeTitle by remember { mutableStateOf("") }
    var placeText by remember { mutableStateOf("") }

    Dialog(onDismissRequest = onDialogClose) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            shape = RoundedCornerShape(size = 6.dp)
        ) {
            Column(
                modifier = Modifier.padding(10.dp)
            ) {
                Text(text = "Lat: ${latLng.latitude}")
                Text(text = "Long: ${latLng.longitude}")

                OutlinedTextField(value = placeTitle,
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text(text = "Place title") },
                    onValueChange = {
                        placeTitle = it
                    }
                )
                OutlinedTextField(value = placeText,
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text(text = "Description") },
                    onValueChange = {
                        placeText = it
                    }
                )

                Button(onClick = {
                    onAddPlace(placeTitle, placeText)
                    onDialogClose()
                }) {
                    Text(text = "Add place")
                }
            }
        }
    }
}