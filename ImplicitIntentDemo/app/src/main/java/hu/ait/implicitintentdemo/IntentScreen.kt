package hu.ait.implicitintentdemo

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

@Composable
fun IntentScreen() {
    val context = LocalContext.current

    Column() {
        Button(onClick = {
            //intentSearch(context)
            //intentCall(context)
            //intentSend(context)
            //intentWaze(context)
            intentStreetMaps(context)
        }) {
            Text(text = "Intent start")
        }
    }
}

fun intentSearch(context: Context) {
    val intent = Intent(Intent.ACTION_WEB_SEARCH)
    intent.putExtra(SearchManager.QUERY,"Balaton")
    context.startActivity(intent)
}

private fun intentCall(context: Context) {
    val intentCall = Intent(Intent.ACTION_DIAL,
        Uri.parse("tel:+36208225581")
    )
    context.startActivity(intentCall)
}

private fun intentSend(context: Context) {
    val intentSend = Intent(Intent.ACTION_SEND)
    intentSend.type = "text/plain"
    //intentSend.`package` = "com.facebook.katana"
    intentSend.putExtra(Intent.EXTRA_TEXT, "Share demo")
    context.startActivity(intentSend)
    //startActivity(Intent.createChooser(intentSend, "Select share app"));
}


private fun intentWaze(context: Context) {
    //String wazeUri = "waze://?favorite=Home&navigate=yes";
    //val wazeUri = "waze://?ll=40.761043, -73.980545&navigate=yes"
    val wazeUri = "waze://?q=AIT+Budapest&navigate=yes"
    val intentTest = Intent(Intent.ACTION_VIEW)
    intentTest.data = Uri.parse(wazeUri)
    context.startActivity(intentTest)
}

private fun intentStreetMaps(context: Context) {
    val gmmIntentUri = Uri.parse(
        "google.streetview:cbll=29.9774614,31.1329645&cbp=0,30,0,0,-15")
    val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
    mapIntent.setPackage("com.google.android.apps.maps")
    context.startActivity(mapIntent)
}