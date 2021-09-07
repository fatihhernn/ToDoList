package com.fatihhernn.todoapp.fragments.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.fatihhernn.todoapp.R
import com.fatihhernn.todoapp.data.models.Priority
import com.fatihhernn.todoapp.data.models.ToDoData

class ListAdapter: RecyclerView.Adapter<ListAdapter.MyViewHolder>() {
    var dataList= emptyList<ToDoData>()

    class MyViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.row_layout,parent,false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.itemView.findViewById<TextView>(R.id.title_et).text=dataList[position].title
        holder.itemView.findViewById<TextView>(R.id.description_editText).text=dataList[position].description

        val priority=dataList[position].priority

        when(priority){
            Priority.HIGH -> holder.itemView.findViewById<CardView>(R.id.indicator_priority).setCardBackgroundColor(ContextCompat.getColor(
                holder.itemView.context,
                R.color.red
            ))
            Priority.LOW -> holder.itemView.findViewById<CardView>(R.id.indicator_priority).setCardBackgroundColor(ContextCompat.getColor(
                holder.itemView.context,
                R.color.green
            ))
            Priority.MEDIUM -> holder.itemView.findViewById<CardView>(R.id.indicator_priority).setCardBackgroundColor(ContextCompat.getColor(
                holder.itemView.context,
                R.color.yellow
            ))
        }
    }

    override fun getItemCount(): Int =dataList.size

    fun setData(toDoData:List<ToDoData>){
        this.dataList=toDoData
        notifyDataSetChanged()
    }
}