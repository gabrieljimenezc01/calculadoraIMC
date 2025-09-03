package com.example.calculadoraimc

import android.content.Context
import android.database.sqlite.SQLiteOpenHelper
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteDatabase.CursorFactory

class AdminSQLiteOpenHelper(context: Context, name: String, factory: CursorFactory?, version: Int) : SQLiteOpenHelper(context, name, factory, version) {

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(
            "CREATE TABLE Historial(" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "peso DOUBLE, " +
                    "altura DOUBLE, " +
                    "imc DOUBLE, " +
                    "nombre TEXT, " +
                    "fecha TEXT)"
        )
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {

    }
}