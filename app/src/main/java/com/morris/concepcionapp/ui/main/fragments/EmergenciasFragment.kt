package com.morris.concepcionapp.ui.main.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.morris.concepcionapp.R
import com.morris.concepcionapp.models.Emergencia
import com.morris.concepcionapp.ui.main.adapters.EmergenciaAdapter


class EmergenciasFragment : Fragment() {

    private lateinit var toolbar: Toolbar
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_emergencias, container, false)

        setViews(view)

        setListenners()

        val emergencias = loadEmergencias()

        recyclerView.apply {

            layoutManager = LinearLayoutManager(activity)
            adapter = EmergenciaAdapter(emergencias)
        }

        return view
    }

    private fun setViews(view: View) {

        // Toolbar
        toolbar = view.findViewById(R.id.toolbar)
        toolbar.title = "Emergencias"

        recyclerView = view.findViewById(R.id.recycle_list_view_emergencias)
    }

    private fun setListenners() {

        // Toolbar
        toolbar.setNavigationOnClickListener { activity?.finish() }
    }

    private fun loadEmergencias(): List<Emergencia> {

        return listOf(
            Emergencia("Defensa Civil", "",
                "100"),

            Emergencia("Policia", "",
                "101"),

            Emergencia("Emergencia Ambiental", "",
                "105"),

            Emergencia("Emergencia Náutica", "",
                "106"),

            Emergencia("Emergencia Médica", "",
                "107"),

            Emergencia("Bomberos", "Mitre Nº 1227",
                "03442423333"),

            Emergencia("Cruz Roja", "Artusi 246",
                "03442433468"),

            Emergencia("Ejército Argentino", "Suipacha 1699",
                "03442442820"),

            Emergencia("Gendarmería Nacional", "Suipacha 1500",
                "03442422610,03442428652"),

            Emergencia("Policia Federal", "San Martín 596",
                "03442425505"),

            Emergencia("Prefectura de Puerto", "Spiro 349",
                "03442425504,03442427304"),

            Emergencia("Prefectura de Zona", "8 de Junio 165",
                "03442422044,03442425503")
        )
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            EmergenciasFragment()
    }
}
