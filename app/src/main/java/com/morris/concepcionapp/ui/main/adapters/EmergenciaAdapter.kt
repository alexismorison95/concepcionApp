package com.morris.concepcionapp.ui.main.adapters

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.text.method.LinkMovementMethod
import android.util.DisplayMetrics
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.app.ActivityCompat
import androidx.core.text.HtmlCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.morris.concepcionapp.R
import com.morris.concepcionapp.models.Emergencia
import kotlin.Exception

class EmergenciaAdapter(private val list: List<Emergencia>): RecyclerView.Adapter<EmergenciaViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmergenciaViewHolder {

        val inflater = LayoutInflater.from(parent.context)

        return EmergenciaViewHolder(
            inflater,
            parent
        )
    }

    override fun getItemCount(): Int {

        return list.size
    }

    override fun onBindViewHolder(holder: EmergenciaViewHolder, position: Int) {

        val emergencia = list[position]
        holder.bind(emergencia)
    }
}

class EmergenciaViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
    RecyclerView.ViewHolder(inflater.inflate(R.layout.emergencia_view, parent, false)) {

    private lateinit var btnEmergencia: Button
    private lateinit var layoutTel: LinearLayout
    private lateinit var direccion: TextView
    private lateinit var layoutVG: LinearLayout


    private val view = inflater.inflate(R.layout.emergencia_view, parent, false)

    // Vinculo las vistas con los datos
    init {
        setView()

        setListeners()
    }

    private fun setView() {

        btnEmergencia = itemView.findViewById(R.id.emergencia_btn)
        layoutTel = itemView.findViewById(R.id.emergencia_layout_tel)
        direccion = itemView.findViewById(R.id.emergencia_dir)
        layoutVG = itemView.findViewById(R.id.layoutVG)
    }

    private fun setListeners() {

        btnEmergencia.setOnClickListener {

            if (layoutVG.visibility == View.GONE) {

                btnEmergencia.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.ic_expand_less_black_24dp, 0)
                layoutVG.visibility = View.VISIBLE
            }
            else {

                btnEmergencia.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.ic_expand_more_black_24dp, 0)
                layoutVG.visibility = View.GONE
            }
        }

        direccion.setOnClickListener {

            try {
                val map = "http://maps.google.co.in/maps?q=${direccion.text}, Concepcion del uruguay, Entre Rios"

                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(map))

                view.context.startActivity(intent)
            }
            catch (e: Exception) {

                Toast.makeText(view.context, e.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun bind(emergencia: Emergencia) {

        btnEmergencia.text = emergencia.nombre

        val nuevaDir = "<u>${emergencia.direccion}</u>"
        direccion.text = HtmlCompat.fromHtml(nuevaDir, HtmlCompat.FROM_HTML_MODE_LEGACY)

        val telefonos = emergencia.telefonos!!.split(',')

        // Vacio el linear layout para volver a cargar los telefonos
        layoutTel.removeAllViews()

        for (telefono in telefonos) {

            val tel = TextView(itemView.context)

            val nuevotel = "<u>${telefono}</u>"
            tel.text = HtmlCompat.fromHtml(nuevotel, HtmlCompat.FROM_HTML_MODE_LEGACY)

            tel.setOnClickListener {

                try {

                    val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel: $telefono"))

                    view.context.startActivity(intent)
                }
                catch (e: Exception) {

                    Toast.makeText(view.context, e.message, Toast.LENGTH_SHORT).show()
                }
            }

            layoutTel.addView(tel)
        }
    }
}
