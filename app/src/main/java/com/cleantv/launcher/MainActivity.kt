package com.cleantv.launcher

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.webkit.JavascriptInterface
import android.webkit.WebSettings
import android.webkit.WebView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var webView: WebView

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        webView = WebView(this)
        setContentView(webView)

        val settings: WebSettings = webView.settings
        settings.javaScriptEnabled = true
        settings.domStorageEnabled = true
        settings.allowFileAccess = true

        // TV Remote navigation & bridge
        webView.addJavascriptInterface(WebAppInterface(), "AndroidBridge")
        webView.loadUrl("file:///android_asset/ui/index.html")
    }

    inner class WebAppInterface {
        @JavascriptInterface
        fun launchApp(packageName: String) {
            val launchIntent = packageManager.getLaunchIntentForPackage(packageName)
            if (launchIntent != null) {
                startActivity(launchIntent)
            } else {
                runOnUiThread {
                    Toast.makeText(this@MainActivity, "App not installed: $packageName", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onBackPressed() {
        // Prevent accidental closing on TV back button
    }
}
