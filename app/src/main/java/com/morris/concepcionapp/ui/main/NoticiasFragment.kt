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
import com.google.android.material.floatingactionbutton.FloatingActionButton

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
    private lateinit var btnReload: FloatingActionButton
    private lateinit var urlMV: String


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_noticias, container, false)

        loadViews(view)

        setListeners()

        loadPDF()

        return view
    }

    private fun loadViews(view: View) {

        webView = view.findViewById(R.id.noticias_webView)

        progressBar = view.findViewById(R.id.progressBar)

        btnReload = view.findViewById(R.id.btnReload)
    }

    private fun setListeners() {

        btnReload.setOnClickListener { webView.reload() }
    }

    private fun Date.toString(format: String, locale: Locale = Locale.getDefault()): String {

        val formatter = SimpleDateFormat(format, locale)

        return formatter.format(this)
    }

    private fun getUrlMV(): String {

        val date = Calendar.getInstance().time

        val hora = date.toString("HH").toInt()

        val calendar = Calendar.getInstance()

        if (hora in 2..18) {

            // Vespertino dia anterior
            calendar.add(Calendar.DAY_OF_YEAR, -1)

            val fecha = calendar.time.toString("dd-MM-yy")

            return "https://drive.google.com/viewerng/viewer?embedded=true&url=https://www.argentina.gob.ar/sites/default/files/$fecha-reporte-vespertino-covid-19.pdf"
        }
        else {

            return if (hora < 2) {

                // Matutino dia anterior
                calendar.add(Calendar.DAY_OF_YEAR, -1)

                val fecha = calendar.time.toString("dd-MM-yy")

                "https://drive.google.com/viewerng/viewer?embedded=true&url=https://www.argentina.gob.ar/sites/default/files/$fecha-reporte-matutino-covid-19.pdf"
            } else {

                // Matutino de hoy

                val fecha = calendar.time.toString("dd-MM-yy")

                "https://drive.google.com/viewerng/viewer?embedded=true&url=https://www.argentina.gob.ar/sites/default/files/$fecha-reporte-matutino-covid-19.pdf"
            }
        }
    }

    private fun loadPDF() {

        webView.webViewClient = MyWebClient()
        webView.settings.javaScriptEnabled = true // Necesito esto para que google muestre los controles del pdf
        webView.settings.builtInZoomControls = true
        webView.settings.displayZoomControls = false

        urlMV = getUrlMV()

        webView.loadUrl(urlMV)
    }

    // Nueva implementacion de WebClient para poner un progressBar
    inner class MyWebClient : WebViewClient() {

        override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {

            progressBar.visibility = View.VISIBLE

            if (url == urlMV) {
                view.loadUrl(url)
            }
            else {
                Toast.makeText(view.context, "No está permitida la navegación", Toast.LENGTH_LONG).show()
                view.loadUrl(urlMV)
            }

            return true
        }

        override fun onPageFinished(view: WebView, url: String) {
            super.onPageFinished(view, url)

            progressBar.visibility = View.GONE
        }

        override fun onReceivedError(view: WebView?, request: WebResourceRequest?, error: WebResourceError?) {
            super.onReceivedError(view, request, error)

            webView.loadUrl(urlMV)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = NoticiasFragment()
    }
}
