package com.fatihhernn.todoapp.data.repository

import androidx.lifecycle.LiveData
import com.fatihhernn.todoapp.data.ToDoDao
import com.fatihhernn.todoapp.data.models.ToDoData

class ToDoRepository(private val toDoDao: ToDoDao) {

    val getAllData:LiveData<List<ToDoData>> =toDoDao.getAllData()

    suspend fun insertData(toDoData: ToDoData){
        toDoDao.insertData(toDoData)
    }
    suspend fun updateData(toDoData: ToDoData){
        toDoDao.updateData(toDoData)
    }
    suspend fun deleteItem(toDoData: ToDoData){
        toDoDao.deleteItem(toDoData)
    }
    suspend fun deleteAll(){
        toDoDao.deleteAll()
    }

    fun searchDatabase(searchQuery:String):LiveData<List<ToDoData>>{
        return toDoDao.searchDatabase(searchQuery)
    }

}