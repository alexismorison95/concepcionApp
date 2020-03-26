package com.morris.concepcionapp.ui.main

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
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

import com.morris.concepcionapp.R
import java.lang.Exception
import java.net.HttpURLConnection
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
    private lateinit var btnAtras: Button
    private lateinit var btnAdelante: Button
    private lateinit var btnReload: Button
    private var urlMatutino: String? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_noticias, container, false)

        loadViews(view)

        loadPDF()

        return view
    }

    private fun loadViews(view: View) {
        webView = view.findViewById(R.id.noticias_webView)
        progressBar = view.findViewById(R.id.progressBar)
        btnAtras = view.findViewById(R.id.btnAtras)
        btnAdelante = view.findViewById(R.id.btnAdelante)
        btnReload = view.findViewById(R.id.btnReload)
    }

    private fun setListneresBtns(wv: WebView) {
        btnAtras.setOnClickListener {
            if (wv.canGoBack()) {
                wv.goBack()
            }
        }

        btnAdelante.setOnClickListener {
            if (wv.canGoForward()) {
                wv.goForward()
            }
        }

        btnReload.setOnClickListener {
            wv.reload()
        }
    }

    private fun Date.toString(format: String, locale: Locale = Locale.getDefault()): String {
        val formatter = SimpleDateFormat(format, locale)
        return formatter.format(this)
    }

    private fun getCurrentDateTime(): Date {
        return Calendar.getInstance().time
    }

    private fun loadPDF() {
        webView.webViewClient = MyWebClient()
        webView.settings.setSupportZoom(true)
        webView.settings.javaScriptEnabled = true

        // Debido a error al entrar en la pagina del gobierno
        //webView.webChromeClient = MyWebChromeClient()

        // Seteo la navegacion de los botones
        setListneresBtns(webView)

        // Fecha Actual
        val date = getCurrentDateTime()
        val matutinoFecha = date.toString("dd-MM")
        //val viserinoFecha = date.toString("dd-MM-yy")

        urlMatutino = "https://www.argentina.gob.ar/sites/default/files/covid19_informe-diario-matutino-$matutinoFecha.pdf"
        //val urlVisperino = "https://www.argentina.gob.ar/sites/default/files/$viserinoFecha-reporte-diario-vespertino-covid-19.pdf"

        webView.loadUrl("https://drive.google.com/viewerng/viewer?embedded=true&url=$urlMatutino")

        // webView.loadUrl(urlVisperino)
    }

    // Nuevo WebClient para poner un progressBar
    inner class MyWebClient : WebViewClient() {
        override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
            progressBar.visibility = View.VISIBLE
            view.loadUrl(url)
            return true
        }

        override fun onPageFinished(view: WebView, url: String) {
            super.onPageFinished(view, url)
            progressBar.visibility = View.GONE
        }

        override fun onReceivedError(view: WebView?, request: WebResourceRequest?, error: WebResourceError?) {
            super.onReceivedError(view, request, error)
            webView.loadUrl("https://drive.google.com/viewerng/viewer?embedded=true&url=$urlMatutino")
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = NoticiasFragment()
    }
}
