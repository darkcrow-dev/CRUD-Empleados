package com.example.crud

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {
    private lateinit var pantallaActvidad: ConstraintLayout
    private lateinit var botonAgregarEmpleado: FloatingActionButton
    private lateinit var barraBusquedaEmpleado: EditText

    private lateinit var sqliteHelper: DatabaseOpenHelper
    private lateinit var listaEmpleados: RecyclerView
    private var adaptador: AdaptadorEmpleados? = null
    private var buscarEmpleadoBandera = false
    private lateinit var empleadosBusqueda: ArrayList<Empleado>
    private lateinit var empleadosActuales: ArrayList<Empleado>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        botonAgregarEmpleado = findViewById(R.id.botonAgregarEmpleado)
        pantallaActvidad = findViewById(R.id.pantallaActividad)
        listaEmpleados = findViewById(R.id.listaEmpleados)
        barraBusquedaEmpleado = findViewById(R.id.barraBusquedaEmpleado)

        sqliteHelper = DatabaseOpenHelper(this)

        listaEmpleados.layoutManager = LinearLayoutManager(this)
        adaptador = AdaptadorEmpleados(this)
        listaEmpleados.adapter = adaptador

        obtenerEmpleados()

        botonAgregarEmpleado.setOnClickListener {
            val actividad = Intent(this, DatosEmpleadoActivity::class.java)
            actividad.putExtra("empleadoDatosID", "")
            startActivity(actividad)
            finish()
        }

        adaptador!!.setOnClickListener(object: AdaptadorEmpleados.OnClickListener{
            override fun onClickItem(position: Int) {
                val actividad = Intent(this@MainActivity, VisualizarEmpleadoActivity::class.java)
                if(buscarEmpleadoBandera){
                    actividad.putExtra("empleadoDatosID", empleadosBusqueda[position].id)
                }
                else{
                    actividad.putExtra("empleadoDatosID", empleadosActuales[position].id)
                }
                startActivity(actividad)
                finish()
            }

        })

        pantallaActvidad.setOnClickListener {
            esconderTeclado()
            window.currentFocus?.clearFocus()
        }

        barraBusquedaEmpleado.addTextChangedListener { texto ->
            buscarEmpleado(texto.toString())
        }
    }

    private fun esconderTeclado(){
        val vista = currentFocus
        val accion = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        accion.hideSoftInputFromWindow(vista?.windowToken, 0)
    }

    private fun obtenerEmpleados(){
        empleadosActuales = sqliteHelper.getAllEmployees()
        if(empleadosActuales.isNotEmpty()){
            adaptador?.actualizarLista(empleadosActuales)
        }
    }

    private fun buscarEmpleado(texto: String){
        if(empleadosActuales.isNotEmpty()){
            if( (texto != "")){
                empleadosBusqueda = ArrayList()
                buscarEmpleadoBandera = true
                for(i in 0 until empleadosActuales.size){
                    if(empleadosActuales[i].id.contains(texto) || empleadosActuales[i].nombres.lowercase().contains(texto.lowercase()) ||
                        empleadosActuales[i].apellidos.lowercase().contains(texto.lowercase()) || empleadosActuales[i].genero.lowercase().contains(texto.lowercase()) ||
                        empleadosActuales[i].puesto.lowercase().contains(texto.lowercase())){

                        empleadosBusqueda.add(Empleado(empleadosActuales[i].id, empleadosActuales[i].fotoPerfil, empleadosActuales[i].banderaFoto, empleadosActuales[i].nombres, empleadosActuales[i].apellidos, empleadosActuales[i].genero, "",
                            "", "", "", "", "", empleadosActuales[i].puesto, "", "", ""))
                    }
                }
                adaptador!!.actualizarLista(empleadosBusqueda)
            }
            else{
                buscarEmpleadoBandera = false
                adaptador!!.actualizarLista(empleadosActuales)
            }
        }
    }
}