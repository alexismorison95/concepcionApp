package com.morris.concepcionapp.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import com.morris.concepcionapp.Negocio
import com.morris.concepcionapp.R
import kotlinx.android.synthetic.main.activity_buscar.*


class BuscarActivity : AppCompatActivity() {

    private lateinit var toolbar: Toolbar
    private lateinit var buscarNotFound: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_buscar)

        // Catch parametros
        val busqueda = intent.getSerializableExtra("busqueda").toString()
        val tipoBusqueda = intent.getSerializableExtra("tipo").toString()

        // Database


        setViews(busqueda)


        // Lista que coincide con lo buscado
        var listaNegocios = buscar(busqueda, tipoBusqueda)
        listaNegocios = listOf() // Probando lista vacia

        // Validacion lista
        if (listaNegocios.isEmpty()) {
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
            adapter = NegocioAdapter(listaNegocios)
        }
    }

    private fun setViews(busqueda: String) {
        // Toolbar
        toolbar = findViewById(R.id.toolbarBuscar)
        toolbar.title = busqueda.capitalize()
        toolbar.setNavigationOnClickListener { this.finish() }

        // LinearLayout Not Found
        buscarNotFound = findViewById(R.id.buscar_not_found)
    }

    private fun buscar(busqueda: String, tipoBusqueda: String): List<Negocio> {

        // TODO: De momento retorno la lista, en el futuro seria una lista desde el servidor
        return listOf(
            Negocio("Tenttacione", "Todo el dia", "+5493442508072", "3442675657",
                "Pizzas y empanadas", "Rocamora 409, Concepción del Uruguay, Entre Ríos",
                "https://cdn2.cocinadelirante.com/sites/default/files/styles/gallerie/public/images/2019/11/masa-para-empanadas-economica.jpg",
                "gastronomia", "pizzas, empanadas"),

            Negocio("Dietética Puiggari", "De 8 a 14 hrs", "+5493442508072", "3442675657",
                "Almacén de alimentos y bebidas, Alimentos saludables", "Rocamora 409, Concepción del Uruguay, Entre Ríos",
                "https://www.restauracioncolectiva.com/admin/app/webroot/files/FotoNota/2724-imagen-almacenaje-alimentos-secos.jpg",
                "almacen", "bebidas, gaseosas, alimentos"),

            Negocio("Carniceria El Tropezón", "8.30 a 13.00hs", "+5493442508072", "3442675657",
                "Farmacia", "Rocamora 409, Concepción del Uruguay, Entre Ríos",
                "http://www.auladelafarmacia.com/media/auladelafarmacia/images/2019/06/26/2019062609021048880.jpg",
                "farmacia", "remedios, pastillas, farmacia"),

            Negocio("Farmacia Moderna", "08.00hs a 12.00hs y de 17.00hs a 20.30hs", "+5493442508072", "3442675657",
                "Pizzas y empanadas", "Rocamora 409, Concepción del Uruguay, Entre Ríos",
                "https://png.pngtree.com/png-clipart/20190515/original/pngtree-a-young-boy-riding-an-orange-delivery-scooter-png-image_3637498.jpg",
                "gastronomia", "pizzas, empanadas"),

            Negocio("Grieco materiales eléctricos e iluminacion", "Lun. a sab. 8.30 A 12.30 y 15.30 A 20", "+5493442508072", "3442675657",
                "Ferretería", "Rocamora 409, Concepción del Uruguay, Entre Ríos",
                "https://www.revista.ferrepat.com/wp-content/uploads/2017/03/bricopatexpressBuscarconGoogle-1.jpg",
                "ferreteria", "ferreteria, tornillos, herramientas"),

            Negocio("La fragancia", "8 a 12 y 16 a 18", "+5493442508072", "3442675657",
                "Limpieza y perfumería", "Rocamora 409, Concepción del Uruguay, Entre Ríos",
                "http://lacamara.com.ar/wp-content/uploads/2017/09/Aqua-perfumeria-001-1000x600.jpg",
                "limpieza", "limpieza, productos, lavandina, alcohol")
        )
    }
}
