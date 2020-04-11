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
import com.morris.concepcionapp.models.Remiseria
import kotlin.Exception

class RemiseriaAdapter(private val list: List<Remiseria>): RecyclerView.Adapter<RemiseriaViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RemiseriaViewHolder {

        val inflater = LayoutInflater.from(parent.context)

        return RemiseriaViewHolder(
            inflater,
            parent
        )
    }

    override fun getItemCount(): Int {

        return list.size
    }

    override fun onBindViewHolder(holder: RemiseriaViewHolder, position: Int) {

        val remiseria = list[position]
        holder.bind(remiseria)
    }
}

class RemiseriaViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
    RecyclerView.ViewHolder(inflater.inflate(R.layout.remiseria_view, parent, false)) {

    private lateinit var btnRemiseria: Button
    private lateinit var layoutTel: LinearLayout
    private lateinit var direccion: TextView
    private lateinit var layoutVG: LinearLayout
    private lateinit var telefonoTV: TextView


    private val view = inflater.inflate(R.layout.remiseria_view, parent, false)

    // Vinculo las vistas con los datos
    init {
        setView()

        setListeners()
    }

    private fun setView() {

        btnRemiseria = itemView.findViewById(R.id.remiseria_btn)
        layoutTel = itemView.findViewById(R.id.remiseria_layout_tel)
        direccion = itemView.findViewById(R.id.remiseria_dir)
        layoutVG = itemView.findViewById(R.id.layoutVG)
        telefonoTV = itemView.findViewById(R.id.remiseria_tel_tv)
    }

    private fun setListeners() {

        btnRemiseria.setOnClickListener {

            if (layoutVG.visibility == View.GONE) {

                btnRemiseria.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.ic_expand_less_black_24dp, 0)
                layoutVG.visibility = View.VISIBLE
            }
            else {

                btnRemiseria.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.ic_expand_more_black_24dp, 0)
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

    fun bind(remiseria: Remiseria) {

        btnRemiseria.text = remiseria.nombre

        val nuevaDir = "<u>${remiseria.direccion}</u>"
        direccion.text = HtmlCompat.fromHtml(nuevaDir, HtmlCompat.FROM_HTML_MODE_LEGACY)

        val telefonos = remiseria.telefonos!!.split(',')

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

            if (telefonos.size > 1) {

                telefonoTV.text = "Teléfonos:"
            }

            layoutTel.addView(tel)
        }
    }
}
