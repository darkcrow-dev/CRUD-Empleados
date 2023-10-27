package com.example.crud

import android.graphics.Bitmap

data class Empleado(var id: String = "",
                    var fotoPerfil: Bitmap,
                    var banderaFoto: String = "",
                    var nombres: String = "",
                    var apellidos: String = "",
                    var genero: String = "",
                    var fechaNacimiento: String = "",
                    var educacionNivel: String = "",
                    var areaEducacion: String = "",
                    var telefono: String = "",
                    var correo: String = "",
                    var empresa: String = "",
                    var puesto: String = "",
                    var experienciaLaboral: String = "",
                    var salario: String = "",
                    var monedaSalario: String = "")
