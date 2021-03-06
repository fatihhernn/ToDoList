package com.fatihhernn.todoapp.fragments.list.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.fatihhernn.todoapp.R
import com.fatihhernn.todoapp.data.models.Priority
import com.fatihhernn.todoapp.data.models.ToDoData
import com.fatihhernn.todoapp.fragments.list.ListFragmentDirections

class ListAdapter: RecyclerView.Adapter<ListAdapter.MyViewHolder>() {

    var dataList= emptyList<ToDoData>()

    class MyViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.row_layout,parent,false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        holder.itemView.findViewById<TextView>(R.id.title_txt).text=dataList[position].title
        holder.itemView.findViewById<TextView>(R.id.description_txt).text=dataList[position].description
        holder.itemView.findViewById<ConstraintLayout>(R.id.row_background).setOnClickListener {
            val action=ListFragmentDirections.actionListFragmentToUpdateFragment(dataList[position])
            holder.itemView.findNavController().navigate(action)
        }

        when(dataList[position].priority){
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
        val toDoDiffUtil=ToDoDiffUtil(dataList,toDoData)
        val toDoDiffResult=DiffUtil.calculateDiff(toDoDiffUtil)
        this.dataList=toDoData
        toDoDiffResult.dispatchUpdatesTo(this)
    }
}