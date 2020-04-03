package com.morris.concepcionapp.ui.main

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.widget.Toolbar
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.morris.concepcionapp.Funciones
import com.morris.concepcionapp.Negocio

import com.morris.concepcionapp.R
import com.squareup.picasso.Picasso
import java.io.ByteArrayOutputStream


class ComercianteFragment : Fragment() {

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
        activity?.setTheme(R.style.AppThemeComerciante)

        super.onCreate(savedInstanceState)
    }

    override fun onDestroy() {
        super.onDestroy()

        // Cierro la sesion de usuario actual
        AuthUI.getInstance()
            .signOut(activity!!.applicationContext)
            .addOnCompleteListener { }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_comerciante, container, false)

        setViews(view)

        authGoogle()

        setListeners(view)

        return view
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

    private fun setViews(view: View) {

        // Toolbar
        toolbar = view.findViewById(R.id.toolbar)

        // Progress Bar
        llProgressBar = view.findViewById(R.id.llProgrssBar)

        // Buttons
        btnGuardar = view.findViewById(R.id.btn_guardar)
        btnCargarFoto = view.findViewById(R.id.btn_cargar_foto)

        // RadioButtons
        radioGroupCategoria = view.findViewById(R.id.radio_group_categoria)
        radioGroupPersonal = view.findViewById(R.id.radio_group_personal)

        // TextEdits
        formularioCuit = view.findViewById(R.id.formulario_cuit)
        formularioNombre = view.findViewById(R.id.formulario_nombre)
        formularioHorario = view.findViewById(R.id.formulario_horarios)
        formularioNumero = view.findViewById(R.id.formulario_numero)
        formularioWhatsapp = view.findViewById(R.id.formulario_whatsapp)
        formularioMail = view.findViewById(R.id.formulario_mail)
        formularioDireccion = view.findViewById(R.id.formulario_direccion)
        formularioDescripcion = view.findViewById(R.id.formulario_descripcion)

        // Foto
        imageView = view.findViewById(R.id.foto_comerciante)
    }

    private fun setListeners(view: View) {

        // Toolbar
        toolbar.setNavigationOnClickListener { activity?.finish() }

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
                Toast.makeText(context,"Debe completar todos los campos", Toast.LENGTH_LONG).show()
                llProgressBar.visibility = View.GONE
            }
        }

        // Radio Groups Categoria
        radioGroupCategoria.setOnCheckedChangeListener { _, checkedId ->
            radioButtonCategoria = view.findViewById(checkedId)
        }

        // Radio Groups Personal
        radioGroupPersonal.setOnCheckedChangeListener { _, checkedId ->
            radioButtonPersonal = view.findViewById(checkedId)
        }
    }

    private fun validarFormulario() {

        // Validaciones
        if (!radioButtonCategoria?.isChecked!! ||
            !radioButtonPersonal?.isChecked!! ||
            formularioCuit.text.isNullOrBlank() ||
            formularioNombre.text.isNullOrBlank() ||
            formularioDireccion.text.isNullOrBlank() ||
            formularioHorario.text.isNullOrBlank() ||
            formularioMail.text.isNullOrBlank() ||
            formularioNumero.text.isNullOrBlank() ||
            formularioWhatsapp.text.isNullOrBlank() ||
            formularioDescripcion.text.isNullOrBlank()
        ) {
            Toast.makeText(context,"Debe completar todos los campos", Toast.LENGTH_LONG).show()
        }
        else {
            // Si todos los campos estan llenos, subo la foto
            subirFoto()
        }
    }

    private fun subirFoto() {

        // Muestro el progress bar
        llProgressBar.visibility = View.VISIBLE

        // Me conecto con firebase
        val storage = FirebaseStorage.getInstance("gs://concepcionapp-803a6.appspot.com")

        // Create a storage reference from our app
        val storageRef = storage.reference

        // Create a reference to my file
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

        uploadTask.continueWithTask { task ->

            if (!task.isSuccessful) {
                task.exception?.let {
                    throw it
                }
            }
            // Para obtener la url
            imagenRef.downloadUrl
        }.addOnCompleteListener { task ->

            if (task.isSuccessful) {
                // La imagen se subio, obtengo la URL
                imagenURL = task.result.toString()

                guardarFormulario()
            } else {
                // Handle failures
                Toast.makeText(context, "No se pudo subir la imagen", Toast.LENGTH_LONG).show()
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

                Toast.makeText(context, "Sus datos se guardaron con éxito", Toast.LENGTH_LONG).show()

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

                toolbar.title = usuario.displayName?.let { Funciones.createTitle(it) }
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
        fun newInstance() = ComercianteFragment()
    }
}
