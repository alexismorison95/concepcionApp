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
import com.morris.concepcionapp.models.Remiseria
import com.morris.concepcionapp.ui.main.adapters.RemiseriaAdapter
import kotlinx.android.synthetic.main.fragment_remiserias.*


class RemiseriasFragment : Fragment() {

    private lateinit var toolbar: Toolbar
    private lateinit var recyclerView: RecyclerView


    override fun onCreate(savedInstanceState: Bundle?) {

        // Theme
        //activity?.setTheme(R.style.AppThemeRemiseria)

        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_remiserias, container, false)

        setViews(view)

        setListenners()

        val remiserias = loadRemiserias()

        recyclerView.apply {

            layoutManager = LinearLayoutManager(activity)
            adapter = RemiseriaAdapter(remiserias)
        }

        return view
    }

    private fun setViews(view: View) {

        // Toolbar
        toolbar = view.findViewById(R.id.toolbar)
        toolbar.title = "Remiserias"

        recyclerView = view.findViewById(R.id.recycle_list_view_remiserias)
    }

    private fun setListenners() {

        // Toolbar
        toolbar.setNavigationOnClickListener { activity?.finish() }
    }

    private fun loadRemiserias(): List<Remiseria> {

        return listOf(
            Remiseria("Servi-mas Remises y Fletes", "Belgrano 465",
            "03442427777,03442427984,03442429170,03442433029"),

            Remiseria("Remises Acción", "Belgrano 674",
            "03442428888,03442422090,03442641200"),

            Remiseria("Remises Tao", "Bv. Montoneras 143",
            "03442432542"),

            Remiseria("Remises Concepción", "J.J Millán 634",
            "03442431212,03442431515"),

            Remiseria("Remises Correcaminos", "Rocamora 1256",
                "03442433233,03442431387"),

            Remiseria("Radio Taxi", "Galarza 1282",
                "03442422020,03442422727,03442427010"),

            Remiseria("Remis Rio", "Bv. Díaz Vélez 870",
                "03442443201,03442443344")
        )
    }

    companion object {
        @JvmStatic
        fun newInstance() = RemiseriasFragment()
    }
}
