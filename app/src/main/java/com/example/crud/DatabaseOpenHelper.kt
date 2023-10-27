package com.example.crud

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import java.io.ByteArrayOutputStream

class DatabaseOpenHelper(private val contexto: Context): SQLiteOpenHelper(contexto, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object{
        private const val DATABASE_NAME = "empleados.db"
        private const val DATABASE_VERSION = 1

        private const val ID_VALUE = "id"
        private const val EMPLOYEES_TABLE = "tabla_empleados"
        private const val PHOTO_VALUE = "foto_de_perfil"
        private const val PHOTO_INDICATOR_VALUE = "foto_indicador"
        private const val NAME_VALUE = "nombres"
        private const val LASTNAME_VALUE = "apellidos"
        private const val GENDER_VALUE = "genero"
        private const val BIRTHDATE_VALUE = "fecha_de_nacimiento"
        private const val SCHOOLING_LEVEL_VALUE = "educacion"
        private const val EDUCATION_AREA_VALUE = "area_de_educacion"
        private const val TELEPHONE_VALUE = "telefono"
        private const val EMAIL_VALUE = "correo"
        private const val COMPANY_VALUE = "compañia"
        private const val JOB_POSITION_VALUE = "puesto_de_trabajo"
        private const val WORK_EXPERIENCE_VALUE = "experiencia_laboral"
        private const val SALARY_VALUE = "salario"
        private const val CURRENCY_VALUE = "moneda"
    }

    override fun onCreate(database: SQLiteDatabase?) {
        val crearTablaBD = ("CREATE TABLE " + EMPLOYEES_TABLE + "(" + ID_VALUE + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                PHOTO_VALUE + " BLOB," + PHOTO_INDICATOR_VALUE + " TEXT," +
                NAME_VALUE + " TEXT," + LASTNAME_VALUE + " TEXT," + GENDER_VALUE + " TEXT," + BIRTHDATE_VALUE + " TEXT," +
                SCHOOLING_LEVEL_VALUE + " TEXT," + EDUCATION_AREA_VALUE + " TEXT," + TELEPHONE_VALUE + " TEXT," +
                EMAIL_VALUE + " TEXT," + COMPANY_VALUE + " TEXT," + JOB_POSITION_VALUE + " TEXT," + WORK_EXPERIENCE_VALUE + " TEXT," +
                SALARY_VALUE + " TEXT," + CURRENCY_VALUE + " TEXT" + ")")
        database?.execSQL(crearTablaBD)
    }

    override fun onUpgrade(database: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
    }

    fun insertEmployee(dataEmployee: Empleado){
        val dataBase = this.writableDatabase
        val contentValues = ContentValues()

        contentValues.put(PHOTO_VALUE, convertirImagen(dataEmployee.fotoPerfil))
        contentValues.put(PHOTO_INDICATOR_VALUE, dataEmployee.banderaFoto)
        contentValues.put(NAME_VALUE, dataEmployee.nombres)
        contentValues.put(LASTNAME_VALUE, dataEmployee.apellidos)
        contentValues.put(GENDER_VALUE, dataEmployee.genero)
        contentValues.put(BIRTHDATE_VALUE, dataEmployee.fechaNacimiento)
        contentValues.put(SCHOOLING_LEVEL_VALUE, dataEmployee.educacionNivel)
        contentValues.put(EDUCATION_AREA_VALUE, dataEmployee.areaEducacion)
        contentValues.put(TELEPHONE_VALUE, dataEmployee.telefono)
        contentValues.put(EMAIL_VALUE, dataEmployee.correo)
        contentValues.put(COMPANY_VALUE, dataEmployee.empresa)
        contentValues.put(JOB_POSITION_VALUE, dataEmployee.puesto)
        contentValues.put(WORK_EXPERIENCE_VALUE, dataEmployee.experienciaLaboral)
        contentValues.put(SALARY_VALUE, dataEmployee.salario)
        contentValues.put(CURRENCY_VALUE, dataEmployee.monedaSalario)

        dataBase.insert(EMPLOYEES_TABLE, null, contentValues)
        dataBase.close()
    }

    fun deleteEmployee(id: String){
        val dataBase = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(ID_VALUE, id)

        dataBase.delete(EMPLOYEES_TABLE, "$ID_VALUE = $id", null)
        dataBase.close()
    }

    fun updateEmployee(dataEmployee: Empleado){
        val dataBase = this.writableDatabase
        val contentValues = ContentValues()

        contentValues.put(PHOTO_VALUE, convertirImagen(dataEmployee.fotoPerfil))
        contentValues.put(PHOTO_INDICATOR_VALUE, dataEmployee.banderaFoto)
        contentValues.put(NAME_VALUE, dataEmployee.nombres)
        contentValues.put(LASTNAME_VALUE, dataEmployee.apellidos)
        contentValues.put(GENDER_VALUE, dataEmployee.genero)
        contentValues.put(BIRTHDATE_VALUE, dataEmployee.fechaNacimiento)
        contentValues.put(SCHOOLING_LEVEL_VALUE, dataEmployee.educacionNivel)
        contentValues.put(EDUCATION_AREA_VALUE, dataEmployee.areaEducacion)
        contentValues.put(TELEPHONE_VALUE, dataEmployee.telefono)
        contentValues.put(EMAIL_VALUE, dataEmployee.correo)
        contentValues.put(COMPANY_VALUE, dataEmployee.empresa)
        contentValues.put(JOB_POSITION_VALUE, dataEmployee.puesto)
        contentValues.put(WORK_EXPERIENCE_VALUE, dataEmployee.experienciaLaboral)
        contentValues.put(SALARY_VALUE, dataEmployee.salario)
        contentValues.put(CURRENCY_VALUE, dataEmployee.monedaSalario)

        dataBase.update(EMPLOYEES_TABLE, contentValues, "$ID_VALUE = ${dataEmployee.id}",null)
        dataBase.close()
    }

    fun getAllEmployees(): ArrayList<Empleado>{
        val listaEmpleados: ArrayList<Empleado> = ArrayList()
        val selectQuery = "SELECT * FROM $EMPLOYEES_TABLE"
        val dataBase = this.readableDatabase

        val cursor: Cursor = dataBase.rawQuery(selectQuery, null)

        if(cursor.count > 0){
            if(cursor.moveToFirst()){
                do {
                    val id = cursor.getInt(0).toString()

                    val imagen = cursor.getBlob(1)
                    val fotoIndicador = cursor.getString(2)
                    val nombres = cursor.getString(3)
                    val apellidos = cursor.getString(4)
                    val genero = cursor.getString(5)
                    val fechaNacimiento = cursor.getString(6)
                    val educacionNivel = cursor.getString(7)
                    val areaEducacion = cursor.getString(8)
                    val telefono = cursor.getString(9)
                    val correo = cursor.getString(10)
                    val empresa = cursor.getString(11)
                    val puesto = cursor.getString(12)
                    val experienciaLaboral = cursor.getString(13)
                    val salario = cursor.getString(14)
                    val monedaSalario = cursor.getString(15)

                    val fotoPerfil = BitmapFactory.decodeByteArray(imagen, 0, imagen.size)

                    val datosEmpleado = Empleado(id, fotoPerfil, fotoIndicador, nombres, apellidos, genero, fechaNacimiento, educacionNivel, areaEducacion,
                        telefono, correo, empresa, puesto, experienciaLaboral, salario, monedaSalario)

                    listaEmpleados.add(datosEmpleado)

                } while (cursor.moveToNext())
            }
        }

        cursor.close()
        return listaEmpleados
    }

    fun getEmployee(id: String): Empleado {
        val foto = BitmapFactory.decodeResource(contexto.resources, R.drawable.agregar_imagen)
        var datosEmpleado = Empleado("", foto, "No","", "", "", "", "",
            "", "", "", "", "", "", "", "MXN")

        if(id.isNotEmpty()){
            val dataBase = this.readableDatabase
            val selectQuery = "SELECT * FROM $EMPLOYEES_TABLE WHERE $ID_VALUE = $id"
            val cursor: Cursor = dataBase.rawQuery(selectQuery, null)

            cursor.moveToFirst()

            val imagen = cursor.getBlob(1)
            val fotoIndicador = cursor.getString(2)
            val nombres = cursor.getString(3)
            val apellidos = cursor.getString(4)
            val genero = cursor.getString(5)
            val fechaNacimiento = cursor.getString(6)
            val educacionNivel = cursor.getString(7)
            val areaEducacion = cursor.getString(8)
            val telefono = cursor.getString(9)
            val correo = cursor.getString(10)
            val empresa = cursor.getString(11)
            val puesto = cursor.getString(12)
            val experienciaLaboral = cursor.getString(13)
            val salario = cursor.getString(14)
            val monedaSalario = cursor.getString(15)

            cursor.close()

            val fotoPerfil = BitmapFactory.decodeByteArray(imagen, 0, imagen.size)

            datosEmpleado = Empleado(id, fotoPerfil, fotoIndicador, nombres, apellidos, genero, fechaNacimiento, educacionNivel, areaEducacion,
                telefono, correo, empresa, puesto, experienciaLaboral, salario, monedaSalario)
        }
        return datosEmpleado
    }

    private fun convertirImagen(bitmap: Bitmap): ByteArray {
        val imagenRecibida = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, imagenRecibida)
        return imagenRecibida.toByteArray()
    }
}