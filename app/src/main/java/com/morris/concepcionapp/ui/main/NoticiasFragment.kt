package com.morris.concepcionapp.ui.main

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.ContactsContract
import android.provider.ContactsContract.CommonDataKinds.Website.URL
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.*
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.view.get
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.api.Http

import com.morris.concepcionapp.R
import java.lang.Exception
import java.net.HttpURLConnection
import java.nio.file.DirectoryStream
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import java.util.logging.SimpleFormatter

/**
 * A simple [Fragment] subclass.
 * Use the [NoticiasFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class NoticiasFragment : Fragment() {

    private lateinit var webView: WebView
    private lateinit var progressBar: ProgressBar

    private lateinit var btnReload: Button
    private lateinit var btnBack: Button
    private lateinit var btnForward: Button

    private lateinit var urlGoogle: String


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_noticias, container, false)

        loadViews(view)

        setListeners()

        loadWebDefault()

        return view
    }

    private fun loadViews(view: View) {

        btnReload = view.findViewById(R.id.btnReload)
        btnBack = view.findViewById(R.id.btnBack)
        btnForward = view.findViewById(R.id.btnForward)

        webView = view.findViewById(R.id.noticias_webView)

        progressBar = view.findViewById(R.id.progressBar)
    }

    private fun setListeners() {

        btnReload.setOnClickListener { webView.reload() }

        btnBack.setOnClickListener {

            if (webView.canGoBack()) {

                webView.goBack()
                progressBar.visibility = View.GONE
            }
        }

        btnForward.setOnClickListener {

            if (webView.canGoForward()) {
                
                webView.goForward()
                progressBar.visibility = View.GONE
            }
        }
    }

    private fun loadWebDefault() {

        webView.webViewClient = MyWebClient()
        webView.webChromeClient = MyChromeClient()
        webView.settings.javaScriptEnabled = true
//        webView.settings.builtInZoomControls = true
//        webView.settings.displayZoomControls = false

        urlGoogle = "https://news.google.com/topstories?hl=es-419&gl=AR&ceid=AR:es-419"

        webView.loadUrl(urlGoogle)
    }

    // Nueva implementacion de WebClient para poner un progressBar
    inner class MyWebClient : WebViewClient() {

        override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {

            progressBar.visibility = View.VISIBLE

            return true
        }

        override fun onPageFinished(view: WebView, url: String) {
            super.onPageFinished(view, url)

            progressBar.visibility = View.GONE
        }

        override fun onReceivedError(view: WebView?, request: WebResourceRequest?, error: WebResourceError?) {
            super.onReceivedError(view, request, error)

            webView.loadUrl(urlGoogle)
        }
    }

    inner class MyChromeClient : WebChromeClient() {

        override fun getDefaultVideoPoster(): Bitmap? {

            return if (super.getDefaultVideoPoster() == null) {

                BitmapFactory.decodeResource(context?.resources, R.drawable.ic_google_downasaur)
            }
            else {

                super.getDefaultVideoPoster()
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = NoticiasFragment()
    }
}
