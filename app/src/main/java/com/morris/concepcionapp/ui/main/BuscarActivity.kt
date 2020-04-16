package com.morris.concepcionapp.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.FirebaseFirestore
import com.morris.concepcionapp.Funciones
import com.morris.concepcionapp.models.Negocio
import com.morris.concepcionapp.R
import com.morris.concepcionapp.ui.main.adapters.NegocioAdapter
import kotlinx.android.synthetic.main.activity_buscar.*
import kotlinx.android.synthetic.main.toolbar.*


class BuscarActivity : AppCompatActivity() {

    private var busqueda: String? = null
    private var tipoBusqueda: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_buscar)

        // Catch parametros
        busqueda = intent.getStringExtra("busqueda")
        tipoBusqueda = intent.getStringExtra("tipo")

        // Toolbar title
        toolbar.title = busqueda?.capitalize()

        setListeners()

        getNegocios()
    }

    private fun setListeners() {

        // Toolbar
        toolbar.setNavigationOnClickListener { this.finish() }
    }

    private fun loadRecyclerViews(negocios: MutableList<Negocio>) {

        customProgressBar.visibility = View.GONE

        // Validacion lista
        if (negocios.isEmpty()) {

            recycle_list_view.visibility = View.GONE
            notFoundLinearLayout.visibility = View.VISIBLE
        }
        else {

            recycle_list_view.visibility = View.VISIBLE
            notFoundLinearLayout.visibility = View.GONE

            // Cargo las vistas en el recyclerView
            recycle_list_view.apply {

                layoutManager = LinearLayoutManager(this.context)
                adapter = NegocioAdapter(negocios)
            }
        }
    }

    private fun getNegocios() {

        // Muestro el progress bar
        customProgressBar.visibility = View.VISIBLE

        // Access a Cloud Firestore instance from your Activity
        val db = FirebaseFirestore.getInstance()

        val negociosRef = db.collection("negocios")

        if (tipoBusqueda == "todo") {

            // Busqueda por lo que sea
            negociosRef
                .whereEqualTo("verificado", true)
                .get()
                .addOnSuccessListener { documents ->

                    val negocios = mutableListOf<Negocio>()

                    for (doc in documents) {
                        negocios.add(doc.toObject(Negocio::class.java))
                    }

                    loadRecyclerViews( filtrarBusqueda(negocios) )
                }
                .addOnFailureListener {

                    showError()
                }
        }
        else {

            // busqueda por categoria
            negociosRef
                .whereEqualTo("rubro", busqueda!!.toLowerCase())
                .whereEqualTo("verificado", true)
                .get()
                .addOnSuccessListener { documents ->

                    val negocios = mutableListOf<Negocio>()

                    for (doc in documents) {
                        negocios.add(doc.toObject(Negocio::class.java))
                    }

                    negocios.sortBy { it.nombre }

                    loadRecyclerViews(negocios)
                }
                .addOnFailureListener {

                    showError()
                }
        }
    }

    private fun filtrarBusqueda(negocios: MutableList<Negocio>): MutableList<Negocio> {

        val res = mutableListOf<Negocio>()

        // Ahora itero para cada atributo que se puede buscar
        for (neg in negocios) {

            // Nombre
            if (Funciones.quitarTildes(neg.nombre!!).contains(busqueda!!)) {
                if (!res.contains(neg)) { res.add(neg) }
            }

            // Horario
            if (Funciones.quitarTildes(neg.horario!!).contains(busqueda!!)) {
                if (!res.contains(neg)) { res.add(neg) }
            }

            // Descripcion
            if (Funciones.quitarTildes(neg.descripcion!!).contains(busqueda!!)) {
                if (!res.contains(neg)) { res.add(neg) }
            }

            // Rubro
            if (Funciones.quitarTildes(neg.rubro!!).contains(busqueda!!)) {
                if (!res.contains(neg)) { res.add(neg) }
            }
        }

        res.sortBy { it.nombre }

        return res
    }

    private fun showError() {

        customProgressBar.visibility = View.GONE

        textViewNotFound.text = "Error del servidor"

        recycle_list_view.visibility = View.GONE
        notFoundLinearLayout.visibility = View.VISIBLE
    }

}
