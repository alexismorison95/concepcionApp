package com.morris.concepcionapp.ui.main

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.marginBottom
import com.google.android.material.textfield.TextInputEditText

import com.morris.concepcionapp.R
import kotlinx.android.synthetic.*

// TODO: En el futuro estas categorias se traen desde un servidor
private val categorias = arrayOf(
    "Almacén",
    "Carnicería",
    "Farmacia",
    "Ferretería",
    "Gastronomía",
    "Limpieza",
    "Otro",
    "Panadería",
    "Verdulería"
)

/**
 * A simple [Fragment] subclass.
 * Use the [InicioFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class InicioFragment : Fragment() {

    private var buscarInput: TextInputEditText? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Set theme
        context?.theme?.applyStyle(R.style.AppThemeInicio, true)

        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_inicio, container, false)

        loadCategorias(view)

        buscarInput = view.findViewById<TextInputEditText>(R.id.buscar_input)

        return view
    }

    override fun onResume() {
        super.onResume()

        // Para busccar por medio del TextInput
        buscarInput?.setOnEditorActionListener { textView, action, keyEvent ->
            var handled = false
            if (action == EditorInfo.IME_ACTION_SEARCH) {
                // Aca ejecuto el metodo necesario
                loadBuscarActivity()
                println(buscarInput!!.text)

                handled = true
            }
            handled
        }
    }

    private fun loadCategorias(view: View) {
        val categoriasLayout = view.findViewById<LinearLayout>(R.id.listaCategoriasLayout)

        // set categorias
        for (categoria in categorias) {

            val button = Button(view.context)
            button.text = categoria
            button.setBackgroundColor(Color.TRANSPARENT)
            button.setTextColor(Color.rgb(33, 150, 243))
            //button.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_launcher_background, 0, 0, 0)

            val param = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 200)

            param.setMargins(0, 0, 0, 10)
            button.layoutParams = param

            // Busco por categoria
            button.setOnClickListener {
                val intent = Intent(activity, BuscarActivity::class.java)

                intent.putExtra("busqueda", categoria)
                intent.putExtra("tipo", categoria)

                startActivity(intent)
            }

            categoriasLayout.addView(button)
        }
    }

    // Se ejecuta cuando usas el buscador
    private fun loadBuscarActivity() {
        var busqueda = buscarInput?.text.toString()

        // Si escribio algo
        if (!busqueda.isNullOrBlank()) {

            val intent = Intent(activity, BuscarActivity::class.java)

            intent.putExtra("busqueda", busqueda)
            intent.putExtra("tipo", "todo")

            startActivity(intent)

        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = InicioFragment()
    }
}
