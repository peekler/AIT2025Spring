package hu.bme.ait.webviewdemo.ui.screen

import android.view.ViewGroup
import android.webkit.ConsoleMessage
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
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
import androidx.compose.ui.viewinterop.AndroidView
import com.google.accompanist.web.AccompanistWebViewClient
import com.google.accompanist.web.rememberWebViewNavigator
import com.google.accompanist.web.rememberWebViewState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WebViewScreen2() {

    val state = rememberWebViewState(url = "https://www.ait-budapest.com/")

    var url by remember{
        mutableStateOf("https://www.ait-budapest.com/") }
    var myUrl by remember{
        mutableStateOf("https://www.ait-budapest.com/") }

    Column {
        Row {
            OutlinedTextField(value = url, onValueChange = {
                url = it
            })
            Button(onClick = {
                myUrl = url
            }) {
                Text(text = "Go")
            }
        }

        // Adding a WebView inside AndroidView
        // with layout as full screen
        AndroidView(factory = {
            WebView(it).apply {
                this.layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
                this.webViewClient = CustomWebViewClient()
                this.webChromeClient = CustomWebChromeClient()
            }
        }, update = {
            it.loadUrl(myUrl)
        })
    }
}


class CustomWebViewClient: WebViewClient(){
    override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
        if(url != null && url.startsWith("https://google.com")){
            return true
        }
        return false
    }
}

class CustomWebChromeClient : WebChromeClient() {
    override fun onCloseWindow(window: WebView?) {}

    override fun onConsoleMessage(consoleMessage: ConsoleMessage?): Boolean {
        return true
    }
}