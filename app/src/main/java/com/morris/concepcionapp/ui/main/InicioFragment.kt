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

    private var buscarInput: TextInputEditText? = null
    private var buttonAlmacen: Button? = null
    private var buttonCarniceria: Button? = null
    private var buttonFarmacia: Button? = null
    private var buttonFerreteria: Button? = null
    private var buttonGastronomia: Button? = null
    private var buttonLimpieza: Button? = null
    private var buttonOtro: Button? = null
    private var buttonPanaderia: Button? = null
    private var buttonVerduleria: Button? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Set theme
        context?.theme?.applyStyle(R.style.AppThemeInicio, true)

        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_inicio, container, false)

        // Inicio las vistas
        initViews(view)

        // Seteo los eventos de click
        setListeners()

        return view
    }

    private fun initViews(view: View) {
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
        buttonAlmacen!!.setOnClickListener(this)
        buttonCarniceria!!.setOnClickListener(this)
        buttonFarmacia!!.setOnClickListener(this)
        buttonFerreteria!!.setOnClickListener(this)
        buttonGastronomia!!.setOnClickListener(this)
        buttonLimpieza!!.setOnClickListener(this)
        buttonOtro!!.setOnClickListener(this)
        buttonPanaderia!!.setOnClickListener(this)
        buttonVerduleria!!.setOnClickListener(this)

        buscarInput?.setOnEditorActionListener { _, action, _ ->
            var handled = false
            if (action == EditorInfo.IME_ACTION_SEARCH) {
                // Aca ejecuto el metodo necesario
                buscarTextInput()
                handled = true
            }
            handled
        }
    }

    // Cuando busco por medio de una categoria
    override fun onClick(v: View?) {
        var intent = Intent(activity, BuscarActivity::class.java)

        val button: Button = v!!.findViewById<Button>(v.id)

        intent.putExtra("busqueda", button.text.toString())
        intent.putExtra("tipo", button.text.toString())

        startActivity(intent)
    }

    // Cuando busco por medio del TextEdit
    private fun buscarTextInput() {
        var busqueda: String = buscarInput?.text.toString()

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
