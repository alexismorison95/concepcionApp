package com.morris.concepcionapp.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.FirebaseFirestore
import com.morris.concepcionapp.Negocio
import com.morris.concepcionapp.R
import kotlinx.android.synthetic.main.activity_buscar.*
import java.text.Normalizer


class BuscarActivity : AppCompatActivity() {

    private lateinit var toolbar: Toolbar
    private lateinit var buscarNotFound: LinearLayout
    private lateinit var llProgressBar: LinearLayout
    private lateinit var textView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_buscar)

        // Catch parametros
        val busqueda = intent.getSerializableExtra("busqueda").toString()
        val tipoBusqueda = intent.getSerializableExtra("tipo").toString()

        setViews(busqueda)

        // Busco en la bbdd
        getNegocios(busqueda, tipoBusqueda)
    }

    private fun setViews(busqueda: String) {
        // Toolbar
        toolbar = findViewById(R.id.toolbarBuscar)
        toolbar.title = busqueda.capitalize()
        toolbar.setNavigationOnClickListener { this.finish() }

        // LinearLayout Not Found
        buscarNotFound = findViewById(R.id.buscar_not_found)

        // Progress Bar
        llProgressBar = findViewById(R.id.llProgrssBar)

        // TextView
        textView = findViewById(R.id.textViewNotFound)
    }

    private fun loadRecyclerViews(negocios: MutableList<Negocio>) {

        llProgressBar.visibility = View.GONE

        // Validacion lista
        if (negocios.isEmpty()) {
            recycle_list_view.visibility = View.GONE
            buscarNotFound.visibility = View.VISIBLE
        }
        else {
            recycle_list_view.visibility = View.VISIBLE
            buscarNotFound.visibility = View.GONE
        }

        // Cargo las vistas en el recyclerView
        recycle_list_view.apply {
            layoutManager = LinearLayoutManager(this.context)
            adapter = NegocioAdapter(negocios)
        }
    }

    private fun getNegocios(busqueda: String, tipoBusqueda: String) {

        // Muestro el progress bar
        llProgressBar.visibility = View.VISIBLE

        // Access a Cloud Firestore instance from your Activity
        val db = FirebaseFirestore.getInstance()

        val negociosRef = db.collection("negocios")

        if (tipoBusqueda == "todo") {
            // Busqueda por lo que sea
            negociosRef.whereEqualTo("verificado", true)
                .get()
                .addOnSuccessListener { documents ->
                    var negocios = mutableListOf<Negocio>()

                    for (doc in documents) {
                        negocios.add(doc.toObject(Negocio::class.java))
                    }

                    var negociosFiltrados = filtrarBusqueda(negocios, busqueda)

                    loadRecyclerViews(negociosFiltrados)
                }
                .addOnFailureListener {
                    showError()
                }
        }
        else {
            // busqueda por categoria
            negociosRef.whereEqualTo("rubro", busqueda.toLowerCase())
                .whereEqualTo("verificado", true)
                .get()
                .addOnSuccessListener { documents ->

                    var negocios = mutableListOf<Negocio>()

                    for (doc in documents) {
                        negocios.add(doc.toObject(Negocio::class.java))
                    }

                    loadRecyclerViews(negocios)
                }
                .addOnFailureListener {
                    showError()
                }
        }
    }

    private fun filtrarBusqueda(negocios: MutableList<Negocio>, busqueda: String): MutableList<Negocio> {
        var res = mutableListOf<Negocio>()

        // Ahora itero para cada atributo que se puede buscar
        for (neg in negocios) {
            // Nombre
            if (quitarTildes(neg.nombre!!).contains(busqueda)) { res.add(neg) }
            // Horario
            if (quitarTildes(neg.horario!!).contains(busqueda)) { res.add(neg) }
            // Descripcion
            if (quitarTildes(neg.descripcion!!).contains(busqueda)) { res.add(neg) }
            // Rubro
            if (quitarTildes(neg.rubro!!).contains(busqueda)) { res.add(neg) }
        }

        // Quito los objetos repetidos
        res.distinctBy { Pair(it.nombre, it.imagenURL) }

        return res
    }

    private fun quitarTildes(palabra: String): String {
        val REGEX_UNACCENT = "\\p{InCombiningDiacriticalMarks}+".toRegex()

        val temp = Normalizer.normalize(palabra, Normalizer.Form.NFD)
        val sinTildes = REGEX_UNACCENT.replace(temp, "")
        Log.d("Sin tilde", sinTildes)

        return sinTildes
    }

    private fun showError() {
        llProgressBar.visibility = View.GONE

        textView.text = "Error del servidor"

        recycle_list_view.visibility = View.GONE
        buscarNotFound.visibility = View.VISIBLE
    }

}
