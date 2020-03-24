package com.morris.concepcionapp.ui.main

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.Image
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.morris.concepcionapp.R

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
    private var imagen: ImageView? = null

    init {
        nombre = itemView.findViewById(R.id.negocio_nombre)
        horario = itemView.findViewById(R.id.negocio_horarios)
        whatsapp = itemView.findViewById(R.id.negocio_whatsapp)
        telefono = itemView.findViewById(R.id.negocio_telefono)
        descripcion = itemView.findViewById(R.id.negocio_descripcion)
        imagen = itemView.findViewById(R.id.negocio_image)
    }

    fun bind(negocio: Negocio) {
        nombre?.setText(negocio.nombre)
        horario?.setText(negocio.horario)
        whatsapp?.setText(negocio.whatsapp)
        telefono?.setText(negocio.telefono)
        descripcion?.setText("Descripcion: " + negocio.descripcion)
        imagen?.setImageURI(Uri.parse(negocio.imagen))
    }
}