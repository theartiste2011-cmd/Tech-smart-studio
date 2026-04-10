package com.techsmartstudio.app

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.webkit.CookieManager
import android.webkit.PermissionRequest
import android.webkit.ValueCallback
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.FrameLayout

class MainActivity : Activity() {

    private lateinit var webView: WebView
    private var filePathCallback: ValueCallback<Array<Uri>>? = null

    companion object {
        private const val APP_URL = "https://tech-smart-studio-terrancemonico92573207.adaptive.ai"
        private const val FILE_CHOOSER_REQUEST_CODE = 1
    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Full-screen layout
        val frameLayout = FrameLayout(this)
        frameLayout.layoutParams = FrameLayout.LayoutParams(
            FrameLayout.LayoutParams.MATCH_PARENT,
            FrameLayout.LayoutParams.MATCH_PARENT
        )

        webView = WebView(this)
        webView.layoutParams = FrameLayout.LayoutParams(
            FrameLayout.LayoutParams.MATCH_PARENT,
            FrameLayout.LayoutParams.MATCH_PARENT
        )

        frameLayout.addView(webView)
        setContentView(frameLayout)

        setupWebView()

        if (savedInstanceState != null) {
            webView.restoreState(savedInstanceState)
        } else {
            webView.loadUrl(APP_URL)
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun setupWebView() {
        val settings: WebSettings = webView.settings

        // Enable JavaScript
        settings.javaScriptEnabled = true

        // Enable DOM storage (localStorage, sessionStorage)
        settings.domStorageEnabled = true

        // Enable database storage
        settings.databaseEnabled = true

        // Enable zoom
        settings.builtInZoomControls = true
        settings.displayZoomControls = false

        // Enable mixed content (HTTPS + HTTP resources)
        settings.mixedContentMode = WebSettings.MIXED_CONTENT_COMPATIBILITY_MODE

        // Media autoplay
        settings.mediaPlaybackRequiresUserGesture = false

        // Enable file access
        settings.allowFileAccess = true
        settings.allowContentAccess = true

        // Set user agent to look like a modern mobile browser
        settings.userAgentString = "Mozilla/5.0 (Linux; Android 11; Mobile) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Mobile Safari/537.36"

        // Cache mode
        settings.cacheMode = WebSettings.LOAD_DEFAULT

        // Enable cookies
        val cookieManager = CookieManager.getInstance()
        cookieManager.setAcceptCookie(true)
        cookieManager.setAcceptThirdPartyCookies(webView, true)

        // WebViewClient handles navigation
        webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                if (url == null) return false
                // Open external links in browser, keep internal links in WebView
                return if (url.startsWith("https://tech-smart-studio-terrancemonico92573207.adaptive.ai") ||
                           url.startsWith("https://adaptive.ai")) {
                    view?.loadUrl(url)
                    false
                } else {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                    startActivity(intent)
                    true
                }
            }
        }

        // WebChromeClient handles JS dialogs, file uploads, permissions
        webView.webChromeClient = object : WebChromeClient() {
            // Handle file input (<input type="file">)
            override fun onShowFileChooser(
                webView: WebView?,
                filePathCallback: ValueCallback<Array<Uri>>?,
                fileChooserParams: FileChooserParams?
            ): Boolean {
                this@MainActivity.filePathCallback?.onReceiveValue(null)
                this@MainActivity.filePathCallback = filePathCallback
                val intent = fileChooserParams?.createIntent() ?: Intent(Intent.ACTION_GET_CONTENT).apply {
                    type = "*/*"
                }
                startActivityForResult(intent, FILE_CHOOSER_REQUEST_CODE)
                return true
            }

            // Handle camera/mic permissions
            override fun onPermissionRequest(request: PermissionRequest?) {
                request?.grant(request.resources)
            }

            // Handle full-screen video
            override fun onShowCustomView(view: View?, callback: CustomViewCallback?) {
                super.onShowCustomView(view, callback)
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        webView.saveState(outState)
    }

    override fun onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack()
        } else {
            super.onBackPressed()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == FILE_CHOOSER_REQUEST_CODE) {
            val results = if (resultCode == RESULT_OK && data != null) {
                data.data?.let { arrayOf(it) } ?: arrayOfNulls<Uri>(0).filterNotNull().toTypedArray()
            } else {
                arrayOfNulls<Uri>(0).filterNotNull().toTypedArray()
            }
            filePathCallback?.onReceiveValue(results)
            filePathCallback = null
        }
    }
}
