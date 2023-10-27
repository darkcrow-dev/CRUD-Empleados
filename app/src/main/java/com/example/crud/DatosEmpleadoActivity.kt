package com.example.crud

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.TypedValue
import android.view.MotionEvent
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.util.PatternsCompat
import com.google.android.material.textfield.TextInputLayout
import java.util.Calendar

class DatosEmpleadoActivity : AppCompatActivity() {
    private lateinit var contornoImagenEmpleado: CardView
    private lateinit var pantallaActividad: ConstraintLayout
    private lateinit var nombresEmpleadoLayout: TextInputLayout
    private lateinit var apellidosEmpleadoLayout: TextInputLayout
    private lateinit var generoEmpleadoLayout: TextInputLayout
    private lateinit var fechaNacimientoEmpleadoLayout: TextInputLayout
    private lateinit var educacionEmpleadoLayout: TextInputLayout
    private lateinit var areaEducacionEmpleadoLayout: TextInputLayout
    private lateinit var telefonoEmpleadoLayout: TextInputLayout
    private lateinit var correoEmpleadoLayout: TextInputLayout
    private lateinit var empresaEmpleadoLayout: TextInputLayout
    private lateinit var puestoEmpleadoLayout: TextInputLayout
    private lateinit var experienciaLaboralEmpleadoLayout: TextInputLayout
    private lateinit var salarioEmpleadoLayout: TextInputLayout
    private lateinit var monedaSalarioEmpleadoLayout: TextInputLayout

    private lateinit var tituloActividad: TextView
    private lateinit var nombresEmpleado: EditText
    private lateinit var apellidosEmpleado: EditText
    private lateinit var generoEmpleado: AutoCompleteTextView
    private lateinit var fechaNacimientoEmpleado: AutoCompleteTextView
    private lateinit var educacionEmpleado: AutoCompleteTextView
    private lateinit var areaEducacionEmpleado: EditText
    private lateinit var telefonoEmpleado: EditText
    private lateinit var correoEmpleado: EditText
    private lateinit var empresaEmpleado: EditText
    private lateinit var puestoEmpleado: EditText
    private lateinit var experienciaLaboralEmpleado: AutoCompleteTextView
    private lateinit var salarioEmpleado: EditText
    private lateinit var monedaSalarioEmpleado: AutoCompleteTextView

    private lateinit var botonGuardar: Button
    private lateinit var botonCancelar: Button
    private lateinit var imagenEmpleado: ImageView

    private lateinit var adaptadorGenero: ArrayAdapter<String>
    private lateinit var adaptadorEducacion: ArrayAdapter<String>
    private lateinit var adaptadorExperiencia: ArrayAdapter<String>
    private lateinit var adaptadorMoneda: ArrayAdapter<String>
    private lateinit var banderaFotoPerfil: String
    private lateinit var fotoPerfilEmpleado: Bitmap

    private lateinit var datosEmpleado: Empleado
    private lateinit var sqliteHelper: DatabaseOpenHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_datos_empleado)

        //Inicializar la pantalla de actividad y los layouts
        contornoImagenEmpleado = findViewById(R.id.contornoImagenEmpleado)
        pantallaActividad = findViewById(R.id.pantallaActividad)
        nombresEmpleadoLayout = findViewById(R.id.nombresEmpleadoLayout)
        apellidosEmpleadoLayout = findViewById(R.id.apellidosEmpleadoLayout)
        generoEmpleadoLayout = findViewById(R.id.generoEmpleadoLayout)
        fechaNacimientoEmpleadoLayout = findViewById(R.id.fechaNacimientoEmpleadoLayout)
        educacionEmpleadoLayout = findViewById(R.id.educacionEmpleadoLayout)
        areaEducacionEmpleadoLayout = findViewById(R.id.areaEducacionEmpleadoLayout)
        telefonoEmpleadoLayout = findViewById(R.id.telefonoEmpleadoLayout)
        correoEmpleadoLayout = findViewById(R.id.correoEmpleadoLayout)
        empresaEmpleadoLayout = findViewById(R.id.empresaEmpleadoLayout)
        puestoEmpleadoLayout = findViewById(R.id.puestoEmpleadoLayout)
        experienciaLaboralEmpleadoLayout = findViewById(R.id.experienciaLaboralEmpleadoLayout)
        salarioEmpleadoLayout = findViewById(R.id.salarioEmpleadoLayout)
        monedaSalarioEmpleadoLayout = findViewById(R.id.monedaSalarioEmpleadoLayout)

        //Inicializar los edittext del empleado y el titulo
        tituloActividad = findViewById(R.id.tituloActividad)
        nombresEmpleado = findViewById(R.id.nombresEmpleado)
        apellidosEmpleado = findViewById(R.id.apellidosEmpleado)
        generoEmpleado = findViewById(R.id.generoEmpleado)
        fechaNacimientoEmpleado = findViewById(R.id.fechaNacimientoEmpleado)
        educacionEmpleado = findViewById(R.id.educacionEmpleado)
        areaEducacionEmpleado = findViewById(R.id.areaEducacionEmpleado)
        telefonoEmpleado = findViewById(R.id.telefonoEmpleado)
        correoEmpleado = findViewById(R.id.correoEmpleado)
        empresaEmpleado = findViewById(R.id.empresaEmpleado)
        puestoEmpleado = findViewById(R.id.puestoEmpleado)
        experienciaLaboralEmpleado = findViewById(R.id.experienciaLaboralEmpleado)
        salarioEmpleado = findViewById(R.id.salarioEmpleado)
        monedaSalarioEmpleado = findViewById(R.id.monedaSalarioEmpleado)

        //Inicializar los botones
        botonGuardar = findViewById(R.id.botonGuardar)
        botonCancelar = findViewById(R.id.botonCancelar)
        imagenEmpleado = findViewById(R.id.imagenEmpleado)

        //Configurar el autotextview de fecha de nacimiento, genero empleado, educacion empleado,
        // experiencia empleado
        fechaNacimientoEmpleado.showSoftInputOnFocus = false
        fechaNacimientoEmpleado.inputType = 0

        generoEmpleado.showSoftInputOnFocus = false
        generoEmpleado.inputType = 0
        generoEmpleado.setDropDownBackgroundResource(R.color.backgroundColorDropdown)

        educacionEmpleado.showSoftInputOnFocus = false
        educacionEmpleado.inputType = 0
        educacionEmpleado.setDropDownBackgroundResource(R.color.backgroundColorDropdown)

        experienciaLaboralEmpleado.showSoftInputOnFocus = false
        experienciaLaboralEmpleado.inputType = 0
        experienciaLaboralEmpleado.setDropDownBackgroundResource(R.color.backgroundColorDropdown)

        monedaSalarioEmpleado.showSoftInputOnFocus = false
        monedaSalarioEmpleado.inputType = 0
        monedaSalarioEmpleado.setDropDownBackgroundResource(R.color.backgroundColorDropdown)

        //Configuracion del adaptador para el genero
        val generos = resources.getStringArray(R.array.genders)
        adaptadorGenero = ArrayAdapter(this, R.layout.dropdown_item_menu, generos)
        generoEmpleado.setAdapter(adaptadorGenero)

        val nivelesEducativos = resources.getStringArray(R.array.schooling_levels)
        adaptadorEducacion = ArrayAdapter(this, R.layout.dropdown_item_menu, nivelesEducativos)
        educacionEmpleado.setAdapter(adaptadorEducacion)

        val experienciasLaborales = resources.getStringArray(R.array.work_experience)
        adaptadorExperiencia = ArrayAdapter(this, R.layout.dropdown_item_menu, experienciasLaborales)
        experienciaLaboralEmpleado.setAdapter(adaptadorExperiencia)

        val monedas = resources.getStringArray(R.array.currency)
        adaptadorMoneda = ArrayAdapter(this, R.layout.dropdown_item_menu, monedas)
        monedaSalarioEmpleado.setAdapter(adaptadorMoneda)

        //Uso de la base de datos
        sqliteHelper = DatabaseOpenHelper(this)

        //visualizacion de datos entre un empleado nuevo y uno ya existente
        val empleadoDatosID = intent.getStringExtra("empleadoDatosID").toString()
        visualizarDatos(empleadoDatosID)

        //Acciones para el campo de la imagen del empleado
        contornoImagenEmpleado.setOnClickListener {
            esconderTeclado()
            seleccionarImagen()
        }

        //Acciones para el campo del genero del empleado
        generoEmpleado.setOnClickListener {
            if(!generoEmpleado.isPopupShowing){
                esconderEnfoque()
            }
        }

        generoEmpleado.setOnTouchListener { view, motionEvent ->
            if(motionEvent.action == MotionEvent.ACTION_UP){
                if(!generoEmpleado.hasFocus()){
                    esconderTeclado()
                    generoEmpleado.showDropDown()
                    view.performClick()
                }
            }

            return@setOnTouchListener false
        }

        generoEmpleado.setOnItemClickListener { _, _, _, _ ->
            esconderEnfoque()
        }

        generoEmpleadoLayout.setEndIconOnClickListener {
            if(!generoEmpleado.hasFocus()){
                generoEmpleado.requestFocus()
                esconderTeclado()
                generoEmpleado.showDropDown()
            }
            else{
                esconderEnfoque()
            }
        }

        generoEmpleado.setOnKeyListener { _, _, event ->
            !event!!.device.isVirtual
        }

        //Acciones para el campo de la fecha de nacimiento
        fechaNacimientoEmpleado.setOnTouchListener { view, motionEvent ->
            if(motionEvent.action == MotionEvent.ACTION_UP){
                if(!fechaNacimientoEmpleado.hasFocus()){
                    esconderTeclado()
                    fechaNacimientoEmpleado.requestFocus()
                    calendario()
                    view.performClick()
                }
            }

            return@setOnTouchListener false
        }

        fechaNacimientoEmpleado.setOnKeyListener { _, _, event ->
            !event!!.device.isVirtual
        }

        //Acciones para el campo de la educacion del empleado
        educacionEmpleado.setOnClickListener {
            if(!educacionEmpleado.isPopupShowing){
                esconderEnfoque()
            }
        }

        educacionEmpleado.setOnTouchListener { view, motionEvent ->
            if(motionEvent.action == MotionEvent.ACTION_UP){
                if(!educacionEmpleado.hasFocus()){
                    esconderTeclado()
                    educacionEmpleado.showDropDown()
                    view.performClick()
                }
            }

            return@setOnTouchListener false
        }

        educacionEmpleado.setOnItemClickListener { _, _, _, _ ->
            esconderEnfoque()
        }

        educacionEmpleadoLayout.setEndIconOnClickListener {
            if(!educacionEmpleado.hasFocus()){
                educacionEmpleado.requestFocus()
                esconderTeclado()
                educacionEmpleado.showDropDown()
            }
            else{
                esconderEnfoque()
            }
        }

        educacionEmpleado.setOnKeyListener { _, _, event ->
            !event!!.device.isVirtual
        }

        //Acciones para el campo de la experiencia del empleado
        experienciaLaboralEmpleado.setOnClickListener {
            if(!experienciaLaboralEmpleado.isPopupShowing){
                esconderEnfoque()
            }
        }

        experienciaLaboralEmpleado.setOnTouchListener { view, motionEvent ->
            if(motionEvent.action == MotionEvent.ACTION_UP){
                if(!experienciaLaboralEmpleado.hasFocus()){
                    esconderTeclado()
                    experienciaLaboralEmpleado.showDropDown()
                    view.performClick()
                }
            }

            return@setOnTouchListener false
        }

        experienciaLaboralEmpleado.setOnItemClickListener { _, _, _, _ ->
            esconderEnfoque()
        }

        experienciaLaboralEmpleadoLayout.setEndIconOnClickListener {
            if(!experienciaLaboralEmpleado.hasFocus()){
                experienciaLaboralEmpleado.requestFocus()
                esconderTeclado()
                experienciaLaboralEmpleado.showDropDown()
            }
            else{
                esconderEnfoque()
            }
        }

        experienciaLaboralEmpleado.setOnKeyListener { _, _, event ->
            !event!!.device.isVirtual
        }

        //Acciones para el campo de la divisa del salario
        monedaSalarioEmpleado.setOnClickListener {
            if(!monedaSalarioEmpleado.isPopupShowing){
                esconderEnfoque()
            }
        }

        monedaSalarioEmpleado.setOnTouchListener { view, motionEvent ->
            if(motionEvent.action == MotionEvent.ACTION_UP){
                if(!monedaSalarioEmpleado.hasFocus()){
                    esconderTeclado()
                    monedaSalarioEmpleado.showDropDown()
                    view.performClick()
                }
            }

            return@setOnTouchListener false
        }

        monedaSalarioEmpleado.setOnItemClickListener { _, _, _, _ ->
            esconderEnfoque()
        }

        monedaSalarioEmpleadoLayout.setEndIconOnClickListener {
            if(!monedaSalarioEmpleado.hasFocus()){
                monedaSalarioEmpleado.requestFocus()
                esconderTeclado()
                monedaSalarioEmpleado.showDropDown()
            }
            else{
                esconderEnfoque()
            }
        }

        monedaSalarioEmpleado.setOnKeyListener { _, _, event ->
            !event!!.device.isVirtual
        }

        //Acciones para la pantalla de actividad cuando es tocada
        pantallaActividad.setOnClickListener {
            esconderTeclado()

            if(generoEmpleado.hasFocus() || educacionEmpleado.hasFocus() || experienciaLaboralEmpleado.hasFocus() || monedaSalarioEmpleado.hasFocus()) {
                esconderEnfoque()
            }
        }

        //Acciones para el campo del nombre del empleado
        nombresEmpleado.setOnEditorActionListener { _, actionID, _ ->
            if(actionID == EditorInfo.IME_ACTION_DONE) {
                esconderEnfoque()
            }
            return@setOnEditorActionListener false
        }

        //Acciones para el campo del apellido del empleado
        apellidosEmpleado.setOnEditorActionListener { _, actionID, _ ->
            if(actionID == EditorInfo.IME_ACTION_DONE) {
                esconderEnfoque()
            }
            return@setOnEditorActionListener false
        }

        //Acciones para el campo del area de educacion del empleado
        areaEducacionEmpleado.setOnEditorActionListener { _, actionID, _ ->
            if(actionID == EditorInfo.IME_ACTION_DONE) {
                esconderEnfoque()
            }
            return@setOnEditorActionListener false
        }

        //Acciones para el campo del telefono del empleado
        telefonoEmpleado.setOnEditorActionListener { _, actionID, _ ->
            if(actionID == EditorInfo.IME_ACTION_DONE) {
                esconderEnfoque()
            }
            return@setOnEditorActionListener false
        }

        //Acciones para el campo del correo del empleado
        correoEmpleado.setOnEditorActionListener { _, actionID, _ ->
            if(actionID == EditorInfo.IME_ACTION_DONE) {
                esconderEnfoque()
            }
            return@setOnEditorActionListener false
        }

        //Acciones para el campo de la empresa del empleado
        empresaEmpleado.setOnEditorActionListener { _, actionID, _ ->
            if(actionID == EditorInfo.IME_ACTION_DONE) {
                esconderEnfoque()
            }
            return@setOnEditorActionListener false
        }

        //Acciones para el campo del puesto del empleado
        puestoEmpleado.setOnEditorActionListener { _, actionID, _ ->
            if(actionID == EditorInfo.IME_ACTION_DONE) {
                esconderEnfoque()
            }
            return@setOnEditorActionListener false
        }

        //Acciones para el campo del salario del empleado
        salarioEmpleado.setOnEditorActionListener { _, actionID, _ ->
            if(actionID == EditorInfo.IME_ACTION_DONE) {
                esconderEnfoque()
            }
            return@setOnEditorActionListener false
        }

        //Acciones para los botones agregar y cancelar
        botonGuardar.setOnClickListener {
            if(validarDatos()){
                val datosModificados = modificarDatos()
                var mensaje = "No se actualizaron los datos"

                if(empleadoDatosID.isEmpty()){
                    sqliteHelper.insertEmployee(obteniendoDatosEmpleado(datosModificados))
                    mensaje = "Empleado agregado"
                }
                else{
                    if(validarDatosActualizados(datosModificados)){
                        sqliteHelper.updateEmployee(obteniendoDatosEmpleado(datosModificados))
                        mensaje = "Datos actualizados"
                    }
                }

                cerrarActividad(mensaje, empleadoDatosID)
            }
        }

        botonCancelar.setOnClickListener {
            cerrarActividad("", empleadoDatosID)
        }

        //Acciones para cuando el usuario apriete el boton de retroceso
        onBackPressedDispatcher.addCallback(this, object: OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                cerrarActividad("", empleadoDatosID)
            }

        })
    }

    private fun esconderEnfoque(){
        window.currentFocus!!.clearFocus()
    }

    private fun esconderTeclado(){
        val vista = currentFocus
        val accion = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        accion.hideSoftInputFromWindow(vista?.windowToken, 0)
    }

    private fun calendario(){
        val calendar = Calendar.getInstance()
        val yearCalendar = calendar.get(Calendar.YEAR)
        val monthCalendar = calendar.get(Calendar.MONTH)
        val dayCalendar = calendar.get(Calendar.DAY_OF_MONTH)

        val calendarioDialogo = DatePickerDialog(this, R.style.Theme_Calendar, { _, year, month, day ->
            val texto = "$day/${month + 1}/$year"
            fechaNacimientoEmpleado.setText(texto)
            esconderEnfoque()
        }, yearCalendar, monthCalendar, dayCalendar)

        calendarioDialogo.setOnCancelListener {
            esconderEnfoque()
        }

        //Fecha minima del calendario
        val minYear = yearCalendar - 100
        val minMonth = 0
        val minDay = 1
        calendar.set(minYear, minMonth, minDay)
        calendarioDialogo.datePicker.minDate = calendar.timeInMillis

        //Fecha maxima del calendario
        calendar.set(yearCalendar, monthCalendar, dayCalendar)
        calendarioDialogo.datePicker.maxDate = calendar.timeInMillis

        calendarioDialogo.show()
    }

    private fun validarDatos(): Boolean{
        var texto = nombresEmpleado.text.toString()
        var datosCorrectos = 0

        if(texto.isEmpty() || !(Regex("^[a-zA-ZnÑáÁéÉíÍóÓúÚ ]*\$").matches(texto))){
            nombresEmpleadoLayout.error = "Ingrese datos válidos"
        }
        else{
            nombresEmpleadoLayout.error = ""
            datosCorrectos += 1
        }

        texto = apellidosEmpleado.text.toString()
        if(texto.isEmpty() || !(Regex("^[a-zA-ZnÑáÁéÉíÍóÓúÚ ]*\$").matches(texto))){
            apellidosEmpleadoLayout.error = "Ingrese datos válidos"
        }
        else{
            apellidosEmpleadoLayout.error = ""
            datosCorrectos += 1
        }

        texto = generoEmpleado.text.toString()
        if(texto.isEmpty()){
            generoEmpleadoLayout.error = "Seleccione una opción válida"
        }
        else{
            generoEmpleadoLayout.error = ""
            datosCorrectos += 1
        }

        texto = fechaNacimientoEmpleado.text.toString()
        if(texto.isEmpty()){
            fechaNacimientoEmpleadoLayout.error = "Seleccione una opción válida"
        }
        else{
            fechaNacimientoEmpleadoLayout.error = ""
            datosCorrectos += 1
        }

        texto = educacionEmpleado.text.toString()
        if(texto.isEmpty()){
            educacionEmpleadoLayout.error = "Seleccione una opción válida"
        }
        else{
            educacionEmpleadoLayout.error = ""
            datosCorrectos += 1
        }

        texto = areaEducacionEmpleado.text.toString()
        if(texto.isEmpty() || !(Regex("^[a-zA-ZnÑáÁéÉíÍóÓúÚ ]*\$").matches(texto))){
            areaEducacionEmpleadoLayout.error = "Ingrese datos válidos"
        }
        else{
            areaEducacionEmpleadoLayout.error = ""
            datosCorrectos += 1
        }

        texto = telefonoEmpleado.text.toString()
        if(texto.isEmpty()){
            telefonoEmpleadoLayout.error = "Ingrese datos válidos"
        }
        else{
            telefonoEmpleadoLayout.error = ""
            datosCorrectos += 1
        }

        texto = correoEmpleado.text.toString()
        val validarCorreo = PatternsCompat.EMAIL_ADDRESS.matcher(texto).matches()
        if(texto.isEmpty() || !validarCorreo){
            correoEmpleadoLayout.error = "Ingrese datos válidos"
        }
        else{
            correoEmpleadoLayout.error = ""
            datosCorrectos += 1
        }

        texto = empresaEmpleado.text.toString()
        if(texto.isEmpty() || !Regex("^[a-zA-Z0-9nÑáÁéÉíÍóÓúÚ ]*\$").matches(texto)){
            empresaEmpleadoLayout.error = "Ingrese datos válidos"
        }
        else{
            empresaEmpleadoLayout.error = ""
            datosCorrectos += 1
        }

        texto = puestoEmpleado.text.toString()
        if(texto.isEmpty() || !Regex("^[a-zA-Z0-9nÑáÁéÉíÍóÓúÚ ]*\$").matches(texto)){
            puestoEmpleadoLayout.error = "Ingrese datos válidos"
        }
        else{
            puestoEmpleadoLayout.error = ""
            datosCorrectos += 1
        }

        texto = experienciaLaboralEmpleado.text.toString()
        if(texto.isEmpty()){
            experienciaLaboralEmpleadoLayout.error = "Seleccione una opción válida"
        }
        else{
            experienciaLaboralEmpleadoLayout.error = ""
            datosCorrectos += 1
        }

        texto = salarioEmpleado.text.toString()
        if(texto.isEmpty()){
            salarioEmpleadoLayout.error = "Ingrese datos válidos"
        }
        else{
            salarioEmpleadoLayout.error = ""
            datosCorrectos += 1
        }

        if(datosCorrectos == 12){
            return true
        }

        return false
    }

    private fun modificarDatos(): ArrayList<String> {
        val arrayDatos = ArrayList<String>()

        var texto = nombresEmpleado.text.toString()
        var arrayTexto = texto.split(" ").toMutableList()

        arrayTexto.removeAll(listOf("", null))

        for(i in 0 until arrayTexto.size){
            arrayTexto[i] = arrayTexto[i].substring(0, 1).uppercase() + arrayTexto[i].substring(1, arrayTexto[i].length).lowercase()
        }

        var textoModificado = arrayTexto.joinToString(" ")
        arrayDatos.add(textoModificado)

        texto = apellidosEmpleado.text.toString()
        arrayTexto = texto.split(" ").toMutableList()

        arrayTexto.removeAll(listOf("", null))

        for(i in 0 until arrayTexto.size){
            arrayTexto[i] = arrayTexto[i].substring(0, 1).uppercase() + arrayTexto[i].substring(1, arrayTexto[i].length).lowercase()
        }

        textoModificado = arrayTexto.joinToString(" ")
        arrayDatos.add(textoModificado)

        texto = areaEducacionEmpleado.text.toString()
        arrayTexto = texto.split(" ").toMutableList()

        arrayTexto.removeAll(listOf("", null))

        for(i in 0 until arrayTexto.size){
            if(i == 0){
                arrayTexto[i] = arrayTexto[i].substring(0, 1).uppercase() + arrayTexto[i].substring(1, arrayTexto[i].length).lowercase()
            }
            else{
                arrayTexto[i] = arrayTexto[i].lowercase()
            }
        }

        textoModificado = arrayTexto.joinToString(" ")
        arrayDatos.add(textoModificado)

        texto = empresaEmpleado.text.toString()
        arrayTexto = texto.split(" ").toMutableList()

        arrayTexto.removeAll(listOf("", null))

        for(i in 0 until arrayTexto.size){
            if(i == 0){
                arrayTexto[i] = arrayTexto[i].substring(0, 1).uppercase() + arrayTexto[i].substring(1, arrayTexto[i].length).lowercase()
            }
            else{
                arrayTexto[i] = arrayTexto[i].lowercase()
            }
        }

        textoModificado = arrayTexto.joinToString(" ")
        arrayDatos.add(textoModificado)

        texto = puestoEmpleado.text.toString()
        arrayTexto = texto.split(" ").toMutableList()

        arrayTexto.removeAll(listOf("", null))

        for(i in 0 until arrayTexto.size){
            if(i == 0){
                arrayTexto[i] = arrayTexto[i].substring(0, 1).uppercase() + arrayTexto[i].substring(1, arrayTexto[i].length).lowercase()
            }
            else{
                arrayTexto[i] = arrayTexto[i].lowercase()
            }
        }

        textoModificado = arrayTexto.joinToString(" ")
        arrayDatos.add(textoModificado)

        return arrayDatos
    }

    private fun visualizarDatos(empleadoDatosID: String) {
        datosEmpleado = sqliteHelper.getEmployee(empleadoDatosID)

        fotoPerfilEmpleado = datosEmpleado.fotoPerfil

        imagenEmpleado.setImageBitmap(fotoPerfilEmpleado)
        banderaFotoPerfil = datosEmpleado.banderaFoto
        nombresEmpleado.setText(datosEmpleado.nombres)
        apellidosEmpleado.setText(datosEmpleado.apellidos)
        generoEmpleado.setText(datosEmpleado.genero, false)
        fechaNacimientoEmpleado.setText(datosEmpleado.fechaNacimiento)
        educacionEmpleado.setText(datosEmpleado.educacionNivel, false)
        areaEducacionEmpleado.setText(datosEmpleado.areaEducacion)
        telefonoEmpleado.setText(datosEmpleado.telefono)
        correoEmpleado.setText(datosEmpleado.correo)
        empresaEmpleado.setText(datosEmpleado.empresa)
        puestoEmpleado.setText(datosEmpleado.puesto)
        experienciaLaboralEmpleado.setText(datosEmpleado.experienciaLaboral, false)
        salarioEmpleado.setText(datosEmpleado.salario)
        monedaSalarioEmpleado.setText(datosEmpleado.monedaSalario, false)

        var titulo = "AGREGAR EMPLEADO"
        var valor = 60f

        if(empleadoDatosID.isNotEmpty()){
            titulo = "EDITAR EMPLEADO"
            if(banderaFotoPerfil == "Si"){
                valor = 0f
                imagenEmpleado.scaleType = ImageView.ScaleType.CENTER_CROP
            }
        }

        tituloActividad.text = titulo
        val dp = (TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, valor, resources.displayMetrics)).toInt()
        contornoImagenEmpleado.setContentPadding(dp, dp, dp, dp)
    }

    private fun validarDatosActualizados(datosModificados: ArrayList<String>): Boolean{
        var texto = datosModificados[0]
        if(texto != datosEmpleado.nombres){
            return true
        }

        texto = datosModificados[1]
        if(texto != datosEmpleado.apellidos){
            return true
        }

        texto = generoEmpleado.text.toString()
        if(texto != datosEmpleado.genero){
            return true
        }

        texto = fechaNacimientoEmpleado.text.toString()
        if(texto != datosEmpleado.fechaNacimiento){
            return true
        }

        texto = educacionEmpleado.text.toString()
        if(texto != datosEmpleado.educacionNivel){
            return true
        }

        texto = datosModificados[2]
        if(texto != datosEmpleado.areaEducacion){
            return true
        }

        texto = telefonoEmpleado.text.toString()
        if(texto != datosEmpleado.telefono){
            return true
        }

        texto = correoEmpleado.text.toString()
        if(texto != datosEmpleado.correo){
            return true
        }

        texto = datosModificados[3]
        if(texto != datosEmpleado.empresa){
            return true
        }

        texto = datosModificados[4]
        if(texto != datosEmpleado.puesto){
            return true
        }

        texto = experienciaLaboralEmpleado.text.toString()
        if(texto != datosEmpleado.experienciaLaboral){
            return true
        }

        texto = salarioEmpleado.text.toString()
        if(texto != datosEmpleado.salario){
            return true
        }

        texto = monedaSalarioEmpleado.text.toString()
        if(texto != datosEmpleado.monedaSalario){
            return true
        }

        return false
    }

    private fun obteniendoDatosEmpleado(datosModificados: ArrayList<String>): Empleado {
        val id = datosEmpleado.id
        val fotoEmpleado = fotoPerfilEmpleado
        val fotoIndicador = banderaFotoPerfil
        val nombres = datosModificados[0]
        val apellidos = datosModificados[1]
        val genero = generoEmpleado.text.toString()
        val fechaNacimiento = fechaNacimientoEmpleado.text.toString()
        val educacionNivel = educacionEmpleado.text.toString()
        val areaEducacion = datosModificados[2]
        val telefono = telefonoEmpleado.text.toString()
        val correo = correoEmpleado.text.toString()
        val empresa = datosModificados[3]
        val puesto = datosModificados[4]
        val experienciaLaboral = experienciaLaboralEmpleado.text.toString()
        val salario = salarioEmpleado.text.toString()
        val monedaSalario = monedaSalarioEmpleado.text.toString()

        return Empleado(id, fotoEmpleado, fotoIndicador, nombres, apellidos, genero, fechaNacimiento, educacionNivel, areaEducacion,
            telefono, correo, empresa, puesto, experienciaLaboral, salario, monedaSalario)
    }

    private fun seleccionarImagen(){
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        obtenerImagen.launch(intent)
    }

    private val obtenerImagen = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        if(it.resultCode == Activity.RESULT_OK){
            val imagenURI = it.data?.data
            fotoPerfilEmpleado = BitmapFactory.decodeStream(contentResolver.openInputStream(imagenURI!!))

            imagenEmpleado.setImageBitmap(fotoPerfilEmpleado)
            contornoImagenEmpleado.setContentPadding(0, 0, 0, 0)
            imagenEmpleado.scaleType = ImageView.ScaleType.CENTER_CROP
            banderaFotoPerfil = "Si"
        }
    }

    private fun cerrarActividad(mensaje: String, empleadoID: String){
        var actividad = Intent(this@DatosEmpleadoActivity, MainActivity::class.java)

        if(empleadoID.isNotEmpty()){
            actividad = Intent(this@DatosEmpleadoActivity, VisualizarEmpleadoActivity::class.java)
            actividad.putExtra("empleadoDatosID", empleadoID)
        }

        startActivity(actividad)
        finish()

        if(mensaje.isNotEmpty()){
            Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show()
        }
    }
}