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

        if (tipoBusqueda == "todo") {
            // TODO: Terminar la busqueda personalizada
            // Busqueda por lo que sea
        }
        else {
            // busqueda por categoria
            val negociosRef = db.collection("negocios")

            negociosRef.whereEqualTo("rubro", busqueda.toLowerCase())
                .whereEqualTo("verificado", true)
                .get()
                .addOnSuccessListener { documents ->

                    var negocios = mutableListOf<Negocio>()

                    for (doc in documents) {

                        Log.d("Consulta", "${doc.id} => ${doc.data}")

                        negocios.add(doc.toObject(Negocio::class.java))
                    }

                    loadRecyclerViews(negocios)
                }
                .addOnFailureListener { exception ->
                    llProgressBar.visibility = View.GONE

                    textView.text = "Error del servidor"

                    recycle_list_view.visibility = View.GONE
                    buscarNotFound.visibility = View.VISIBLE

                    Toast.makeText(applicationContext, exception.message, Toast.LENGTH_LONG).show()
                }
        }
    }

}
