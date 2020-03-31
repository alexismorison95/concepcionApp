package com.morris.concepcionapp

import java.text.Normalizer

class Funciones {

    companion object {

        fun createTitle(cadena: String): String {

            val aux = cadena.split(" ")
            var titulo = ""

            for (palabra in aux) {
                titulo += palabra.capitalize() + " "
            }

            return titulo
        }

        fun quitarTildes(cadena: String): String {

            val regex = "\\p{InCombiningDiacriticalMarks}+".toRegex()

            val temp = Normalizer.normalize(cadena, Normalizer.Form.NFD)

            return regex.replace(temp, "")
        }
    }
}