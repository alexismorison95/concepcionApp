package com.morris.concepcionapp.ui.main

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.morris.concepcionapp.Negocio
import com.morris.concepcionapp.R
import com.squareup.picasso.Picasso
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.util.*

class ComercianteActivity : AppCompatActivity() {

    private lateinit var toolbar: Toolbar
    private lateinit var imageView: ImageView
    private lateinit var llProgressBar: LinearLayout
    private lateinit var imagenURL: String

    // Auth
    private lateinit var usuario: FirebaseUser
    private val RC_SIGN_IN = 123

    // TextEdits
    private lateinit var formularioCuit: TextInputEditText
    private lateinit var formularioNombre: TextInputEditText
    private lateinit var formularioHorario: TextInputEditText
    private lateinit var formularioNumero: TextInputEditText
    private lateinit var formularioWhatsapp: TextInputEditText
    private lateinit var formularioMail: TextInputEditText
    private lateinit var formularioDireccion: TextInputEditText
    private lateinit var formularioDescripcion: TextInputEditText

    // Buttons
    private lateinit var btnCargarFoto: Button
    private lateinit var btnGuardar: Button

    // RadioButtons
    private lateinit var radioGroupCategoria: RadioGroup
    private lateinit var radioGroupPersonal: RadioGroup
    private var radioButtonCategoria: RadioButton? = null
    private var radioButtonPersonal: RadioButton? = null

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

        // Progress Bar
        llProgressBar = findViewById(R.id.llProgrssBar)

        // Buttons
        btnGuardar = findViewById(R.id.btn_guardar)
        btnCargarFoto = findViewById(R.id.btn_cargar_foto)

        // RadioButtons
        radioGroupCategoria = findViewById(R.id.radio_group_categoria)
        radioGroupPersonal = findViewById(R.id.radio_group_personal)

        // TextEdits
        formularioCuit = findViewById(R.id.formulario_cuit)
        formularioNombre = findViewById(R.id.formulario_nombre)
        formularioHorario = findViewById(R.id.formulario_horarios)
        formularioNumero = findViewById(R.id.formulario_numero)
        formularioWhatsapp = findViewById(R.id.formulario_whatsapp)
        formularioMail = findViewById(R.id.formulario_mail)
        formularioDireccion = findViewById(R.id.formulario_direccion)
        formularioDescripcion = findViewById(R.id.formulario_descripcion)

        // Foto
        imageView = findViewById(R.id.foto_comerciante)
    }

    private fun setListeners() {

        // Toolbar
        toolbar.setNavigationOnClickListener { this.finish() }

        // Foto
        btnCargarFoto.setOnClickListener {
            val intent = Intent().setType("image/*").setAction(Intent.ACTION_GET_CONTENT)

            startActivityForResult(Intent.createChooser(intent, "Seleccionar imagen"), 111)
        }

        // Guardar formulario
        btnGuardar.setOnClickListener {
            try {
                validarFormulario()
            }
            catch (e: Exception) {
                Toast.makeText(applicationContext,"Debe completar todos los campos", Toast.LENGTH_LONG).show()
            }
        }

        // Radio Groups Categoria
        radioGroupCategoria.setOnCheckedChangeListener { _, checkedId ->
            radioButtonCategoria = findViewById(checkedId)
        }

        // Radio Groups Personal
        radioGroupPersonal.setOnCheckedChangeListener { _, checkedId ->
            radioButtonPersonal = findViewById(checkedId)
        }
    }

    private fun validarFormulario() {
        // Validaciones
        if (!radioButtonCategoria!!.isChecked &&
            !radioButtonPersonal!!.isChecked  &&
            formularioCuit.text.isNullOrBlank() &&
            formularioNombre.text.isNullOrBlank() &&
            formularioDireccion.text.isNullOrBlank() &&
            formularioHorario.text.isNullOrBlank() &&
            formularioMail.text.isNullOrBlank() &&
            formularioNumero.text.isNullOrBlank() &&
            formularioWhatsapp.text.isNullOrBlank() &&
            formularioDescripcion.text.isNullOrBlank()
        ) {
            Toast.makeText(applicationContext,"Debe completar todos los campos", Toast.LENGTH_LONG).show()
        }
        else {
            // Si todos los campos estan llenos, subo la foto
            subirFoto()
        }
    }

    private fun subirFoto() {

        // Muestro el progress bar
        llProgressBar.visibility = View.VISIBLE

        // Guardar foto
        var storage = FirebaseStorage.getInstance("gs://concepcionapp-803a6.appspot.com")

        // Create a storage reference from our app
        val storageRef = storage.reference

        // Create a reference to "mountains.jpg"
        val nombreArch = formularioNombre.text.toString()
        val imagenRef = storageRef.child("$nombreArch.jpg")

        // Get the data from an ImageView as bytes
        imageView.isDrawingCacheEnabled = true
        imageView.buildDrawingCache()

        val bitmap = (imageView.drawable as BitmapDrawable).bitmap
        val baos = ByteArrayOutputStream()

        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)

        val data = baos.toByteArray()

        val uploadTask = imagenRef.putBytes(data)

        val urlTask = uploadTask.continueWithTask { task ->
            if (!task.isSuccessful) {
                task.exception?.let {
                    throw it
                }
            }
            imagenRef.downloadUrl
        }.addOnCompleteListener { task ->

            if (task.isSuccessful) {
                // La imagen se subio, obtengo la URL
                imagenURL = task.result.toString()

                guardarFormulario()

            } else {
                // Handle failures
                Toast.makeText(applicationContext, "No se pudo subir la imagen", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun guardarFormulario() {

        // Access a Cloud Firestore instance from your Activity
        val db = FirebaseFirestore.getInstance()

        // Creo el objeto negocio
        val negocio = Negocio(formularioNombre.text.toString(),
                                formularioHorario.text.toString(),
                                formularioWhatsapp.text.toString(),
                                formularioNumero.text.toString(),
                                formularioDescripcion.text.toString(),
                                "${formularioDireccion.text.toString()}, Concepción del Uruguay, Entre Ríos",
                                imagenURL,
                                radioButtonCategoria?.text.toString().toLowerCase(),
                                formularioCuit.text.toString(),
                                radioButtonPersonal?.text.toString().toLowerCase(),
                                usuario.uid,
                                usuario.displayName.toString(),
                                usuario.email.toString(),
                                usuario.phoneNumber.toString(),
                                false
        )

        db.collection("negocios")
            .add(negocio)
            .addOnSuccessListener {

                // Se subio la coleccion, detengo el progress bar
                llProgressBar.visibility = View.GONE

                Toast.makeText(applicationContext, "Sus datos se guardaron con exito", Toast.LENGTH_LONG).show()

                // Cierro la sesion de usuario actual
                AuthUI.getInstance()
                    .signOut(this)
                    .addOnCompleteListener {
                        this.finish()
                    }
            }
            .addOnFailureListener { e ->
                Toast.makeText(applicationContext, e.message, Toast.LENGTH_LONG).show()
            }
    }

    @RequiresApi(Build.VERSION_CODES.Q)
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

                toolbar.title = usuario.displayName?.let { createTitle(it) }
            }
            else {
                // Sign in failed.
                // Cierro la sesion de usuario actual
                AuthUI.getInstance()
                    .signOut(this)
                    .addOnCompleteListener {
                        this.finish()
                    }

                //this.finish()
            }
        }


    }

    private fun createTitle(nombre: String): String {
        val aux = nombre.split(" ")
        var titulo = ""

        for (palabra in aux) {
            titulo += palabra.capitalize() + " "
        }

        return titulo
    }
}
