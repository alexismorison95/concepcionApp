package com.morris.concepcionapp.ui.main

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.morris.concepcionapp.R
import com.squareup.picasso.Picasso
import java.util.*

class ComercianteActivity : AppCompatActivity() {

    private lateinit var toolbar: Toolbar
    private lateinit var imageView: ImageView

    // Auth
    private lateinit var usuario: FirebaseUser
    private val RC_SIGN_IN = 123

    // TextEdits
    private lateinit var formularioCuit: TextInputEditText
    private lateinit var formularioNombre: TextInputEditText
    private lateinit var formularioOtro: TextInputEditText
    private lateinit var formularioHorario: TextInputEditText
    private lateinit var formularioNumero: TextInputEditText
    private lateinit var formularioWhatsapp: TextInputEditText
    private lateinit var formularioMail: TextInputEditText
    private lateinit var formularioDireccion: TextInputEditText

    // Buttons
    private lateinit var btnCargarFoto: Button
    private lateinit var btnGuardar: Button

    // RadioButtons
    private lateinit var radioGroupCategoria: RadioGroup
    private lateinit var radioGroupPersonal: RadioGroup
    private lateinit var radioButtonCategoria: RadioButton
    private lateinit var radioButtonPersonal: RadioButton

    override fun onCreate(savedInstanceState: Bundle?) {
        // Theme
        setTheme(R.style.AppThemeInicio)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comerciante)

        setViews()

        authGoogle()

        setListeners()
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

    private fun setViews() {
        // Toolbar
        toolbar = findViewById(R.id.toolbarComerciante)
        toolbar.setNavigationOnClickListener { this.finish() }

        // Buttons
        btnGuardar = findViewById(R.id.btn_guardar)
        btnCargarFoto = findViewById(R.id.btn_cargar_foto)

        // RadioButtons
        radioGroupCategoria = findViewById(R.id.radio_group_categoria)
        radioGroupPersonal = findViewById(R.id.radio_group_personal)

        // TextEdits
        formularioOtro = findViewById(R.id.rgc_otro_explicacion)
        formularioCuit = findViewById(R.id.rgc_otro_explicacion)
        formularioNombre = findViewById(R.id.rgc_otro_explicacion)
        formularioOtro = findViewById(R.id.rgc_otro_explicacion)
        formularioHorario = findViewById(R.id.rgc_otro_explicacion)
        formularioNumero = findViewById(R.id.rgc_otro_explicacion)
        formularioWhatsapp = findViewById(R.id.rgc_otro_explicacion)
        formularioMail = findViewById(R.id.rgc_otro_explicacion)
        formularioDireccion = findViewById(R.id.rgc_otro_explicacion)

        // Foto
        imageView = findViewById(R.id.foto_comerciante)
    }

    private fun setListeners() {
        // Foto
        btnCargarFoto.setOnClickListener {
            val intent = Intent().setType("image/*").setAction(Intent.ACTION_GET_CONTENT)

            startActivityForResult(Intent.createChooser(intent, "Seleccionar imagen"), 111)
        }

        // Guardar formulario
        btnGuardar.setOnClickListener {
            validarFormulario()
        }

        // Radio Groups Categoria
        radioGroupCategoria.setOnCheckedChangeListener { _, checkedId ->
            radioButtonCategoria = findViewById(checkedId)

            if (radioButtonCategoria.id == R.id.rgc_otro) {
                formularioOtro.isFocusableInTouchMode = true
                formularioOtro.requestFocus()
            }

            Toast.makeText(applicationContext,"On checked change :${radioButtonCategoria.text}", Toast.LENGTH_SHORT).show()
        }

        // Radio Groups Personal
        radioGroupPersonal.setOnCheckedChangeListener { _, checkedId ->
            radioButtonPersonal = findViewById(checkedId)

            Toast.makeText(applicationContext,"On checked change :${radioButtonPersonal.text}", Toast.LENGTH_SHORT).show()
        }
    }

    private fun validarFormulario() {
        // Validaciones
        if (radioButtonCategoria.id == -1 &&
            radioButtonPersonal.id == -1 &&
            formularioCuit.text.isNullOrBlank() &&
            formularioNombre.text.isNullOrBlank() &&
            formularioDireccion.text.isNullOrBlank() &&
            formularioHorario.text.isNullOrBlank() &&
            formularioMail.text.isNullOrBlank() &&
            formularioNumero.text.isNullOrBlank() &&
            formularioWhatsapp.text.isNullOrBlank()
        ) {
            Toast.makeText(applicationContext,"Debe completar todos los campos", Toast.LENGTH_LONG).show()
        }
        else {
            guardarFormulario()
        }
    }

    private fun guardarFormulario() {

        // Guardar en Firebase

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Activity elegir foto
        if (requestCode == 111 && resultCode == Activity.RESULT_OK) {

            Picasso.get().load(data?.data)
                .error(R.drawable.ic_google_downasaur)
                .into(imageView)
        }

        // Activity AUTH
        if (requestCode == RC_SIGN_IN) {
            val response = IdpResponse.fromResultIntent(data)

            if (resultCode == Activity.RESULT_OK) {
                // Successfully signed in
                usuario = FirebaseAuth.getInstance().currentUser!!

                toolbar.title = usuario.displayName?.toUpperCase(Locale.ROOT)
            }
            else {
                // Sign in failed.
                Toast.makeText(applicationContext, response?.error?.message, Toast.LENGTH_LONG).show()
                this.finish()
            }
        }


    }
}
