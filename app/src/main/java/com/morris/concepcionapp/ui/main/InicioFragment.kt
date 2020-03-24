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
    "Almacen",
    "Carniceria",
    "Gastronomia",
    "Farmacia",
    "Ferreteria",
    "Limpieza",
    "Panaderia",
    "Otro"
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
            button.setTextColor(Color.WHITE)

            val param = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 200)

            param.setMargins(0, 0, 0, 25)
            button.layoutParams = param

            categoriasLayout.addView(button)
        }
    }

    private fun loadBuscarActivity() {
        val intent = Intent(activity, BuscarActivity::class.java)
        intent.putExtra("busqueda", buscarInput?.text)
        intent.putExtra("tipo", "busqueda")

        startActivity(intent)
    }

    companion object {
        @JvmStatic
        fun newInstance() = InicioFragment()
    }
}
