package com.morris.concepcionapp.models

data class Reporte(
    val descripcion: String? = null,
    val fecha: String? = null,
    val usuarioID: String? = null,
    val usuarioNombre: String? = null,
    val usuarioEmail: String? = null,
    val usuarioPhoneNumber: String? = null,
    val verificado: Boolean? = null
)