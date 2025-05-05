package hu.bme.ait.webviewdemo.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.google.accompanist.web.AccompanistWebViewClient
import com.google.accompanist.web.WebView
import com.google.accompanist.web.rememberWebViewNavigator
import com.google.accompanist.web.rememberWebViewState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WebViewScreen() {

    val navigator = rememberWebViewNavigator()
    val state = rememberWebViewState(url = "https://www.ait-budapest.com/")

    var myUrl by remember{
        mutableStateOf("https://www.ait-budapest.com/") }

    Column {
        val webClient = remember {object : AccompanistWebViewClient(){}}

        Row {
            OutlinedTextField(value = myUrl, onValueChange = {
                myUrl = it
            })
            Button(onClick = {
                navigator.loadUrl(myUrl)
            }) {
                Text(text = "Go")
            }
        }


        WebView(
            state = state,
            modifier = Modifier
                .weight(1f),
            navigator = navigator,
            onCreated = { webView ->
                webView.settings.javaScriptEnabled = true
            },
            client = webClient
        )
    }
}