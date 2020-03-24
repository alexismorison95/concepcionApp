package com.morris.concepcionapp.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.morris.concepcionapp.R
import kotlinx.android.synthetic.main.activity_buscar.*

data class Negocio(val nombre: String,
                   val horario: String,
                   val whatsapp: String,
                   val telefono: String,
                   val descripcion: String,
                   val imagen: String,
                   val rubro: String,
                   val palabrasClave: String)

class BuscarActivity : AppCompatActivity() {

    private val negociosLista = listOf(
        Negocio("Tenttacione", "Todo el dia", "+5493442508072", "3442675657",
            "Pizzas y empanadas", "https://png.pngtree.com/png-clipart/20190515/original/pngtree-a-young-boy-riding-an-orange-delivery-scooter-png-image_3637498.jpg",
            "gastronomia", "pizzas, empanadas"),

        Negocio("Dietética Puiggari", "De 8 a 14 hrs", "+5493442508072", "3442675657",
            "Almacén de alimentos y bebidas, Alimentos saludables", "https://png.pngtree.com/png-clipart/20190515/original/pngtree-a-young-boy-riding-an-orange-delivery-scooter-png-image_3637498.jpg",
            "almacen", "bebidas, gaseosas, alimentos"),

        Negocio("Carniceria El Tropezón", "8.30 a 13.00hs", "+5493442508072", "3442675657",
            "Farmacia", "https://png.pngtree.com/png-clipart/20190515/original/pngtree-a-young-boy-riding-an-orange-delivery-scooter-png-image_3637498.jpg",
            "farmacia", "remedios, pastillas, farmacia"),

        Negocio("Farmacia Moderna", "08.00hs a 12.00hs y de 17.00hs a 20.30hs", "+5493442508072", "3442675657",
            "Pizzas y empanadas", "https://png.pngtree.com/png-clipart/20190515/original/pngtree-a-young-boy-riding-an-orange-delivery-scooter-png-image_3637498.jpg",
            "gastronomia", "pizzas, empanadas"),

        Negocio("Grieco materiales eléctricos e iluminacion", "Lun. a sab. 8.30 A 12.30 y 15.30 A 20", "+5493442508072", "3442675657",
            "Ferretería", "https://png.pngtree.com/png-clipart/20190515/original/pngtree-a-young-boy-riding-an-orange-delivery-scooter-png-image_3637498.jpg",
            "ferreteria", "ferreteria, tornillos, herramientas"),

        Negocio("La fragancia", "8 a 12 y 16 a 18", "+5493442508072", "3442675657",
            "Limpieza y perfumería", "https://png.pngtree.com/png-clipart/20190515/original/pngtree-a-young-boy-riding-an-orange-delivery-scooter-png-image_3637498.jpg",
            "limpieza", "limpieza, productos, lavandina, alcohol")
    )

    private lateinit var linearLayoutManager: LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_buscar)

        val busqueda = intent.getSerializableExtra("busqueda")
        val tipoBusqueda = intent.getSerializableExtra("tipo")

        // TODO: Cargo los negocios, en el futuro, la lista viene desde una bbdd
        recycle_list_view.apply {
            layoutManager = LinearLayoutManager(this.context)
            adapter = NegocioAdapter(negociosLista)
        }

//        linearLayoutManager = LinearLayoutManager(this)
//        recycle_list_view.layoutManager = linearLayoutManager
    }
}
