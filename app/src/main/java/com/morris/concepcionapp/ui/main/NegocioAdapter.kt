package com.morris.concepcionapp.ui.main

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.Image
import android.net.Uri
import android.os.Build
import android.text.Html
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.text.HtmlCompat
import androidx.recyclerview.widget.RecyclerView
import com.morris.concepcionapp.Negocio
import com.morris.concepcionapp.R
import com.squareup.picasso.Picasso
import kotlin.Exception

class NegocioAdapter(private val list: MutableList<Negocio>): RecyclerView.Adapter<NegocioViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NegocioViewHolder {
        var inflater = LayoutInflater.from(parent.context)

        return NegocioViewHolder(inflater, parent)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: NegocioViewHolder, position: Int) {
        val negocio: Negocio = list[position]

        holder.bind(negocio)
    }
}

class NegocioViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
        RecyclerView.ViewHolder(inflater.inflate(R.layout.negocio_view, parent, false)) {

    private var nombre: TextView? = null
    private var horario: TextView? = null
    private var whatsapp: TextView? = null
    private var telefono: TextView? = null
    private var descripcion: TextView? = null
    private var direccion: TextView? = null
    private var imagen: ImageView? = null

    // Para los intent
    private val view = inflater.inflate(R.layout.negocio_view, parent, false)

    // Vinculo las vistas con los datos
    init {
        setView()

        // onClickListeners
        whatsapp?.setOnClickListener {
            try {
                // Ruta para WhatsApp
                val msjTo = "https://api.whatsapp.com/send?phone=" + whatsapp!!.text.toString()

                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse(msjTo)
                view.context.startActivity(intent)
            }
            catch (e: Exception) {
                Toast.makeText(view.context, e.message, Toast.LENGTH_SHORT).show()
            }
        }

        direccion?.setOnClickListener {
            try {
                // Ruta para Google Maps
                val map = "http://maps.google.co.in/maps?q=" + direccion!!.text.toString() + ", Concepcion del uruguay, Entre Rios"

                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(map))
                view.context.startActivity(intent)
            }
            catch (e: Exception) {
                Toast.makeText(view.context, e.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setView() {
        nombre = itemView.findViewById(R.id.negocio_nombre)
        horario = itemView.findViewById(R.id.negocio_horarios)
        whatsapp = itemView.findViewById(R.id.negocio_whatsapp)
        telefono = itemView.findViewById(R.id.negocio_telefono)
        descripcion = itemView.findViewById(R.id.negocio_descripcion)
        direccion = itemView.findViewById(R.id.negocio_direccion)
        imagen = itemView.findViewById(R.id.negocio_image)
    }

    fun bind(negocio: Negocio) {
        nombre?.text = negocio.nombre?.capitalize()
        horario?.text = negocio.horario?.capitalize()
        telefono?.text = negocio.telefono
        descripcion?.text = negocio.descripcion?.capitalize()

        Picasso.get().load(negocio.imagenURL).
            placeholder(R.drawable.progress_animation).
            error(R.drawable.ic_google_downasaur).
            fit().centerCrop().into(imagen)

        val nuevoWhatsapp = "<u>" + negocio.whatsapp + "</u>"
        whatsapp?.text = HtmlCompat.fromHtml(nuevoWhatsapp, HtmlCompat.FROM_HTML_MODE_LEGACY)

        val direccionArray = negocio.direccion!!.split(",")
        val nuevaDireccion = "<u>" + direccionArray[0].capitalize() + "</u>"
        direccion?.text = HtmlCompat.fromHtml(nuevaDireccion, HtmlCompat.FROM_HTML_MODE_LEGACY)
    }
}