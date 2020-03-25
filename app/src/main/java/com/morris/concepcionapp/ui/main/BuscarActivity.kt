package com.morris.concepcionapp.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import com.morris.concepcionapp.R
import kotlinx.android.synthetic.main.activity_buscar.*

data class Negocio(val nombre: String,
                   val horario: String,
                   val whatsapp: String,
                   val telefono: String,
                   val descripcion: String,
                   val direccion: String,
                   val imagen: String,
                   val rubro: String,
                   val palabrasClave: String)

class BuscarActivity : AppCompatActivity() {

    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var toolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_buscar)

        // Catch parametros
        val busqueda = intent.getSerializableExtra("busqueda").toString()
        val tipoBusqueda = intent.getSerializableExtra("tipo").toString()

        // Toolbar
        toolbar = findViewById(R.id.toolbarBuscar)
        toolbar.title = busqueda.capitalize()
        toolbar.setNavigationOnClickListener { this.finish() }

        // Lista que coincide con lo buscado
        val listaNegocios = buscar(busqueda, tipoBusqueda)

        // Cargo las vistas en el recyclerView
        recycle_list_view.apply {
            layoutManager = LinearLayoutManager(this.context)
            adapter = NegocioAdapter(listaNegocios)
        }
    }

    private fun buscar(busqueda: String, tipoBusqueda: String): List<Negocio> {

        // TODO: De momento retorno la lista, en el futuro seria una lista desde el servidor
        return listOf(
            Negocio("Tenttacione", "Todo el dia", "+5493442508072", "3442675657",
                "Pizzas y empanadas", "Rocamora 409, Concepción del Uruguay, Entre Ríos",
                "https://png.pngtree.com/png-clipart/20190515/original/pngtree-a-young-boy-riding-an-orange-delivery-scooter-png-image_3637498.jpg",
                "gastronomia", "pizzas, empanadas"),

            Negocio("Dietética Puiggari", "De 8 a 14 hrs", "+5493442508072", "3442675657",
                "Almacén de alimentos y bebidas, Alimentos saludables", "Rocamora 409, Concepción del Uruguay, Entre Ríos",
                "https://png.pngtree.com/png-clipart/20190515/original/pngtree-a-young-boy-riding-an-orange-delivery-scooter-png-image_3637498.jpg",
                "almacen", "bebidas, gaseosas, alimentos"),

            Negocio("Carniceria El Tropezón", "8.30 a 13.00hs", "+5493442508072", "3442675657",
                "Farmacia", "Rocamora 409, Concepción del Uruguay, Entre Ríos",
                "https://png.pngtree.com/png-clipart/20190515/original/pngtree-a-young-boy-riding-an-orange-delivery-scooter-png-image_3637498.jpg",
                "farmacia", "remedios, pastillas, farmacia"),

            Negocio("Farmacia Moderna", "08.00hs a 12.00hs y de 17.00hs a 20.30hs", "+5493442508072", "3442675657",
                "Pizzas y empanadas", "Rocamora 409, Concepción del Uruguay, Entre Ríos",
                "https://png.pngtree.com/png-clipart/20190515/original/pngtree-a-young-boy-riding-an-orange-delivery-scooter-png-image_3637498.jpg",
                "gastronomia", "pizzas, empanadas"),

            Negocio("Grieco materiales eléctricos e iluminacion", "Lun. a sab. 8.30 A 12.30 y 15.30 A 20", "+5493442508072", "3442675657",
                "Ferretería", "Rocamora 409, Concepción del Uruguay, Entre Ríos",
                "https://png.pngtree.com/png-clipart/20190515/original/pngtree-a-young-boy-riding-an-orange-delivery-scooter-png-image_3637498.jpg",
                "ferreteria", "ferreteria, tornillos, herramientas"),

            Negocio("La fragancia", "8 a 12 y 16 a 18", "+5493442508072", "3442675657",
                "Limpieza y perfumería", "Rocamora 409, Concepción del Uruguay, Entre Ríos",
                "https://png.pngtree.com/png-clipart/20190515/original/pngtree-a-young-boy-riding-an-orange-delivery-scooter-png-image_3637498.jpg",
                "limpieza", "limpieza, productos, lavandina, alcohol")
        )
    }
}
