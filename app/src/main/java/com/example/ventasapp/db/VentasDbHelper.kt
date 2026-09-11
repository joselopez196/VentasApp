package com.example.ventasapp.db

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.example.ventasapp.model.Venta

class VentasDbHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "ventas.db"
        private const val DATABASE_VERSION = 1

        private const val TABLE_VENTAS = "ventas"

        private const val COLUMN_CODIGO = "codigo"
        private const val COLUMN_NOMBRE = "nombre"
        private const val COLUMN_PRECIO = "precio"
        private const val COLUMN_CANTIDAD = "cantidad"
        private const val COLUMN_TIPO = "tipo"
        private const val COLUMN_FECHA = "fecha_venta"

        private const val TAG = "VENTAS_DB"
    }

    override fun onCreate(db: SQLiteDatabase) {

        val crearTabla = """
            CREATE TABLE $TABLE_VENTAS (
                $COLUMN_CODIGO TEXT PRIMARY KEY,
                $COLUMN_NOMBRE TEXT NOT NULL,
                $COLUMN_PRECIO REAL NOT NULL,
                $COLUMN_CANTIDAD INTEGER NOT NULL,
                $COLUMN_TIPO TEXT NOT NULL,
                $COLUMN_FECHA TEXT NOT NULL
            )
        """.trimIndent()

        db.execSQL(crearTabla)

        Log.i(TAG, "Tabla ventas creada correctamente")

        insertarDatosIniciales(db)
    }

    private fun insertarDatosIniciales(db: SQLiteDatabase) {

        insertarVenta(
            db,
            Venta(
                codigo = "XYZ001",
                nombre = "Laptop Gamer HP",
                precio = 2500.0,
                cantidad = 1,
                tipo = "Factura",
                fechaVenta = "20/07/2026"
            )
        )

        insertarVenta(
            db,
            Venta(
                codigo = "XYZ002",
                nombre = "Teclado Logitech",
                precio = 70.0,
                cantidad = 1,
                tipo = "Boleta",
                fechaVenta = "14/08/2026"
            )
        )

        insertarVenta(
            db,
            Venta(
                codigo = "XYZ003",
                nombre = "Mouse Gamer",
                precio = 120.0,
                cantidad = 2,
                tipo = "Factura",
                fechaVenta = "18/08/2026"
            )
        )

        insertarVenta(
            db,
            Venta(
                codigo = "XYZ004",
                nombre = "Monitor 24 pulgadas",
                precio = 650.0,
                cantidad = 1,
                tipo = "Boleta",
                fechaVenta = "19/08/2026"
            )
        )

        insertarVenta(
            db,
            Venta(
                codigo = "XYZ005",
                nombre = "Audifonos Logitech",
                precio = 150.0,
                cantidad = 1,
                tipo = "Boleta",
                fechaVenta = "21/08/2026"
            )
        )

        Log.i(TAG, "5 registros insertados correctamente")
    }

    private fun insertarVenta(
        db: SQLiteDatabase,
        venta: Venta
    ) {

        val valores = ContentValues().apply {
            put(COLUMN_CODIGO, venta.codigo)
            put(COLUMN_NOMBRE, venta.nombre)
            put(COLUMN_PRECIO, venta.precio)
            put(COLUMN_CANTIDAD, venta.cantidad)
            put(COLUMN_TIPO, venta.tipo)
            put(COLUMN_FECHA, venta.fechaVenta)
        }

        db.insert(
            TABLE_VENTAS,
            null,
            valores
        )

        Log.i(
            TAG,
            "Registro insertado: ${venta.codigo} - ${venta.nombre}"
        )
    }

    fun obtenerVentas(): List<Venta> {

        val listaVentas = mutableListOf<Venta>()

        val db = readableDatabase

        Log.i(TAG, "Conexion a la base de datos establecida")

        val consulta = """
            SELECT codigo,
                   nombre,
                   precio,
                   cantidad,
                   tipo,
                   fecha_venta
            FROM ventas
            ORDER BY codigo ASC
        """.trimIndent()

        val cursor = db.rawQuery(
            consulta,
            null
        )

        Log.i(TAG, "rawQuery ejecutado correctamente")

        cursor.use {

            while (it.moveToNext()) {

                val codigo = it.getString(
                    it.getColumnIndexOrThrow(COLUMN_CODIGO)
                )

                val nombre = it.getString(
                    it.getColumnIndexOrThrow(COLUMN_NOMBRE)
                )

                val precio = it.getDouble(
                    it.getColumnIndexOrThrow(COLUMN_PRECIO)
                )

                val cantidad = it.getInt(
                    it.getColumnIndexOrThrow(COLUMN_CANTIDAD)
                )

                val tipo = it.getString(
                    it.getColumnIndexOrThrow(COLUMN_TIPO)
                )

                val fecha = it.getString(
                    it.getColumnIndexOrThrow(COLUMN_FECHA)
                )

                listaVentas.add(
                    Venta(
                        codigo = codigo,
                        nombre = nombre,
                        precio = precio,
                        cantidad = cantidad,
                        tipo = tipo,
                        fechaVenta = fecha
                    )
                )
            }
        }

        Log.i(
            TAG,
            "Registros encontrados: ${listaVentas.size}"
        )

        return listaVentas
    }

    override fun onUpgrade(
        db: SQLiteDatabase,
        oldVersion: Int,
        newVersion: Int
    ) {

        db.execSQL(
            "DROP TABLE IF EXISTS $TABLE_VENTAS"
        )

        onCreate(db)
    }
}