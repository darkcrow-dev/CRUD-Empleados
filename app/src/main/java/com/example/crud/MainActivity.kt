package com.example.crud

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var nombre: EditText
    private lateinit var correo: EditText
    private lateinit var sqliteHelper: DatabaseOpenHelper
    private lateinit var lista: RecyclerView
    private var adaptador: EmployeeAdapter? = null
    private var empleado: Employee? = null
    private var contador = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Elementos de la vista
        nombre = findViewById(R.id.nameEmployee)
        correo = findViewById(R.id.emailEmployee)
        lista = findViewById(R.id.listEmployee)

        val botonAgregar = findViewById<Button>(R.id.buttonADD)
        val botonLeer = findViewById<Button>(R.id.buttonREAD)

        sqliteHelper = DatabaseOpenHelper(this)

        lista.layoutManager = LinearLayoutManager(this)
        adaptador = EmployeeAdapter()
        lista.adapter = adaptador

        botonAgregar.setOnClickListener {
            if(!contador){
                agregarEmpleado()
            }
        }

        botonLeer.setOnClickListener {
            if(!contador){
                obtenerEmpleados()
            }
        }

        adaptador?.setOnClickDeleteItem {
            eliminarEmpleado(it.id)
        }

        adaptador?.setOnClickEditItem {
            if(!contador){
                contador = true
                nombre.setText(it.name)
                correo.setText(it.email)
                empleado = it
            }
            else {
                actualizarEmpleado()
            }
        }

    }

    private fun agregarEmpleado(){
        val name = nombre.text.toString()
        val email = correo.text.toString()

        if(name.isEmpty() || email.isEmpty()){
            Toast.makeText(this,"Ingrese los datos requeridos", Toast.LENGTH_SHORT).show()
        }
        else{
            val ee = Employee(name = name, email = email)
            val status = sqliteHelper.insertEmployee(ee)

            if(status > -1){
                Toast.makeText(this,"Empleado agregado...", Toast.LENGTH_SHORT).show()
                limpiarTexto()
                obtenerEmpleados()
            }
            else{
                Toast.makeText(this,"Los datos no se guardaron", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun obtenerEmpleados(){
        val eeList = sqliteHelper.getAllEmployees()

        if(eeList.isEmpty()){
            Toast.makeText(this, "No existen datos guardados", Toast.LENGTH_SHORT).show()
        }
        else{
            adaptador?.addItems(eeList)
        }
    }

    private fun actualizarEmpleado(){
        val name = nombre.text.toString()
        val email = correo.text.toString()

        if(empleado == null){
            Toast.makeText(this,"Seleccione empleado a cambiar", Toast.LENGTH_SHORT).show()
        }
        else if(name == empleado?.name && email == empleado?.email){
            Toast.makeText(this, "Datos no se cambiaron", Toast.LENGTH_SHORT).show()
        }
        else if(name.isEmpty() || email.isEmpty()){
            Toast.makeText(this,"Ingrese los datos requeridos", Toast.LENGTH_SHORT).show()
        }

        val stdConstructor = Employee(id = empleado!!.id, name = name, email = email)
        val status = sqliteHelper.updateStudent(stdConstructor)

        if(status > -1){
            Toast.makeText(this,"Datos cambiados", Toast.LENGTH_SHORT).show()
            limpiarTexto()
            empleado = null
            contador = false
            obtenerEmpleados()
        }
        else {
            Toast.makeText(this, "Actualizar datos falló", Toast.LENGTH_SHORT).show()
        }
    }

    private fun eliminarEmpleado(id: String){
        val mensaje = AlertDialog.Builder(this)
        mensaje.setMessage("¿Está seguro de eliminar este dato?")
        mensaje.setCancelable(true)

        mensaje.setPositiveButton("Si"){ dialog, _ ->
            val status = sqliteHelper.deleteEmployee(id)

            if(status > -1){
                Toast.makeText(this,"Datos eliminados", Toast.LENGTH_SHORT).show()
                limpiarTexto()
                contador = false
            }
            else {
                Toast.makeText(this, "Falló la eliminación de datos", Toast.LENGTH_SHORT).show()
            }

            val eeList = sqliteHelper.getAllEmployees()
            adaptador?.addItems(eeList)

            dialog.dismiss()
        }

        mensaje.setNegativeButton("No"){ dialog, _ ->
            dialog.dismiss()
        }

        val alerta = mensaje.create()
        alerta.show()
    }

    private fun limpiarTexto(){
        nombre.setText("")
        correo.setText("")
        nombre.requestFocus()
    }

}