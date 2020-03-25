package com.morris.concepcionapp.ui.main

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
import com.morris.concepcionapp.R
import com.squareup.picasso.Picasso

class NegocioAdapter(private val list: List<Negocio>): RecyclerView.Adapter<NegocioViewHolder>() {

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

    // Para el toast
    val view = inflater.inflate(R.layout.negocio_view, parent, false)

    init {
        nombre = itemView.findViewById(R.id.negocio_nombre)
        horario = itemView.findViewById(R.id.negocio_horarios)
        whatsapp = itemView.findViewById(R.id.negocio_whatsapp)
        telefono = itemView.findViewById(R.id.negocio_telefono)
        descripcion = itemView.findViewById(R.id.negocio_descripcion)
        direccion = itemView.findViewById(R.id.negocio_direccion)
        imagen = itemView.findViewById(R.id.negocio_image)

        // onClickListeners
        whatsapp?.setOnClickListener {
            Toast.makeText(view.context, "You clicked on TextView 'WhatsApp'.", Toast.LENGTH_SHORT).show()
        }

        telefono?.setOnClickListener {
            Toast.makeText(view.context, "You clicked on TextView 'Telefono'.", Toast.LENGTH_SHORT).show()
        }

        direccion?.setOnClickListener {
            Toast.makeText(view.context, "You clicked on TextView 'Direccion'.", Toast.LENGTH_SHORT).show()
        }
    }

    fun bind(negocio: Negocio) {
        nombre?.text = negocio.nombre
        horario?.text = negocio.horario
        telefono?.text = negocio.telefono
        //imagen?.setImageURI(Uri.parse(negocio.imagen))
        Picasso.get().load(negocio.imagenURL).
            placeholder(R.drawable.progress_animation).
            error(R.drawable.ic_google_downasaur).
            resize(800, 600).centerCrop().into(imagen)

        val nuevoWhatsapp = "<u>" + negocio.whatsapp + "</u>"
        whatsapp?.text = HtmlCompat.fromHtml(nuevoWhatsapp, HtmlCompat.FROM_HTML_MODE_LEGACY)

        val nuevaDireccion = "<u>" + negocio.direccion + "</u>"
        direccion?.text = HtmlCompat.fromHtml(nuevaDireccion, HtmlCompat.FROM_HTML_MODE_LEGACY)

        val nuevaDescripcion= "<b>Descripción:</b> " + negocio.descripcion
        descripcion?.text = HtmlCompat.fromHtml(nuevaDescripcion, HtmlCompat.FROM_HTML_MODE_LEGACY)
    }
}