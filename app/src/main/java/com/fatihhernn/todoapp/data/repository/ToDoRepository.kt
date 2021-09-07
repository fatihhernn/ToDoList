package com.fatihhernn.todoapp.data.repository

import androidx.lifecycle.LiveData
import com.fatihhernn.todoapp.data.ToDoDao
import com.fatihhernn.todoapp.data.models.ToDoData

class ToDoRepository(private val toDoDao: ToDoDao) {

    val getAllData:LiveData<List<ToDoData>> =toDoDao.getAllData()

    suspend fun insertData(toDoData: ToDoData){
        toDoDao.insertData(toDoData)
    }

}