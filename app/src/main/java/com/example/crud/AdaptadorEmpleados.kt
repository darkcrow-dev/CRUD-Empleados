package com.example.crud

import android.content.Context
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView

class AdaptadorEmpleados(private val context: Context): RecyclerView.Adapter<AdaptadorEmpleados.EmpleadosViewHolder>() {
    private var listaEmpleados: ArrayList<Empleado> = ArrayList()
    private lateinit var clickListener: OnClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmpleadosViewHolder{
        val view = LayoutInflater.from(parent.context).inflate(R.layout.datos_empleado_tarjeta, parent, false)
        return EmpleadosViewHolder(view, clickListener, context)
    }

    override fun getItemCount(): Int {
        return listaEmpleados.size
    }

    override fun onBindViewHolder(holder: EmpleadosViewHolder, position: Int) {
        val datosEmpleado = listaEmpleados[position]
        holder.bindView(datosEmpleado)
    }

    fun actualizarLista(empleadosArrayList: ArrayList<Empleado>){
        this.listaEmpleados = empleadosArrayList
        notifyDataSetChanged()
    }

    class EmpleadosViewHolder(view: View, clickListenerItem: OnClickListener, private val context: Context): RecyclerView.ViewHolder(view){
        private var contornoImagenEmpleado = view.findViewById<CardView>(R.id.contornoImagenEmpleado)

        private var imagenEmpleado = view.findViewById<ImageView>(R.id.imagenEmpleado)
        private var idEmpleado = view.findViewById<TextView>(R.id.textoID)
        private var nombresEmpleado = view.findViewById<TextView>(R.id.textoNombres)
        private var apellidosEmpleado = view.findViewById<TextView>(R.id.textoApellidos)
        private var generoEmpleado = view.findViewById<TextView>(R.id.textoGenero)
        private var puestoEmpleado = view.findViewById<TextView>(R.id.textoPuesto)

        init {
            view.setOnClickListener{
                clickListenerItem.onClickItem(adapterPosition)
            }
        }

        fun bindView(datosEmpleado: Empleado){
            imagenEmpleado.setImageBitmap(datosEmpleado.fotoPerfil)
            idEmpleado.text = datosEmpleado.id
            nombresEmpleado.text = datosEmpleado.nombres
            apellidosEmpleado.text = datosEmpleado.apellidos
            generoEmpleado.text = datosEmpleado.genero
            puestoEmpleado.text = datosEmpleado.puesto

            if(datosEmpleado.banderaFoto == "No"){
                val dp = (TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 18f, context.resources.displayMetrics)).toInt()
                contornoImagenEmpleado.setContentPadding(dp, dp, dp, dp)
                return
            }

            contornoImagenEmpleado.setContentPadding(0, 0, 0, 0)
            imagenEmpleado.scaleType = ImageView.ScaleType.CENTER_CROP
        }
    }

    interface OnClickListener{
        fun onClickItem(position: Int)
    }

    fun setOnClickListener(clickListenerItem: OnClickListener){
        clickListener = clickListenerItem
    }
}