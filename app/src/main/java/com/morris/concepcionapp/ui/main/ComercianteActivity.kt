package com.morris.concepcionapp.ui.main

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.widget.Toolbar
import com.morris.concepcionapp.R
import com.squareup.picasso.Picasso

class ComercianteActivity : AppCompatActivity() {

    private lateinit var toolbar: Toolbar
    private lateinit var btnCargarFoto: Button
    private lateinit var imageView: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        // Theme
        setTheme(R.style.AppThemeInicio)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comerciante)

        // Toolbar
        toolbar = findViewById(R.id.toolbarComerciante)
        toolbar.setNavigationOnClickListener { this.finish() }

        // Foto
        btnCargarFoto = findViewById(R.id.btn_cargar_foto)
        imageView = findViewById(R.id.foto_comerciante)

        btnCargarFoto.setOnClickListener {
            val intent = Intent().setType("image/*").setAction(Intent.ACTION_GET_CONTENT)

            startActivityForResult(Intent.createChooser(intent, "Seleccionar imagen"), 111)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 111 && resultCode == Activity.RESULT_OK) {

            Picasso.get().load(data?.data)
                .error(R.drawable.ic_google_downasaur)
                .into(imageView)
        }
    }
}
