package com.morris.concepcionapp.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import com.morris.concepcionapp.R

class ComercianteActivity : AppCompatActivity() {

    private lateinit var toolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        // Theme
        setTheme(R.style.AppThemeInicio)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comerciante)

        // Toolbar
        toolbar = findViewById(R.id.toolbarComerciante)
        toolbar.setNavigationOnClickListener { this.finish() }
    }
}
