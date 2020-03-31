package com.morris.concepcionapp.ui.main

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.Image
import android.net.Uri
import android.os.Build
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.text.HtmlCompat
import androidx.recyclerview.widget.RecyclerView
import com.morris.concepcionapp.Funciones
import com.morris.concepcionapp.Negocio
import com.morris.concepcionapp.R
import com.squareup.picasso.Picasso
import kotlin.Exception
import com.squareup.picasso.Callback as callback

class NegocioAdapter(private val list: MutableList<Negocio>): RecyclerView.Adapter<NegocioViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NegocioViewHolder {

        val inflater = LayoutInflater.from(parent.context)

        return NegocioViewHolder(inflater, parent)
    }

    override fun getItemCount(): Int {

        return list.size
    }

    override fun onBindViewHolder(holder: NegocioViewHolder, position: Int) {

        val negocio = list[position]
        holder.bind(negocio)
    }
}

class NegocioViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
        RecyclerView.ViewHolder(inflater.inflate(R.layout.negocio_view, parent, false)) {

    private lateinit var nombre: TextView
    private lateinit var horario: TextView
    private lateinit var whatsapp: TextView
    private lateinit var telefono: TextView
    private lateinit var descripcion: TextView
    private lateinit var direccion: TextView
    private lateinit var imagen: ImageView
    private lateinit var progressBar: ProgressBar

    
    private val view = inflater.inflate(R.layout.negocio_view, parent, false)

    // Vinculo las vistas con los datos
    init {
        setView()

        setListeners()
    }

    private fun setView() {

        nombre = itemView.findViewById(R.id.negocio_nombre)
        horario = itemView.findViewById(R.id.negocio_horarios)
        whatsapp = itemView.findViewById(R.id.negocio_whatsapp)
        telefono = itemView.findViewById(R.id.negocio_telefono)
        descripcion = itemView.findViewById(R.id.negocio_descripcion)
        direccion = itemView.findViewById(R.id.negocio_direccion)
        imagen = itemView.findViewById(R.id.negocio_image)

        progressBar = itemView.findViewById(R.id.progressBarNegocio)
    }

    private fun setListeners() {

        whatsapp.setOnClickListener {

            try {
                val msjTo = "https://api.whatsapp.com/send?phone=${whatsapp.text}"

                val intent = Intent(Intent.ACTION_VIEW)

                intent.data = Uri.parse(msjTo)

                view.context.startActivity(intent)
            }
            catch (e: Exception) {
                Toast.makeText(view.context, e.message, Toast.LENGTH_SHORT).show()
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

    fun bind(negocio: Negocio) {

        nombre.text = negocio.nombre?.let { Funciones.createTitle(it) }
        horario.text = negocio.horario?.capitalize()
        telefono.text = negocio.telefono
        descripcion.text = negocio.descripcion?.capitalize()

        val nuevoWhatsapp = "<u>${negocio.whatsapp}</u>"
        whatsapp.text = HtmlCompat.fromHtml(nuevoWhatsapp, HtmlCompat.FROM_HTML_MODE_LEGACY)

        val direccionArray = negocio.direccion!!.split(",")
        val nuevaDireccion = "<u>${direccionArray[0].capitalize()}</u>"
        direccion.text = HtmlCompat.fromHtml(nuevaDireccion, HtmlCompat.FROM_HTML_MODE_LEGACY)

        Picasso.get().load(negocio.imagenURL)
            .error(R.drawable.ic_google_downasaur)
            .fit().centerCrop()
            .into(imagen, object: com.squareup.picasso.Callback {

                override fun onSuccess() {
                    progressBar.visibility = View.GONE
                }

                override fun onError(e: java.lang.Exception?) {
                    progressBar.visibility = View.GONE
                }
            })
    }
}
