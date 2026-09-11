package com.example.ventasapp.adapter

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.LinearLayout
import android.widget.TextView
import com.example.ventasapp.R
import com.example.ventasapp.model.Venta

class VentaAdapter(
    private val context: Context,
    private val listaVentas: List<Venta>
) : BaseAdapter() {

    override fun getCount(): Int {
        return listaVentas.size
    }

    override fun getItem(position: Int): Venta {
        return listaVentas[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(
        position: Int,
        convertView: View?,
        parent: ViewGroup?
    ): View {

        val vista = convertView ?: LayoutInflater
            .from(context)
            .inflate(
                R.layout.item_venta,
                parent,
                false
            )

        val venta = getItem(position)

        val cardVenta =
            vista.findViewById<LinearLayout>(
                R.id.cardVenta
            )

        val tvCodigo =
            vista.findViewById<TextView>(
                R.id.tvCodigo
            )

        val tvNombre =
            vista.findViewById<TextView>(
                R.id.tvNombre
            )

        val tvPrecio =
            vista.findViewById<TextView>(
                R.id.tvPrecio
            )

        val tvCantidad =
            vista.findViewById<TextView>(
                R.id.tvCantidad
            )

        val tvTipo =
            vista.findViewById<TextView>(
                R.id.tvTipo
            )

        val tvFecha =
            vista.findViewById<TextView>(
                R.id.tvFecha
            )

        // Mostrar los datos
        tvCodigo.text = venta.codigo
        tvNombre.text = venta.nombre

        tvPrecio.text =
            "Precio: S/ %.2f".format(venta.precio)

        tvCantidad.text =
            "Cantidad: ${venta.cantidad}"

        tvTipo.text =
            "Tipo: ${venta.tipo}"

        tvFecha.text =
            "Fecha: ${venta.fechaVenta}"

        // Crear fondo de la tarjeta
        val fondo = GradientDrawable()

        fondo.cornerRadius = 25f

        // Factura = rojo
        if (
            venta.tipo.equals(
                "Factura",
                ignoreCase = true
            )
        ) {

            fondo.setColor(
                Color.parseColor("#FFCDD2")
            )

            fondo.setStroke(
                3,
                Color.parseColor("#D32F2F")
            )

        } else {

            // Boleta = verde
            fondo.setColor(
                Color.parseColor("#C8E6C9")
            )

            fondo.setStroke(
                3,
                Color.parseColor("#388E3C")
            )
        }

        cardVenta.background = fondo

        return vista
    }
}