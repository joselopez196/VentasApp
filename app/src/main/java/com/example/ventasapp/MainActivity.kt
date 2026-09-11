package com.example.ventasapp

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.LinearLayout
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.example.ventasapp.adapter.VentaAdapter
import com.example.ventasapp.db.VentasDbHelper

class MainActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "VENTAS_APP"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        // Elementos de la pantalla
        val listaVentas = findViewById<ListView>(
            R.id.listaVentas
        )

        val panelCarga = findViewById<LinearLayout>(
            R.id.panelCarga
        )

        Log.i(
            TAG,
            "Aplicacion Ventas iniciada"
        )

        // Cargar animacion de 5 segundos
        val animacion = AnimationUtils.loadAnimation(
            this,
            R.anim.animacion_5_segundos
        )

        panelCarga.startAnimation(animacion)

        Log.i(
            TAG,
            "Animacion de 5 segundos iniciada"
        )

        // Conexion con la base de datos SQLite
        val dbHelper = VentasDbHelper(this)

        val baseDatos = dbHelper.readableDatabase

        Log.i(
            TAG,
            "Conexion BD correcta: ${baseDatos.path}"
        )

        // Obtener los 5 registros
        // Dentro de este metodo se ejecuta rawQuery()
        val ventas = dbHelper.obtenerVentas()

        Log.i(
            TAG,
            "Cantidad de registros obtenidos: ${ventas.size}"
        )

        // Mostrar registros utilizando el Adapter
        val adapter = VentaAdapter(
            this,
            ventas
        )

        listaVentas.adapter = adapter

        // Esperar exactamente 5 segundos
        Handler(
            Looper.getMainLooper()
        ).postDelayed({

            // Ocultar pantalla de carga
            panelCarga.clearAnimation()
            panelCarga.visibility = View.GONE

            // Mostrar lista de ventas
            listaVentas.visibility = View.VISIBLE

            Log.i(
                TAG,
                "Animacion de 5 segundos finalizada"
            )

            Log.i(
                TAG,
                "Lista de ventas mostrada correctamente"
            )

        }, 5000)
    }
}