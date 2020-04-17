package com.morris.concepcionapp.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.FrameLayout
import com.morris.concepcionapp.R
import com.morris.concepcionapp.ui.main.fragments.*
import kotlinx.android.synthetic.main.toolbar.*

class GeneralActivity : AppCompatActivity() {

    private var paramFragment: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_general)

        // Get params
        paramFragment = intent.getStringExtra("FrangmentName")

        // Esto es para que mantenga el estado de los fragments al cambiar la orientacion de la pantalla
        if (savedInstanceState == null) {

            loadFragment()
        }
    }

    private fun loadFragment() {

        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()

        when (paramFragment) {

            "InfoFragment" -> {

                val fragment = InfoFragment.newInstance()

                fragmentTransaction.add(R.id.contenedor, fragment)
                fragmentTransaction.commit()
            }
            "ComercianteFragment" -> {

                val fragment = ComercianteFragment.newInstance()

                fragmentTransaction.add(R.id.contenedor, fragment)
                fragmentTransaction.commit()
            }
            "EmergenciasFragment" -> {

                val fragment = EmergenciasFragment.newInstance()

                fragmentTransaction.add(R.id.contenedor, fragment)
                fragmentTransaction.commit()
            }
            "RemiseriasFragment" -> {

                val fragment = RemiseriasFragment.newInstance()

                fragmentTransaction.add(R.id.contenedor, fragment)
                fragmentTransaction.commit()
            }
            "ReporteErrorFragment" -> {

                val fragment = ReporteErrorFragment.newInstance()

                fragmentTransaction.add(R.id.contenedor, fragment)
                fragmentTransaction.commit()
            }
        }
    }
}
