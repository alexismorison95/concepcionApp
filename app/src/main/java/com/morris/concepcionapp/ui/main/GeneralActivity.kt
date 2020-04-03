package com.morris.concepcionapp.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.FrameLayout
import com.morris.concepcionapp.R

class GeneralActivity : AppCompatActivity() {

    private lateinit var contenedor: FrameLayout

    private var paramFragment: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_general)

        // Get params
        paramFragment = intent.getStringExtra("FrangmentName")

        loadFragment()
    }

    private fun loadFragment() {

        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()

        when (paramFragment) {

            "InfoFragment" -> {

                val fragment = InfoFragment()

                fragmentTransaction.add(R.id.contenedor, fragment)
                fragmentTransaction.commit()
            }
            else -> {

                val fragment = ComercianteFragment()

                fragmentTransaction.add(R.id.contenedor, fragment)
                fragmentTransaction.commit()
            }
        }

//        if (paramFragment == "InfoFragment") {
//
//            val fragment = InfoFragment()
//
//            fragmentTransaction.add(R.id.contenedor, fragment)
//            fragmentTransaction.commit()
//        }
//
//        if (paramFragment == "ComercianteFragment") {
//
//            val fragment = ComercianteFragment()
//
//            fragmentTransaction.add(R.id.contenedor, fragment)
//            fragmentTransaction.commit()
//        }

    }
}
