package com.example.crud

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.TypedValue
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.cardview.widget.CardView

class VisualizarEmpleadoActivity : AppCompatActivity() {
    private lateinit var contornoImagenEmpleado: CardView

    private lateinit var idEmpleadoTexto: TextView
    private lateinit var imagenEmpleado: ImageView
    private lateinit var nombreCompletoEmpleado: TextView
    private lateinit var estudiosEmpleado: TextView
    private lateinit var areaEducacionEmpleado: TextView
    private lateinit var telefonoEmpleado: TextView
    private lateinit var correoEmpleado: TextView
    private lateinit var generoEmpleado: TextView
    private lateinit var fechaNacimientoEmpleado: TextView
    private lateinit var empresaEmpleado: TextView
    private lateinit var puestoEmpleado: TextView
    private lateinit var salarioEmpleado: TextView
    private lateinit var experienciaLaboralEmpleado: TextView

    private lateinit var botonEditar: Button
    private lateinit var botonEliminar: Button

    private lateinit var sqliteHelper: DatabaseOpenHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_visualizar_empleado)

        contornoImagenEmpleado = findViewById(R.id.contornoImagenEmpleado)

        idEmpleadoTexto = findViewById(R.id.idEmpleadoTexto)
        imagenEmpleado = findViewById(R.id.imagenEmpleado)
        nombreCompletoEmpleado = findViewById(R.id.nombreCompletoEmpleadoTexto)
        estudiosEmpleado = findViewById(R.id.estudiosEmpleadoTexto)
        areaEducacionEmpleado = findViewById(R.id.areaEducacionEmpleadoTexto)
        telefonoEmpleado = findViewById(R.id.telefonoEmpleadoTexto)
        correoEmpleado = findViewById(R.id.correoEmpleadoTexto)
        generoEmpleado = findViewById(R.id.generoEmpleadoTexto)
        fechaNacimientoEmpleado = findViewById(R.id.fechaNacimientoEmpleadoTexto)
        empresaEmpleado = findViewById(R.id.empresaEmpleadoTexto)
        puestoEmpleado = findViewById(R.id.puestoEmpleadoTexto)
        salarioEmpleado = findViewById(R.id.salarioEmpleadoTexto)
        experienciaLaboralEmpleado = findViewById(R.id.experienciaEmpleadoTexto)

        botonEditar = findViewById(R.id.botonEditar)
        botonEliminar = findViewById(R.id.botonEliminar)

        sqliteHelper = DatabaseOpenHelper(this)
        val empleadoDatosID = intent.getStringExtra("empleadoDatosID").toString()

        visualizarDatos(empleadoDatosID)

        botonEditar.setOnClickListener {
            cerrarActividad("", empleadoDatosID)
        }

        botonEliminar.setOnClickListener {
            sqliteHelper.deleteEmployee(empleadoDatosID)
            cerrarActividad("Empleado eliminado", "")
        }

        onBackPressedDispatcher.addCallback(this, object: OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                cerrarActividad("", "")
            }
        })
    }

    private fun visualizarDatos(empleadoDatosID: String){
        val datosEmpleado = sqliteHelper.getEmployee(empleadoDatosID)

        imagenEmpleado.setImageBitmap(datosEmpleado.fotoPerfil)
        val banderaFotoPerfil = datosEmpleado.banderaFoto

        var texto = "ID: ${datosEmpleado.id}"
        idEmpleadoTexto.text = texto

        texto = "${datosEmpleado.nombres} ${datosEmpleado.apellidos}"
        nombreCompletoEmpleado.text = texto

        texto = datosEmpleado.educacionNivel
        estudiosEmpleado.text = texto

        texto = datosEmpleado.areaEducacion
        areaEducacionEmpleado.text = texto

        texto = datosEmpleado.telefono
        telefonoEmpleado.text = texto

        texto = datosEmpleado.correo
        correoEmpleado.text = texto

        texto = datosEmpleado.genero
        generoEmpleado.text = texto

        texto = datosEmpleado.fechaNacimiento
        fechaNacimientoEmpleado.text = texto

        texto = datosEmpleado.empresa
        empresaEmpleado.text = texto

        texto = datosEmpleado.puesto
        puestoEmpleado.text = texto

        texto = "${datosEmpleado.salario} ${datosEmpleado.monedaSalario}"
        salarioEmpleado.text = texto

        texto = datosEmpleado.experienciaLaboral
        experienciaLaboralEmpleado.text = texto

        var valor = 60f

        if(banderaFotoPerfil == "Si"){
            valor = 0f
            imagenEmpleado.scaleType = ImageView.ScaleType.CENTER_CROP
        }

        val dp = (TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, valor, resources.displayMetrics)).toInt()
        contornoImagenEmpleado.setContentPadding(dp, dp, dp, dp)
    }

    private fun cerrarActividad(mensaje: String, empleadoID: String){
        var actividad = Intent(this@VisualizarEmpleadoActivity, MainActivity::class.java)
        if(empleadoID.isNotEmpty()){
            actividad = Intent(this@VisualizarEmpleadoActivity, DatosEmpleadoActivity::class.java)
            actividad.putExtra("empleadoDatosID", empleadoID)
        }

        startActivity(actividad)
        finish()

        if(mensaje.isNotEmpty()){
            Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show()
        }
    }
}