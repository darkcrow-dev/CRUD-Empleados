package com.example.crud
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseOpenHelper(context: Context): SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object{
        private const val DATABASE_NAME = "Empleados.db"
        private const val DATABASE_VERSION = 1

        private const val ID_VALUE = "ID"
        private const val TABLE_EMPLOYEE = "TABLA_EMPLEADOS"
        private const val NAME_VALUE = "NAME"
        private const val EMAIL_VALUE = "EMAIL"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTableDB = ("CREATE TABLE " + TABLE_EMPLOYEE + "(" + ID_VALUE + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + NAME_VALUE + " TEXT," + EMAIL_VALUE + " TEXT" + ")")

        db?.execSQL(createTableDB)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS $TABLE_EMPLOYEE")
        onCreate(db)
    }

    fun insertEmployee(ee: Employee): Long {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(NAME_VALUE, ee.name)
        contentValues.put(EMAIL_VALUE, ee.email)

        val sucess = db.insert(TABLE_EMPLOYEE, null, contentValues)
        db.close()

        return sucess
    }

    fun deleteEmployee(id: String): Int {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(ID_VALUE, id)

        val success = db.delete(TABLE_EMPLOYEE, "ID =$id", null)
        db.close()

        return success
    }

    fun updateStudent(ee: Employee): Int {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(ID_VALUE, ee.id)
        contentValues.put(NAME_VALUE, ee.name)
        contentValues.put(EMAIL_VALUE, ee.email)

        val success = db.update(TABLE_EMPLOYEE, contentValues, "ID=" + ee.id, null)
        db.close()

        return success
    }

    fun getAllEmployees(): ArrayList<Employee> {
        val eeList: ArrayList<Employee> = ArrayList()
        val selectQuery = "SELECT * FROM $TABLE_EMPLOYEE"
        val db = this.readableDatabase

        val cursor: Cursor?

        try {
            cursor = db.rawQuery(selectQuery, null)
        } catch (e: Exception) {
            e.printStackTrace()
            db.execSQL(selectQuery)

            return ArrayList()
        }

        var id: String
        var name: String
        var email: String

        if (cursor.moveToFirst()){
            do {
                id = cursor.getInt(0).toString()
                name = cursor.getString(1)
                email = cursor.getString(2)

                val std = Employee(id = id, name = name, email = email)
                eeList.add(std)
            }while (cursor.moveToNext())
        }

        return eeList
    }
}