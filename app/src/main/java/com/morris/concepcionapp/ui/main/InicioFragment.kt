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

/**
 * A simple [Fragment] subclass.
 * Use the [InicioFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class InicioFragment : Fragment(), View.OnClickListener {

    private lateinit var buscarInput: TextInputEditText
    private lateinit var buttonAlmacen: Button
    private lateinit var buttonCarniceria: Button
    private lateinit var buttonFarmacia: Button
    private lateinit var buttonFerreteria: Button
    private lateinit var buttonGastronomia: Button
    private lateinit var buttonLimpieza: Button
    private lateinit var buttonOtro: Button
    private lateinit var buttonPanaderia: Button
    private lateinit var buttonVerduleria: Button


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        // Set theme
        context?.theme?.applyStyle(R.style.AppThemeInicio, true)

        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_inicio, container, false)

        setViews(view)

        setListeners()

        return view
    }

    private fun setViews(view: View) {

        buscarInput = view.findViewById(R.id.buscar_input)

        buttonAlmacen = view.findViewById(R.id.button_almacen)
        buttonCarniceria = view.findViewById(R.id.button_carniceria)
        buttonFarmacia = view.findViewById(R.id.button_farmacia)
        buttonFerreteria = view.findViewById(R.id.button_ferreteria)
        buttonGastronomia = view.findViewById(R.id.button_gastronomia)
        buttonLimpieza = view.findViewById(R.id.button_limpieza)
        buttonOtro = view.findViewById(R.id.button_otro)
        buttonPanaderia = view.findViewById(R.id.button_panaderia)
        buttonVerduleria = view.findViewById(R.id.button_verduleria)
    }

    private fun setListeners() {

        buttonAlmacen.setOnClickListener(this)
        buttonCarniceria.setOnClickListener(this)
        buttonFarmacia.setOnClickListener(this)
        buttonFerreteria.setOnClickListener(this)
        buttonGastronomia.setOnClickListener(this)
        buttonLimpieza.setOnClickListener(this)
        buttonOtro.setOnClickListener(this)
        buttonPanaderia.setOnClickListener(this)
        buttonVerduleria.setOnClickListener(this)

        buscarInput.setOnEditorActionListener { _, action, _ ->

            var handled = false

            if (action == EditorInfo.IME_ACTION_SEARCH) {

                buscarTextInput()
                handled = true
            }

            handled
        }
    }

    // Cuando busco por medio de una categoria
    override fun onClick(v: View?) {

        val intent = Intent(activity, BuscarActivity::class.java)

        val buttonSeleccionado: Button = v!!.findViewById(v.id)

        intent.putExtra("busqueda", buttonSeleccionado.text.toString())
        intent.putExtra("tipo", buttonSeleccionado.text.toString())

        startActivity(intent)
    }

    // Cuando busco por medio del TextEdit
    private fun buscarTextInput() {

        val busqueda: String = buscarInput.text.toString()

        if (!busqueda.isBlank()) {

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
