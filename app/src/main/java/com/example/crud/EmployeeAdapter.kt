package com.example.crud

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class EmployeeAdapter: RecyclerView.Adapter<EmployeeAdapter.EmployeeViewHolder>() {
    private var eeList: ArrayList<Employee> = ArrayList()
    private var onClickDeleteItem: ((Employee) -> Unit?)? = null
    private var onClickEditItem: ((Employee) -> Unit?)? = null

    fun setOnClickDeleteItem(callback: (Employee) -> Unit) {    //Unit es el void en kotlin y no tiene funcion de retorno
        this.onClickDeleteItem = callback			//Inicializa la funcion a utilizar
    }

    fun setOnClickEditItem(callback: (Employee) -> Unit) {
        this.onClickEditItem = callback
    }

    fun addItems(items: ArrayList<Employee>){
        this.eeList = items
        notifyDataSetChanged()  //notifica al recycleview que se actualiza item por item
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = EmployeeViewHolder (
        LayoutInflater.from(parent.context).inflate(R.layout.card_items_employees, parent, false)
    )

    override fun getItemCount(): Int {
        return eeList.size
    }

    override fun onBindViewHolder(holder: EmployeeViewHolder, position: Int) {
        val dataEmployees = eeList[position]
        holder.bindView(dataEmployees)

        holder.delete.setOnClickListener {
            onClickDeleteItem?.invoke(dataEmployees)
        }

        holder.edit.setOnClickListener {		//Activa la funcion al momento de que suceda el evento
            onClickEditItem?.invoke(dataEmployees)
        }

    }

    class EmployeeViewHolder(view: View): RecyclerView.ViewHolder(view) {
        private var id = view.findViewById<TextView>(R.id.idEmployee)
        private var name = view.findViewById<TextView>(R.id.nameEmployee)
        private var email = view.findViewById<TextView>(R.id.emailEmployee)

        var edit = view.findViewById<ImageView>(R.id.imageEdit)
        var delete = view.findViewById<ImageView>(R.id.imageDelete)

        fun bindView(ee: Employee){
            id.text = ee.id
            name.text = ee.name
            email.text = ee.email
        }
    }
}