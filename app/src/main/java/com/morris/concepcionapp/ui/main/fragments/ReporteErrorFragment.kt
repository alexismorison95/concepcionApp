package com.morris.concepcionapp.ui.main.fragments

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.morris.concepcionapp.Funciones

import com.morris.concepcionapp.R
import com.morris.concepcionapp.models.Reporte
import com.squareup.picasso.Picasso
import java.text.SimpleDateFormat
import java.util.*

class ReporteErrorFragment : Fragment() {

    private lateinit var toolbar: Toolbar
    private lateinit var descripcionReporte: TextInputEditText
    private lateinit var btnEnviar: Button
    private lateinit var llProgressBar: LinearLayout

    // Auth
    private lateinit var usuario: FirebaseUser
    private val RC_SIGN_IN = 123


    override fun onCreate(savedInstanceState: Bundle?) {

        // Theme
        activity?.setTheme(R.style.AppTheme)

        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_reporte_error, container, false)

        setViews(view)

        setListenners()

        return view
    }

    private fun setViews(view: View) {

        // Toolbar
        toolbar = view.findViewById(R.id.toolbar)
        toolbar.title = "Reportar error"

        // inputs
        descripcionReporte = view.findViewById(R.id.reporte_descripcion)

        // button
        btnEnviar = view.findViewById(R.id.reporte_btn_enviar)

        // Progress Bar
        llProgressBar = view.findViewById(R.id.llProgrssBar)
    }

    private fun setListenners() {

        // Toolbar
        toolbar.setNavigationOnClickListener { activity?.finish() }

        // Btn enviar
        btnEnviar.setOnClickListener {

            validarCampos()
        }
    }

    private fun authGoogle() {

        // Array con las formas disponibles para iniciar sesion
        val providers = arrayListOf(
            AuthUI.IdpConfig.GoogleBuilder().build()
        )

        // Inicio la activity AuthUI
        startActivityForResult(
            AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .build(),
            RC_SIGN_IN
        )
    }

    private fun validarCampos() {

        if (descripcionReporte.text.isNullOrBlank()) {

            Toast.makeText(context, "Debe escribir una descripción", Toast.LENGTH_SHORT).show()
        }
        else {

            if (descripcionReporte.text!!.length < 19) {

                Toast.makeText(context, "Debe escribir una descripción más extensa", Toast.LENGTH_SHORT).show()
            }
            else {

                authGoogle()
            }
        }
    }

    private fun Date.toString(format: String, locale: Locale = Locale.getDefault()): String {
        val formatter = SimpleDateFormat(format, locale)
        return formatter.format(this)
    }

    private fun guardarReporte() {

        llProgressBar.visibility = View.VISIBLE

        val db = FirebaseFirestore.getInstance()

        val calendar = Calendar.getInstance()
        val fechaHora = calendar.time.toString("dd-MM-yyyy - HH:mm")

        val reporte = Reporte(
            descripcionReporte.text.toString().toLowerCase(),
            fechaHora,
            usuario.uid,
            usuario.displayName.toString(),
            usuario.email.toString(),
            usuario.phoneNumber.toString(),
            false
        )

        db.collection("reportes")
            .add(reporte)
            .addOnCompleteListener {

                // Se subio la coleccion, detengo el progress bar
                llProgressBar.visibility = View.GONE

                Toast.makeText(context, "Su reporte se guardó con éxito", Toast.LENGTH_LONG).show()

                // Cierro la sesion de usuario actual
                AuthUI.getInstance()
                    .signOut(activity!!.applicationContext)
                    .addOnCompleteListener {
                        activity!!.finish()
                    }
            }
            .addOnFailureListener { e ->
                Toast.makeText(context, e.message, Toast.LENGTH_LONG).show()
            }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Activity AUTH
        if (requestCode == RC_SIGN_IN) {

            val response = IdpResponse.fromResultIntent(data)

            if (resultCode == Activity.RESULT_OK) {

                // Successfully signed in
                usuario = FirebaseAuth.getInstance().currentUser!!

                guardarReporte()
            }
            else {

                // Sign in failed.
                // Cierro la sesion de usuario actual
                AuthUI.getInstance()
                    .signOut(activity!!.applicationContext)
                    .addOnCompleteListener {
                        activity!!.finish()
                    }
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = ReporteErrorFragment()
    }
}
